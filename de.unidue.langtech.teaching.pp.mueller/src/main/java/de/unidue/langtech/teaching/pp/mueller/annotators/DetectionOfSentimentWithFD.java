package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.jcas.cas.LongArray;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
//import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;

public class DetectionOfSentimentWithFD extends JCasAnnotator_ImplBase
{
	//private FrequencyDistribution<String> fd;
    private ConditionalFrequencyDistribution<String, String> cfd;
    private FrequencyDistribution<String> fd;
    
    String[] sentiments = {"pos", "neg", "other"};
	long[] countsForSentiments  = new long[sentiments.length];
   
    
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
			fd = cfd.getFrequencyDistribution(t.getCoveredText().toLowerCase());
			if (fd != null)
			{
				long summeAnPunkten = 0;
				for (String key : fd.getKeys())	{
					summeAnPunkten += fd.getCount(key);
				}
				for(int i = 0; i < sentiments.length; ++i)
				{
					countsForSentiments[i] += Math.round((double)fd.getCount(sentiments[i])/summeAnPunkten * 100);
				}
			}
			
		}
		
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		di.setSent_count_pos(countsForSentiments[0]);
		di.setSent_count_neg(countsForSentiments[1]);
		di.setSent_count_other(countsForSentiments[2]);
		di.addToIndexes();

		//Werte fürd die nächste jCas zurücksetzen.
		for(int j = 0; j<countsForSentiments.length; ++j) countsForSentiments[j] = 0;
	}
}
