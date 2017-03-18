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
public class FindMostFrequentTargetTest {
	
	FindMostFrequentTarget fmfT;
	AnalysisEngine fmfTEngine;
	
	
	@Before
	public void beforeTestBuildFDs() throws UIMAException
	{
	    ArrayList<String> targetForCas = new ArrayList<>();
	    	
	    //Example One (0)
	    targetForCas.add("targetOne");
	    	
	    //Example Two (1)
		targetForCas.add("targetTwo");
		
		//Example Three (2)
		targetForCas.add("targetThree");
		
		//Example Four (3)
	    targetForCas.add("targetOne");
		
	    AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		
		fmfT = new FindMostFrequentTarget();
		fmfTEngine = createEngine(fmfT.getClass());
	    	
		JCas[] jcass = new JCas[targetForCas.size()];
		for (short i = 0; i < jcass.length; ++i){
		    jcass[i] = JCasFactory.createJCas();
		    jcass[i].setDocumentText("not relevant");
		    GoldInformation temp = new GoldInformation(jcass[i]);
		    temp.setTarget(targetForCas.get(i));
		    temp.addToIndexes();
		    segEngine.process(jcass[i]);
		    fmfTEngine.process(jcass[i]);
		}
	}
	
	@Test
	public void testOneProcess() throws UIMAException
	{
		assertEquals(2, fmfT.getCountOf("targetOne"));
		assertEquals(1, fmfT.getCountOf("targetTwo"));
		assertEquals(1, fmfT.getCountOf("targetThree"));
		assertEquals(4, fmfT.getSum());
	    assertEquals(null, fmfT.getMax());
	}
	
	
	@Test
	public void testTwoCollectionProcessComplete() throws UIMAException
	{
		fmfTEngine.collectionProcessComplete();
		assertEquals("targetOne", fmfT.getMax());
	}
}
