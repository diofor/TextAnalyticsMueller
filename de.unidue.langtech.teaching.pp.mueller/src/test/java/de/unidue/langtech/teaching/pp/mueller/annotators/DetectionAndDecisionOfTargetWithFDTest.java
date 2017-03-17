package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class DetectionAndDecisionOfTargetWithFDTest {
	
	@Before
	public void beforeTestBuildFDs() throws UIMAException
	{
		ArrayList<String> documentTextForCas = new ArrayList<>();
	    ArrayList<String> targetForCas = new ArrayList<>();
	    	
	    //Example One (0)
	    documentTextForCas.add("happy happy happy happy happy fun fun sad");
	    targetForCas.add("targetOne");
	    	
	    //Example Two (1)
		documentTextForCas.add("happy sad sad boring");
		targetForCas.add("targetTwo");
		
		//Example Three (2)
		documentTextForCas.add("today sad"); 
		targetForCas.add("targetThree");
		
	    AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		
		BuildFDsAndWriteToDisk bfwd = new BuildFDsAndWriteToDisk();
		AnalysisEngine bfwdEngine = createEngine(bfwd.getClass());
	    	
		JCas[] jcass = new JCas[documentTextForCas.size()];
		for (short i = 0; i < jcass.length; ++i){
		    jcass[i] = JCasFactory.createJCas();
		    jcass[i].setDocumentText(documentTextForCas.get(i));
		    GoldInformation temp = new GoldInformation(jcass[i]);
		    temp.setTarget(targetForCas.get(i));
		    temp.addToIndexes();
		    segEngine.process(jcass[i]);
		    bfwdEngine.process(jcass[i]);
		}
		bfwdEngine.collectionProcessComplete();
	}
	
	@Test
	//decide for pos
	public void testProcessOne() throws UIMAException
	{
	    JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("happy sad");
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		DetectionOfSentimentWithFD deSFD= new DetectionOfSentimentWithFD();
		AnalysisEngine deSFDEngine = createEngine(deSFD.getClass());
		deSFDEngine.process(jcas);
		
		DetectedInformation di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
		
		//do the Tests before
		assertEquals(null, di.getTarget());
		
		DetectionAndDecisionOfTargetWithFD dAdOfT = new DetectionAndDecisionOfTargetWithFD();
		AnalysisEngine dAdOfTEngine = createEngine(dAdOfT.getClass());
		dAdOfTEngine.process(jcas);
		
		//do the Tests after
		assertEquals("targetOne", di.getTarget());
	}
	
	
	@Test
	//decide for neg
	public void testProcessTwo() throws UIMAException
	{
	    JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("sad fun boring somethingelse");
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		DetectionOfSentimentWithFD deSFD= new DetectionOfSentimentWithFD();
		AnalysisEngine deSFDEngine = createEngine(deSFD.getClass());
		deSFDEngine.process(jcas);
		
		DetectedInformation di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
		
		//do the Tests before
		assertEquals(null, di.getTarget());
		
		DetectionAndDecisionOfTargetWithFD dAdOfT = new DetectionAndDecisionOfTargetWithFD();
		AnalysisEngine dAdOfTEngine = createEngine(dAdOfT.getClass());
		dAdOfTEngine.process(jcas);
		
		//do the Tests after
		System.out.println();
		assertEquals("targetTwo", di.getTarget());
	}
	
	@Test
	//decide for other
	public void testProcessThree() throws UIMAException
	{
	    JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("today");
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		DetectionOfSentimentWithFD deSFD= new DetectionOfSentimentWithFD();
		AnalysisEngine deSFDEngine = createEngine(deSFD.getClass());
		deSFDEngine.process(jcas);
		
		DetectedInformation di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
		
		//do the Tests before
		assertEquals(null, di.getTarget());
		
		DetectionAndDecisionOfTargetWithFD dAdOfT = new DetectionAndDecisionOfTargetWithFD();
		AnalysisEngine dAdOfTEngine = createEngine(dAdOfT.getClass());
		dAdOfTEngine.process(jcas);
		
		//do the Tests after
		assertEquals("targetThree", di.getTarget());
	}
	
}
