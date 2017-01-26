package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class AnalyseWithFDTest {
	
	@Test
	public void testCollectionProcessComplete() throws UIMAException
	{
		//todo
	}

	@Test
	public void testProcess() throws UIMAException
	{
		JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("Das ist der erste Test");
		GoldInformation gold = new GoldInformation(jcas);
		gold.setTargetText("Targettext");
		gold.setSentiment("pos");
		gold.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		AnalyseWithFD ana = new AnalyseWithFD();
		AnalysisEngine analyseEngine = createEngine(ana.getClass());
		analyseEngine.process(jcas);
		
//		ana.cfd_rawdata_sentiment.getConditions().forEach(x->System.out.println(x));
		
//		static ConditionalFrequencyDistribution<String, String> cfd_sentiment = ana.cfd_rawdata_sentiment;
	
		//Vergleichswert für die CFD
		ConditionalFrequencyDistribution<String, String> cfd_sentiment_verleichswert = new ConditionalFrequencyDistribution<String, String>();
		cfd_sentiment_verleichswert.inc("Das", "pos");
		cfd_sentiment_verleichswert.inc("ist", "pos");
		cfd_sentiment_verleichswert.inc("der", "pos");
		cfd_sentiment_verleichswert.inc("erste", "pos");
		cfd_sentiment_verleichswert.inc("Test", "pos");
		
		assertEquals(true, vergleiche(ana.getCfd_rawdata_sentiment(), cfd_sentiment_verleichswert)); 
	}
	
	private boolean vergleiche(ConditionalFrequencyDistribution<String, String> cfd1, ConditionalFrequencyDistribution<String, String> cfd2)
	{
		if (cfd1.getConditions().size() == cfd2.getConditions().size())
		{
			List<String> condition1 = new ArrayList<String>(cfd1.getConditions());
			List<String> condition2 = new ArrayList<String>(cfd2.getConditions());
			condition1.sort(null);
			condition2.sort(null);
			
			
			for(int i = 0; i < condition1.size(); ++i)
			{
				if(!(condition1.get(i).equals(condition2.get(i)))) return false;
			}
		}
		else
		{
			return false;
		}
		
		for(String condition : cfd1.getConditions())
		{
			for (String key :cfd1.getFrequencyDistribution(condition).getKeys())
			{
				if (!(cfd1.getCount(condition, key) == cfd2.getCount(condition, key))) return false; 
			}
		}
//		System.out.println(cfd1.getConditions().equals(cfd2.getConditions()));
		return true;
	}

}
