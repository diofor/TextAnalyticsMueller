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
    private static final float seperator = 0.6f; //should set the border for evaluating an non sure tweet. 
    String[] sentiments = {"pos", "neg", "other"};
    
    private int counter, up, down;
    private double min, max, ave;
    
   
    
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
        min = 0;
        max = 0;
        ave = 0;
        up = 0;
        down = 0;
        
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		long[] scores = new long[3];
		scores[0] = di.getSent_count_pos();
		scores[1] = di.getSent_count_neg();
		scores[2] = di.getSent_count_other();
		
		long[] temp = Arrays.copyOf(scores, 3);
		Arrays.sort(temp);
//		System.out.println(Arrays.toString(scores));
		if ((temp[2]*seperator) < temp[1])
		{
			Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
			
			for(Token t: tokens)
			{
				String pos_raw = t.getPos().getPosValue().toLowerCase();
				String pos = parse(pos_raw);
				
				if (!(pos.isEmpty())) sentimentValue += wordnet.extract(t.getCoveredText().toLowerCase(), pos);
			}
			++counter;
//			System.out.printf(" %.7f %n", sentimentValue);
		}
		
		
		if (sentimentValue != 0)
		{
			int faktor = 8;
//			System.err.println(counter);
//			System.out.println(Arrays.toString(scores));
			if (sentimentValue < 0) //Die Einschätzung von Wordnet geht eher ins negative.
			{
				 scores[1] *= (Math.abs(sentimentValue)+1)*faktor; //+1 damit der Faktor immer größer als 1 ist.
//				 System.out.println("Korrektur-");
				 ++down;
			}
			else //Die Einschätzung von Wordnet geht eher ins positve.
			{
				scores[0] *= (Math.abs(sentimentValue)+1)*faktor; //+1 damit der Faktor immer größer als 1 ist.
//				System.out.println("Korrektur+");
				++up;
				
			}
//			System.out.println(Arrays.toString(scores) +"\n");
		}
		
		long max_score = Long.MIN_VALUE;
		int stelle = -1; 
		for(int i = 0; i < scores.length; ++i)
		{
			if(scores[i] > max_score) {
				max_score = scores[i];
				stelle = i;
			}
		}
		di.setSentiment(sentiments[stelle]);
		
		di.setSentiment_value_wordnet(sentimentValue);
		di.addToIndexes();
		if (sentimentValue > max) max = sentimentValue;
		if (sentimentValue < min) min = sentimentValue;
		if (sentimentValue != 0) ave += sentimentValue;
		sentimentValue = 0;
		
	}
	
	private String parse(String raw)
	{
		if (raw.charAt(0) == 'j') raw = "a";
		raw = raw.substring(0, 1);
		if (!("anvr".contains(raw))) raw = "";
		return raw;
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
        System.out.printf("Das minimum ist %.6f - das maximum ist %.6f %nDer Durchschnitt ist %.6f %n", min, max, ave/counter);
        System.out.printf("Es wurde %d mal nach open korrigiert und %d mal nach unten.", up, down);
        System.out.println("ENDE");

    }

}
