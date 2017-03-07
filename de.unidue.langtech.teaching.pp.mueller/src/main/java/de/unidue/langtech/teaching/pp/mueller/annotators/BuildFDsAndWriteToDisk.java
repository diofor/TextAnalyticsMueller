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

public class BuildFDsAndWriteToDisk extends JCasAnnotator_ImplBase
{
    protected static ConditionalFrequencyDistribution<String, String> cfd_target;
    protected static ConditionalFrequencyDistribution<String, String> cfd_sentiment;
    private int counter;
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        cfd_target = new ConditionalFrequencyDistribution<String, String>();
        cfd_sentiment = new ConditionalFrequencyDistribution<String, String>();
        counter = 0;
    }
    
    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		String cond = gold.getTarget();
		System.out.println("Condition ist: "+ cond);
		String sentiment = gold.getSentiment();
		for(Token t: tokens)
		{
			cfd_target.inc(t.getCoveredText().toLowerCase(), cond);
			cfd_sentiment.inc(t.getCoveredText().toLowerCase(), sentiment);
		}
		++counter;
    }
	
    @Override
    public void collectionProcessComplete() throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        CFDFileManager writer = new CFDFileManager();
        writer.write(cfd_target, "Target");
        writer.write(cfd_sentiment, "Sentiment");
        
        System.out.println("\nBEGIN Auswertung BuildFDsAndWriteToDisk");
        System.out.printf("Es wurden %d Tweets eingelesen und als CFDs abgelegt.%n", counter);
        System.out.println("ENDE Auswertung BuildFDsAndWriteToDisk\n");
    }
    
    public ConditionalFrequencyDistribution<String, String> getCfd_rawdata_target() {
		return cfd_target;
    }

    public ConditionalFrequencyDistribution<String, String> getCfd_rawdata_sentiment() {
    		return cfd_sentiment;
    }

}
