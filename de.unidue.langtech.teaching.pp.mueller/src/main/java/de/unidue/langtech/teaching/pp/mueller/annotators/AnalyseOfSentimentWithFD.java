package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
//import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class AnalyseOfSentimentWithFD extends JCasAnnotator_ImplBase
{
	//private FrequencyDistribution<String> fd;
    private ConditionalFrequencyDistribution<String, String> cfd;
    private FrequencyDistribution<String> fd;
    private long jcasCounter;
    private long trefferCounter;
    
   
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        CFDFileManager fm = new CFDFileManager();
        cfd = fm.read("Sentiment");
        jcasCounter = 0;
        trefferCounter = 0;
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		int neg = 0;
		int pos = 0;
		int other = 0;
		jcasCounter++;
		//GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		//String cond = gold.getTargetText();
		//String sentiment = gold.getSentiment();
		for(Token t: tokens)
		{
			fd = cfd.getFrequencyDistribution(t.getCoveredText());
			if (fd != null)
			{
				String max = fd.getMostFrequentSamples(1).get(0);
				if(max.equals("pos")) pos++;
				if(max.equals("neg")) neg++;
				if(max.equals("other")) other++;
			}
			
		}
		
		String sentimentOfJCas = "neg"; //weil auf den Trainignsdaten das häufigste Sentiment neg ist.
		if (pos > neg && pos > other) sentimentOfJCas = "pos";
		if (neg > pos && neg > other) sentimentOfJCas = "neg";
		if (other > pos && other > neg) sentimentOfJCas = "other";
		
		//Vergeichen....
//		String goldSentiment = JCasUtil.selectSingle(aJCas, GoldInformation.class).getSentiment();
//		if (goldSentiment.equals(sentimentOfJCas)) trefferCounter++;
		
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		di.setSentiment(sentimentOfJCas);
		di.addToIndexes();
		
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
    }

}
