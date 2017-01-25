package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.*;

import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class AnalyseWithFDTest {

	@Test
	public void testProcess() throws UIMAException
	{
		JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("Das ist der erste Test");
		GoldInformation gold = new GoldInformation(jcas);
		gold.setTargetText("Targettext");
		gold.setSentiment("pos");
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		AnalyseWithFD ana = new AnalyseWithFD();
		AnalysisEngineDescription analyse = createEngineDescription(ana.getClass());
		AnalysisEngine analyseEngine = createEngine(analyse);
		analyseEngine.process(jcas);
		
		ConditionalFrequencyDistribution<String, String> cfd_sentiment = ana.getCfd_rawdata_sentiment();
		
		//Vergleichswert für die CFD
		ConditionalFrequencyDistribution<String, String> cfd_sentiment_verleichswert = new ConditionalFrequencyDistribution<String, String>();
		cfd_sentiment_verleichswert.inc("Das", "pos");
		cfd_sentiment_verleichswert.inc("ist", "pos");
		cfd_sentiment_verleichswert.inc("der", "pos");
		cfd_sentiment_verleichswert.inc("erste", "pos");
		cfd_sentiment_verleichswert.inc("Test", "pos");
		/*
		int richtig = 0;
		
		for(String condition: cfd_sentiment_verleichswert.getConditions())
		{
			if(cfd_sentiment.getCount(condition, "pos") == 1) richtig++;
		}
		*/
		
		assertEquals(cfd_sentiment, cfd_sentiment_verleichswert);
		//assertEquals(5, richtig);
	}

}
