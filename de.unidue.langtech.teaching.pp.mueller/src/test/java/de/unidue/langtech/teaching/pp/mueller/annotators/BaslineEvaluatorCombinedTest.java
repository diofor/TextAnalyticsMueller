package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class BaslineEvaluatorCombinedTest {
    	private final String documentTextForTest = "the text is not relevant";
	private final String defaultTargetFromBasline = "testTarget";
	private final String defaultSentimentFromBasline = "pos";

	@Test
	public void testProcessEverythingOkay() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		GoldInformation gold = new GoldInformation(jcas);
		gold.setSentiment(defaultSentimentFromBasline);
		gold.setTarget(defaultTargetFromBasline);
		gold.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		BaselineEvaluatorCombined bec = new BaselineEvaluatorCombined();
		AnalysisEngine becEngine = createEngine(bec.getClass(),  
												BaselineEvaluatorCombined.PARAM_TARGET, defaultTargetFromBasline, 
												BaselineEvaluatorCombined.PARAM_SENTIMENT, defaultSentimentFromBasline);
		
		//do the Tests
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(0, bec.getNrOfDocuments());
		
		becEngine.process(jcas);
		
		assertEquals(1, bec.getNrOfCorrectDocuments());
		assertEquals(1, bec.getNrOfDocuments());
	}
	
	@Test
	public void testProcessSentimentNotOkay() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		GoldInformation gold = new GoldInformation(jcas);
		gold.setSentiment("somethingDifferent"+defaultSentimentFromBasline);
		gold.setTarget(defaultTargetFromBasline);
		gold.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		BaselineEvaluatorCombined bec = new BaselineEvaluatorCombined();
		AnalysisEngine becEngine = createEngine(bec.getClass(),  
												BaselineEvaluatorCombined.PARAM_TARGET, defaultTargetFromBasline, 
												BaselineEvaluatorCombined.PARAM_SENTIMENT, defaultSentimentFromBasline);
		
		//do the Tests
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(0, bec.getNrOfDocuments());
		
		becEngine.process(jcas);
		
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(1, bec.getNrOfDocuments());
	}
	
	@Test
	public void testProcessTargetNotOkay() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		GoldInformation gold = new GoldInformation(jcas);
		gold.setSentiment(defaultSentimentFromBasline);
		gold.setTarget("somethingDifferent"+defaultTargetFromBasline);
		gold.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		BaselineEvaluatorCombined bec = new BaselineEvaluatorCombined();
		AnalysisEngine becEngine = createEngine(bec.getClass(),  
												BaselineEvaluatorCombined.PARAM_TARGET, defaultTargetFromBasline, 
												BaselineEvaluatorCombined.PARAM_SENTIMENT, defaultSentimentFromBasline);
		
		//do the Tests
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(0, bec.getNrOfDocuments());
		
		becEngine.process(jcas);
		
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(1, bec.getNrOfDocuments());
	}
	
	@Test
	public void testProcessNothingOkay() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		GoldInformation gold = new GoldInformation(jcas);
		gold.setSentiment("somethingDifferent"+defaultSentimentFromBasline);
		gold.setTarget("somethingDifferent"+defaultTargetFromBasline);
		gold.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		BaselineEvaluatorCombined bec = new BaselineEvaluatorCombined();
		AnalysisEngine becEngine = createEngine(bec.getClass(),  
												BaselineEvaluatorCombined.PARAM_TARGET, defaultTargetFromBasline, 
												BaselineEvaluatorCombined.PARAM_SENTIMENT, defaultSentimentFromBasline);
		
		//do the Tests
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(0, bec.getNrOfDocuments());
		
		becEngine.process(jcas);
		
		assertEquals(0, bec.getNrOfCorrectDocuments());
		assertEquals(1, bec.getNrOfDocuments());
	}
}
