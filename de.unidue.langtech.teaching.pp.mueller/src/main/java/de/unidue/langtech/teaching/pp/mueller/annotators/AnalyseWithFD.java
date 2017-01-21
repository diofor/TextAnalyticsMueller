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
    private ConditionalFrequencyDistribution<String, String> cfd_rawdata; 
    private ConditionalFrequencyDistribution<String, String> cfd;
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
//        fd = new FrequencyDistribution<String>();
        cfd_rawdata = new ConditionalFrequencyDistribution<String, String>();
        cfd = new ConditionalFrequencyDistribution<String, String>();
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		String cond = gold.getTargetText();
		for(Token t: tokens)
		{
			cfd_rawdata.inc(cond, t.getCoveredText());
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
        
        for (String condition : cfd_rawdata.getConditions())
        {
        	System.out.println(condition+"\n-------------------\n");
        	
        	for(String t : cfd_rawdata.getFrequencyDistribution(condition).getKeys())
//        	for (String t : cfd_rawdata.getFreqDist(condition).getMostFrequentSamples(1000))
        	{
        		boolean existsInOtherFDs = false;
	        	for(String secondConditon: cfd_rawdata.getConditions())
	        	{
	        		if (!(condition.equals(secondConditon)))
	        		{
	        			if (cfd_rawdata.getFrequencyDistribution(secondConditon).contains(t)) existsInOtherFDs = true;
	        		}
	        	}
	        	if (!existsInOtherFDs) cfd.addSample(condition, t, cfd_rawdata.getCount(condition, t));
        	}
        	
        	
        	for (String t : cfd.getFrequencyDistribution(condition).getMostFrequentSamples(50))
        	{
        		System.out.println(t);
        	}
        	System.out.println("\n");
        }
        
        for (String condition : cfd.getConditions())
        {
        	System.out.println(condition+" "+cfd.getFrequencyDistribution(condition).getB());
        }
        
        CFDFileManager writer = new CFDFileManager();
        writer.write(cfd, "Target");
    }

}