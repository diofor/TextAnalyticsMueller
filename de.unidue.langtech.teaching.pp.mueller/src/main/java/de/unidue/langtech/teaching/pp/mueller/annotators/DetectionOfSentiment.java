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

public class DetectionOfSentiment extends JCasAnnotator_ImplBase
{
	//private FrequencyDistribution<String> fd;
    private ConditionalFrequencyDistribution<String, String> cfd;
    private FrequencyDistribution<String> fd;
    
    String[] sentiments = {"pos", "neg", "other"};
	long[] countsForOrientation  = new long[sentiments.length];
   
    
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
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		
		for(Token t: tokens)
		{
			fd = cfd.getFrequencyDistribution(t.getCoveredText());
			if (fd != null)
			{
				long summeAnPunkten = 0;
				for (String key : fd.getKeys())	{
					summeAnPunkten += fd.getCount(key);
				}
				for(int i = 0; i < sentiments.length; ++i)
				{
					countsForOrientation[i] += Math.round((double)fd.getCount(sentiments[i])/summeAnPunkten * 100);
				}
			}
			
		}
		
		String sentimentOfJCas = "neg"; //weil auf den Trainignsdaten das häufigste Sentiment neg ist.
		
		
		long max = Long.MIN_VALUE;
		int stelle = -1; 
		for(int i = 0; i < countsForOrientation.length; ++i)
		{
			if(countsForOrientation[i] > max) {
				max = countsForOrientation[i];
				stelle = i;
			}
			countsForOrientation[i] = 0;
		}
//		for(int i = 0; i < countsForOrientation.length; ++i)
//		{
//			if(countsForOrientation[i] + 10 > max && i != stelle) {
//				System.out.printf("(%d, %d, %d) - %d - %d%n", countsForOrientation[0], countsForOrientation[1], countsForOrientation[2], max, countsForOrientation[i]);
//				System.out.println(countsForOrientation.length);
//			}
//			countsForOrientation[i] = 0;
//		}
		

		if (max > 0) sentimentOfJCas = sentiments[stelle];
		
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		di.setSentiment(sentimentOfJCas);
		di.addToIndexes();
		
	}
}
