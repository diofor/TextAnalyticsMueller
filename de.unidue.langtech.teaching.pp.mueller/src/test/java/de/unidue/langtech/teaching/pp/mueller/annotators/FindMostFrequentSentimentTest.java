package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FindMostFrequentSentimentTest {
	
	FindMostFrequentSentiment fmfS;
	AnalysisEngine fmfSEngine;
	
	
	@Before
	public void beforeTestBuildFDs() throws UIMAException
	{
	    ArrayList<String> sentimentForCas = new ArrayList<>();
	    	
	    //Example One (0)
	    sentimentForCas.add("pos");
	    	
	    //Example Two (1)
		sentimentForCas.add("neg");
		
		//Example Three (2)
		sentimentForCas.add("other");
		
		//Example Four (3)
	    sentimentForCas.add("pos");
		
	    AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		
		fmfS = new FindMostFrequentSentiment();
		fmfSEngine = createEngine(fmfS.getClass());
	    	
		JCas[] jcass = new JCas[sentimentForCas.size()];
		for (short i = 0; i < jcass.length; ++i){
		    jcass[i] = JCasFactory.createJCas();
		    jcass[i].setDocumentText("not relevant");
		    GoldInformation temp = new GoldInformation(jcass[i]);
		    temp.setSentiment(sentimentForCas.get(i));
		    temp.addToIndexes();
		    segEngine.process(jcass[i]);
		    fmfSEngine.process(jcass[i]);
		}
	}
	
	@Test
	public void testOneProcess() throws UIMAException
	{
		assertEquals(2, fmfS.getPos());
		assertEquals(1, fmfS.getNeg());
		assertEquals(1, fmfS.getOther());
		assertEquals(4, fmfS.getSum());
	    assertEquals(null, fmfS.getMax());
	}
	
	
	@Test
	public void testTwoCollectionProcessComplete() throws UIMAException
	{
		fmfSEngine.collectionProcessComplete();
		assertEquals("pos", fmfS.getMax());
	}
}
