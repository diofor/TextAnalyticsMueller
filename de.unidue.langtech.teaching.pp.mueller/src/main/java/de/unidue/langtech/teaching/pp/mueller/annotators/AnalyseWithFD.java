package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
//import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class AnalyseWithFD extends JCasAnnotator_ImplBase
{
	//private FrequencyDistribution<String> fd;
    private ConditionalFrequencyDistribution<String, String> cfd_rawdata_target;
	private ConditionalFrequencyDistribution<String, String> cfd_rawdata_sentiment;
    private ConditionalFrequencyDistribution<String, String> cfd_target;
    private ConditionalFrequencyDistribution<String, String> cfd_sentiment;
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
//        fd = new FrequencyDistribution<String>();
        cfd_rawdata_target = new ConditionalFrequencyDistribution<String, String>();
        cfd_rawdata_sentiment = new ConditionalFrequencyDistribution<String, String>();
        cfd_target = new ConditionalFrequencyDistribution<String, String>();
        cfd_sentiment = new ConditionalFrequencyDistribution<String, String>();
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		String cond = gold.getTargetText();
		String sentiment = gold.getSentiment();
		for(Token t: tokens)
		{
			cfd_rawdata_target.inc(cond, t.getCoveredText());
			cfd_rawdata_sentiment.inc(t.getCoveredText(), sentiment);
			//fd.inc(t.getCoveredText());
		}
		
	}
	
	/* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
      
        /*
         * ToDo: 	Es fehlt noch zu den einzeln Keywords die ich selektiere die Information, ob es in einem Favor, none, Against kontext vokam.
         * 			Dies fehlt auch noch im Reader.
         * 			Auswertung dieser Daten auf weiter Tweets fehlt noch.
         * 
         * Idee:	Eventuell kann ich die FD über Tokens aufbauen und nicht über den String... Dann habe ich die Info direkt mit dabei.
         * 			
         */
        
        //Better use tf/idf
        
        for (String condition : cfd_rawdata_target.getConditions())
        {
        	System.out.println(condition+"\n-------------------\n");
        	
        	for(String t : cfd_rawdata_target.getFrequencyDistribution(condition).getKeys())
//        	for (String t : cfd_rawdata_target.getFreqDist(condition).getMostFrequentSamples(1000))
        	{
        		boolean existsInOtherFDs = false;
	        	for(String secondConditon: cfd_rawdata_target.getConditions())
	        	{
	        		if (!(condition.equals(secondConditon)))
	        		{
	        			if (cfd_rawdata_target.getFrequencyDistribution(secondConditon).contains(t)) existsInOtherFDs = true;
	        		}
	        	}
	        	if (!existsInOtherFDs) cfd_target.addSample(condition, t, cfd_rawdata_target.getCount(condition, t));
        	}
        	
        	
        	for (String t : cfd_target.getFrequencyDistribution(condition).getMostFrequentSamples(50))
        	{
        		System.out.println(t);
        	}
        	System.out.println("\n");
        }
        
        for (String condition : cfd_target.getConditions())
        {
        	System.out.println(condition+" "+cfd_target.getFrequencyDistribution(condition).getB());
        }
        
        CFDFileManager writer = new CFDFileManager();
        writer.write(cfd_target, "Target");
        writer.write(cfd_rawdata_sentiment, "Sentiment");
    }
    
    public ConditionalFrequencyDistribution<String, String> getCfd_rawdata_target() {
		return cfd_rawdata_target;
	}

	public ConditionalFrequencyDistribution<String, String> getCfd_rawdata_sentiment() {
		return cfd_rawdata_sentiment;
	}

}
