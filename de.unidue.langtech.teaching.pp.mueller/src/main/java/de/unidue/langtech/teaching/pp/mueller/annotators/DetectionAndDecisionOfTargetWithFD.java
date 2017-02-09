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
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;

public class DetectionAndDecisionOfTargetWithFD extends JCasAnnotator_ImplBase
{
    private ConditionalFrequencyDistribution<String, String> cfd;
    private FrequencyDistribution<String> fd_token;
	private FrequencyDistribution<String> fd_tweet;
   
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        CFDFileManager fm = new CFDFileManager();
        cfd = fm.read("Target");
        fd_tweet = new FrequencyDistribution<String>();
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		
		for(Token t: tokens)
		{
			fd_token = cfd.getFrequencyDistribution(t.getCoveredText().toLowerCase());
			if (fd_token != null)
			{
				for(String keyForToken : fd_token.getKeys())
				{
					fd_tweet.addSample(keyForToken, Math.round(fd_token.getCount(keyForToken)/fd_token.getN()*100));
				}
			}
		}
		

		//Bestimmung der Targets welches am häufigsten vorkam.
		//Der normale Aufruf von getSampleWithMaxFreq geht hier nicht, da wir mit addSample hinzufügen und nicht mit inc.
		//String haeufigstesTarget = fd_tweet.getSampleWithMaxFreq();
		String haeufigstesTarget = "";
		long countHaeufigstesTarget = Integer.MIN_VALUE;
		for(String key : fd_tweet.getKeys())
		{
			if (fd_tweet.getCount(key)> countHaeufigstesTarget)
			{
				haeufigstesTarget = key;
				countHaeufigstesTarget = fd_tweet.getCount(key);
			}
		}
		
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		di.setTarget(haeufigstesTarget);
		di.addToIndexes();

		//Werte fürd die nächste jCas zurücksetzen.
		fd_tweet = new FrequencyDistribution<String>();
		//eventuell wenn der bug mit clear weg ist hier: fd_tweet.clear();
	}
}
