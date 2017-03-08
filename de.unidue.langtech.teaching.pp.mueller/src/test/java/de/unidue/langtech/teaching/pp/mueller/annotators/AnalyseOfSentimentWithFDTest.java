package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class AnalyseOfSentimentWithFDTest {
      //-- TrainingData --
      	private static final String documentTextForTraingingOne = "Haus"; 
      	private static final String sentimentForTrainingOne = "pos";
    
      	private static final String documentTextForTrainingTwo = "Haus"; 
      	private static final String sentimentForTrainingTwo = "pos";
    
      	private static final String documentTextForTrainingThree = "Haus Baum"; 
      	private static final String sentimentForTrainingThree = "other";
    
      	//-- TestData --
      	private static final String documentTextForTestOne = "Das Haus ist toll";
      	private static final String sentimentForTestOne = "pos";
      	
      	private static final String documentTextForTestTwo = "Ich mag den Baum dort";
      	private static final String sentimentForTestTwo = "other";
      	
      	private static final String documentTextForTestThree = "Hier vermeide ich die Worte mit H und B von oben.";
      	private static final String sentimentForTestThree = "neg";
      	
      @Test
      public void testProcess() throws UIMAException
      {
          	//Create all JCas Elements and set the documentText
          	JCas[] jcasTraining = new JCas[3];
          	for(short i = 0; i<jcasTraining.length; ++i) jcasTraining[i] = JCasFactory.createJCas();

          	jcasTraining[0].setDocumentText(documentTextForTraingingOne);
          	GoldInformation goldOne = new GoldInformation(jcasTraining[0]);
          	goldOne.setSentiment(sentimentForTrainingOne);
          	goldOne.addToIndexes();
          	
          	jcasTraining[1].setDocumentText(documentTextForTrainingTwo);
          	GoldInformation goldTwo = new GoldInformation(jcasTraining[1]);
          	goldTwo.setSentiment(sentimentForTrainingTwo);
          	goldTwo.addToIndexes();
          	
          	jcasTraining[2].setDocumentText(documentTextForTrainingThree);
          	GoldInformation goldThree = new GoldInformation(jcasTraining[2]);
          	goldThree.setSentiment(sentimentForTrainingThree);
          	goldThree.addToIndexes();
          	
          	
          	JCas[] jcasTest = new JCas[3];
          	for(short i = 0; i<jcasTest.length; ++i) jcasTest[i] = JCasFactory.createJCas();
          	jcasTest[0].setDocumentText(documentTextForTestOne);
          	jcasTest[1].setDocumentText(documentTextForTestTwo);
          	jcasTest[2].setDocumentText(documentTextForTestThree);
          	
      	
          	//Do the Segmentation on the Cas-Elements
          	AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
      		AnalysisEngine segEngine = createEngine(segmenter);
      		for(JCas jcas : jcasTraining)   	{
          	    segEngine.process(jcas);
          	}
      		for(JCas jcas : jcasTest)   	{
          	    segEngine.process(jcas);
          	}
      	
      		//Build FDs with the JCas-Elements
      		BuildFDsAndWriteToDisk bfdWD = new BuildFDsAndWriteToDisk();
      		AnalysisEngine bfdWDEngine = createEngine(bfdWD.getClass());
      		for(JCas jcas : jcasTraining)   	{
          	    bfdWDEngine.process(jcas);
          	}
      		bfdWDEngine.collectionProcessComplete();
      	
      	
      		AnalyseOfSentimentWithFD asfd = new AnalyseOfSentimentWithFD();
      		AnalysisEngine analyseEngine = createEngine(asfd.getClass());
      		for(JCas jcas : jcasTest)   	{
      		    analyseEngine.process(jcas);
          	}
      	
      	
      		DetectedInformation diOne = JCasUtil.selectSingle(jcasTest[0], DetectedInformation.class);
      		DetectedInformation diTwo = JCasUtil.selectSingle(jcasTest[1], DetectedInformation.class);
      		DetectedInformation diThree = JCasUtil.selectSingle(jcasTest[2], DetectedInformation.class);
      	
      		assertEquals(sentimentForTestOne, diOne.getSentiment());
      		assertEquals(sentimentForTestTwo, diTwo.getSentiment());
      		assertEquals(sentimentForTestThree, diThree.getSentiment());
        	
	}
}



