package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class EvaluatorTest {
	
	@Test
	public void testProcessSentimentTP() throws UIMAException
	{
		String[] sentiments = {"pos", "neg", "other"};
		
		for(int j = 0; j < sentiments.length; ++j){
			//d for detected and g for gold
			
			String gSentiment = sentiments[j];
			String dSentiment = sentiments[j];
			
			int[] comparisonArraySentiment = new int[sentiments.length];
			comparisonArraySentiment[j] = 1;
			
			
		    JCas jcas = JCasFactory.createJCas();
			jcas.setDocumentText("not relevant");
			
			GoldInformation gTemp = new GoldInformation(jcas);
		    gTemp.setSentiment(gSentiment);
		    gTemp.setTarget("");
		    gTemp.addToIndexes();
		    
		    DetectedInformation dTemp = new DetectedInformation(jcas);
		    dTemp.setSentiment(dSentiment);
		    dTemp.setTarget("");
		    dTemp.addToIndexes();
		    
		    Evaluator ev = new Evaluator();
		    AnalysisEngine evEngine = createEngine(ev.getClass());
		    evEngine.process(jcas);
		    
			assertEquals(comparisonArraySentiment.length, ev.getTP_Sentiment().length);
			for(int i = 0; i < comparisonArraySentiment.length; ++i)
			{
				assertEquals(comparisonArraySentiment[i], ev.getTP_Sentiment()[i]);
			}
			
			assertEquals(1, ev.getNumberOfDocuments());
		}
	}
	
	
	@Test
	public void testProcessTargetTP() throws UIMAException
	{
		String[] targets = {"Legalization of Abortion", "Atheism", "Hillary Clinton", "Climate Change is a Real Concern", "Feminist Movement"};
		
		for(int j = 0; j < targets.length; ++j){
			//d for detected and g for gold
			
			String gTarget = targets[j];
			String dTarget = targets[j];
			
			int[] comparisonArrayTarget = new int[targets.length];
			comparisonArrayTarget[j] = 1;
			
			
		    JCas jcas = JCasFactory.createJCas();
			jcas.setDocumentText("not relevant");
			
			GoldInformation gTemp = new GoldInformation(jcas);
			gTemp.setSentiment("");
			gTemp.setTarget(gTarget);
		    gTemp.addToIndexes();
		    
		    DetectedInformation dTemp = new DetectedInformation(jcas);
		    dTemp.setSentiment("");
		    dTemp.setTarget(dTarget);
		    dTemp.addToIndexes();
		    
		    Evaluator ev = new Evaluator();
		    AnalysisEngine evEngine = createEngine(ev.getClass());
		    evEngine.process(jcas);
		    
			assertEquals(comparisonArrayTarget.length, ev.getTP_Target().length);
			for(int i = 0; i < comparisonArrayTarget.length; ++i)
			{
				assertEquals(comparisonArrayTarget[i], ev.getTP_Target()[i]);
			}
			
			assertEquals(1, ev.getNumberOfDocuments());
		}
	}
	
	@Test
	public void testProcessSentimentFP() throws UIMAException
	{
		String[] sentiments = {"pos", "neg", "other"};
		
		for(int j = 0; j < sentiments.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				String gSentiment = sentiments[(j+h)%3];
				String dSentiment = sentiments[j];
				
				int[] comparisonArraySentiment 		= new int[sentiments.length];
				int[] otherComparisonArraySentiment = new int[sentiments.length];
				comparisonArraySentiment[j] = 1;
				
				
			    JCas jcas = JCasFactory.createJCas();
				jcas.setDocumentText("not relevant");
				
				GoldInformation gTemp = new GoldInformation(jcas);
			    gTemp.setSentiment(gSentiment);
			    gTemp.setTarget("");
			    gTemp.addToIndexes();
			    
			    DetectedInformation dTemp = new DetectedInformation(jcas);
			    dTemp.setSentiment(dSentiment);
			    dTemp.setTarget("");
			    dTemp.addToIndexes();
			    
			    Evaluator ev = new Evaluator();
			    AnalysisEngine evEngine = createEngine(ev.getClass());
			    evEngine.process(jcas);
			    
			    assertEquals(otherComparisonArraySentiment[j], ev.getTP_Sentiment()[j]); 	// TP
			    assertEquals(comparisonArraySentiment[j], ev.getFP_Sentiment()[j]); 		// FP
			    assertEquals(otherComparisonArraySentiment[j], ev.getFN_Sentiment()[j]);	// FN
			    assertEquals(otherComparisonArraySentiment[j], ev.getTN_Sentiment()[j]);	// TN
				
				assertEquals(1, ev.getNumberOfDocuments());
			}
		}
	}
	
	@Test
	public void testProcessTargetFP() throws UIMAException
	{
		String[] targets = {"Legalization of Abortion", "Atheism", "Hillary Clinton", "Climate Change is a Real Concern", "Feminist Movement"};
		
		for(int j = 0; j < targets.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				String gTarget = targets[(j+h)%3];
				String dTarget = targets[j];
				
				int[] comparisonArrayTarget 		= new int[targets.length];
				int[] otherComparisonArrayTarget 	= new int[targets.length];
				comparisonArrayTarget[j] = 1;
				
				
			    JCas jcas = JCasFactory.createJCas();
				jcas.setDocumentText("not relevant");
				
				GoldInformation gTemp = new GoldInformation(jcas);
				gTemp.setSentiment("");
				gTemp.setTarget(gTarget);
			    gTemp.addToIndexes();
			    
			    DetectedInformation dTemp = new DetectedInformation(jcas);
			    dTemp.setSentiment("");
			    dTemp.setTarget(dTarget);
			    dTemp.addToIndexes();
			    
			    Evaluator ev = new Evaluator();
			    AnalysisEngine evEngine = createEngine(ev.getClass());
			    evEngine.process(jcas);
			    
			    assertEquals(otherComparisonArrayTarget[j], ev.getTP_Target()[j]); 	// TP
			    assertEquals(comparisonArrayTarget[j], ev.getFP_Target()[j]); 		// FP
			    assertEquals(otherComparisonArrayTarget[j], ev.getFN_Target()[j]);	// FN
			    assertEquals(otherComparisonArrayTarget[j], ev.getTN_Target()[j]);	// TN
				
				assertEquals(1, ev.getNumberOfDocuments());
			}
		}
	}
	
	@Test
	public void testProcessSentimentFN() throws UIMAException
	{
		String[] sentiments = {"pos", "neg", "other"};
		
		for(int j = 0; j < sentiments.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				String gSentiment = sentiments[j];
				String dSentiment = sentiments[(j+h)%3];
				
				int[] comparisonArraySentiment 		= new int[sentiments.length];
				int[] otherComparisonArraySentiment = new int[sentiments.length];
				comparisonArraySentiment[j] = 1;
				
				
			    JCas jcas = JCasFactory.createJCas();
				jcas.setDocumentText("not relevant");
				
				GoldInformation gTemp = new GoldInformation(jcas);
			    gTemp.setSentiment(gSentiment);
			    gTemp.setTarget("");
			    gTemp.addToIndexes();
			    
			    DetectedInformation dTemp = new DetectedInformation(jcas);
			    dTemp.setSentiment(dSentiment);
			    dTemp.setTarget("");
			    dTemp.addToIndexes();
			    
			    Evaluator ev = new Evaluator();
			    AnalysisEngine evEngine = createEngine(ev.getClass());
			    evEngine.process(jcas);
			    
			    assertEquals(otherComparisonArraySentiment[j], ev.getTP_Sentiment()[j]); 	// TP
			    assertEquals(otherComparisonArraySentiment[j], ev.getFP_Sentiment()[j]); 	// FP
			    assertEquals(comparisonArraySentiment[j], ev.getFN_Sentiment()[j]);			// FN
			    assertEquals(otherComparisonArraySentiment[j], ev.getTN_Sentiment()[j]);	// TN
				
				assertEquals(1, ev.getNumberOfDocuments());
			}
		}
	}
	
	
	@Test
	public void testProcessTargetFN() throws UIMAException
	{
		String[] targets = {"Legalization of Abortion", "Atheism", "Hillary Clinton", "Climate Change is a Real Concern", "Feminist Movement"};
		
		for(int j = 0; j < targets.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				String gTarget = targets[j];
				String dTarget = targets[(j+h)%3];
				
				int[] comparisonArrayTarget 		= new int[targets.length];
				int[] otherComparisonArrayTarget = new int[targets.length];
				comparisonArrayTarget[j] = 1;
				
				
			    JCas jcas = JCasFactory.createJCas();
				jcas.setDocumentText("not relevant");
				
				GoldInformation gTemp = new GoldInformation(jcas);
				gTemp.setSentiment("");
				gTemp.setTarget(gTarget);
			    gTemp.addToIndexes();
			    
			    DetectedInformation dTemp = new DetectedInformation(jcas);
			    dTemp.setSentiment("");
			    dTemp.setTarget(dTarget);
			    dTemp.addToIndexes();
			    
			    Evaluator ev = new Evaluator();
			    AnalysisEngine evEngine = createEngine(ev.getClass());
			    evEngine.process(jcas);
			    
			    assertEquals(otherComparisonArrayTarget[j], ev.getTP_Target()[j]); 	// TP
			    assertEquals(otherComparisonArrayTarget[j], ev.getFP_Target()[j]); 	// FP
			    assertEquals(comparisonArrayTarget[j], ev.getFN_Target()[j]);		// FN
			    assertEquals(otherComparisonArrayTarget[j], ev.getTN_Target()[j]);	// TN
				
				assertEquals(1, ev.getNumberOfDocuments());
			}
		}
	}
	
	@Test
	public void testProcessSentimentTN() throws UIMAException
	{
		String[] sentiments = {"pos", "neg", "other"};
		
		for(int j = 0; j < sentiments.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				for(short g = 1; g < 3 ; ++g)
				{
					String gSentiment = sentiments[(j+g)%3];
					String dSentiment = sentiments[(j+h)%3];
					
					int[] comparisonArraySentiment 		= new int[sentiments.length];
					int[] otherComparisonArraySentiment = new int[sentiments.length];
					comparisonArraySentiment[j] = 1;
					
					
				    JCas jcas = JCasFactory.createJCas();
					jcas.setDocumentText("not relevant");
					
					GoldInformation gTemp = new GoldInformation(jcas);
				    gTemp.setSentiment(gSentiment);
				    gTemp.setTarget("");
				    gTemp.addToIndexes();
				    
				    DetectedInformation dTemp = new DetectedInformation(jcas);
				    dTemp.setSentiment(dSentiment);
				    dTemp.setTarget("");
				    dTemp.addToIndexes();
				    
				    Evaluator ev = new Evaluator();
				    AnalysisEngine evEngine = createEngine(ev.getClass());
				    evEngine.process(jcas);
				    
				    assertEquals(otherComparisonArraySentiment[j], ev.getTP_Sentiment()[j]); 	// TP
				    assertEquals(otherComparisonArraySentiment[j], ev.getFP_Sentiment()[j]); 	// FP
				    assertEquals(otherComparisonArraySentiment[j], ev.getFN_Sentiment()[j]);	// FN
				    assertEquals(comparisonArraySentiment[j], ev.getTN_Sentiment()[j]);			// TN
					
					assertEquals(1, ev.getNumberOfDocuments());
				}
			}
		}
	}
	
	@Test
	public void testProcessTargetTN() throws UIMAException
	{
		String[] targets = {"pos", "neg", "other"};
		
		for(int j = 0; j < targets.length; ++j){
			//d for detected and g for gold
			for(short h = 1; h < 3 ; ++h)
			{
				for(short g = 1; g < 3 ; ++g)
				{
					String gTarget = targets[(j+g)%3];
					String dTarget = targets[(j+h)%3];
					
					int[] comparisonArrayTarget 		= new int[targets.length];
					int[] otherComparisonArrayTarget = new int[targets.length];
					comparisonArrayTarget[j] = 1;
					
					
				    JCas jcas = JCasFactory.createJCas();
					jcas.setDocumentText("not relevant");
					
					GoldInformation gTemp = new GoldInformation(jcas);
					gTemp.setSentiment("");
					gTemp.setTarget(gTarget);
				    gTemp.addToIndexes();
				    
				    DetectedInformation dTemp = new DetectedInformation(jcas);
				    dTemp.setSentiment("");
				    dTemp.setTarget(dTarget);
				    dTemp.addToIndexes();
				    
				    Evaluator ev = new Evaluator();
				    AnalysisEngine evEngine = createEngine(ev.getClass());
				    evEngine.process(jcas);
				    
				    assertEquals(otherComparisonArrayTarget[j], ev.getTP_Target()[j]); 	// TP
				    assertEquals(otherComparisonArrayTarget[j], ev.getFP_Target()[j]); 	// FP
				    assertEquals(otherComparisonArrayTarget[j], ev.getFN_Target()[j]);	// FN
				    assertEquals(comparisonArrayTarget[j], ev.getTN_Target()[j]);		// TN
					
					assertEquals(1, ev.getNumberOfDocuments());
				}
			}
		}
	}
	
}
