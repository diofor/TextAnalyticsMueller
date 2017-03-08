package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;
import de.unidue.langtech.teaching.pp.mueller.io.vergleicheCFDs;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class BuildFDsAndWriteToDiskTest {
	
    	String documentTextForTest = "Das ist der erste Test mit zwei mal dem Wort der";
	String sentimentForTest = "pos";
	String targetForTest ="Targettext";
	
	JCas jcas = null;
	
	//Vergleichswert für die CFD
	ConditionalFrequencyDistribution<String, String> cfd_sentiment_verleichswert;
	ConditionalFrequencyDistribution<String, String> cfd_target_verleichswert;
    
	BuildFDsAndWriteToDisk ana;
	AnalysisEngine analyseEngine;
	
	@Before
	public void doBeforeStartTesting() throws UIMAException
	{
	    	jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		GoldInformation gold = new GoldInformation(jcas);
		gold.setTarget(targetForTest);
		gold.setSentiment(sentimentForTest);
		gold.addToIndexes();
		
		ana = new BuildFDsAndWriteToDisk();
		analyseEngine = createEngine(ana.getClass());
		
		cfd_sentiment_verleichswert = new ConditionalFrequencyDistribution<String, String>();
		cfd_target_verleichswert = new ConditionalFrequencyDistribution<String, String>();
	}
	
	@Test
	public void testProcess() throws UIMAException
	{
		
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		analyseEngine.process(jcas);
		
//		ana.cfd_rawdata_sentiment.getConditions().forEach(x->System.out.println(x));
		
//		static ConditionalFrequencyDistribution<String, String> cfd_sentiment = ana.cfd_rawdata_sentiment;
		
		for(String wort : documentTextForTest.split(" "))
		{
		    cfd_sentiment_verleichswert.inc(wort.toLowerCase() , sentimentForTest);
		    cfd_target_verleichswert.inc(wort.toLowerCase() , targetForTest);
		}
		
		assertEquals(true, vergleicheCFDs.vergleiche(ana.getCfd_rawdata_sentiment(), cfd_sentiment_verleichswert));
		assertEquals(true, vergleicheCFDs.vergleiche(ana.getCfd_rawdata_target(), cfd_target_verleichswert));
	}
	
	@Test
	public void testCollectionProcessComplete() throws UIMAException
	{
	    	analyseEngine.collectionProcessComplete();
	    	
		CFDFileManager fm = new CFDFileManager();
		ConditionalFrequencyDistribution<String , String> cfd_target_fromFile = fm.read("Target");
		ConditionalFrequencyDistribution<String , String> cfd_sentiment_fromFile = fm.read("Sentiment");
		
		assertEquals(true,  vergleicheCFDs.vergleiche(cfd_target_fromFile, cfd_target_verleichswert));
		assertEquals(true,  vergleicheCFDs.vergleiche(cfd_sentiment_fromFile, cfd_sentiment_verleichswert));
	}

	
}
