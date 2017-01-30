package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
//import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.io.SentiWordNet;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;
import edu.berkeley.nlp.util.ArrayUtil;

public class AnalyseOfSentimentWithWordnet extends JCasAnnotator_ImplBase
{
	public static final String PARAM_WORDNET_FILE = "WordnetFile";
    @ConfigurationParameter(name = PARAM_WORDNET_FILE, mandatory = true)
    private File wordnetFile;
    private SentiWordNet wordnet;
    private double sentimentValue;
    private static final float seperator = 0.8f; //should set the border for evaluating an non sure tweet. 
	
    
    private int counter;
    
   
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        try {
			wordnet = new SentiWordNet(wordnetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        sentimentValue = 0;
        
        counter = 0;
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		long[] scores = new long[3];
		scores[0] = di.getSent_count_pos();
		scores[1] = di.getSent_count_neg();
		scores[2] = di.getSent_count_other();
		
		Arrays.sort(scores);
//		System.out.println(Arrays.toString(scores));
		if ((scores[2]*seperator) < scores[1])
		{
			Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
			for(Token t: tokens)
			{
//				System.out.println(t.getPos());
				sentimentValue += wordnet.extract(t.getCoveredText().toLowerCase(), "a");
			}
			++counter;
			System.out.printf(" %.7f %n", sentimentValue);
		}
		di.setSentiment_value_wordnet(sentimentValue);
		sentimentValue = 0;
		
	}
	
	/* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
//        System.out.println("Absolute Zahlen: \nTreffer: "+trefferCounter+" - JCas Elemente: "+jcasCounter);
//        System.out.println("Trefferquote: "+ (double)trefferCounter/jcasCounter*100 +"%");
        
//        ResultSpecification rs = new
        System.out.println("Counter: "+ counter);
        System.out.println("ENDE");

    }

}
