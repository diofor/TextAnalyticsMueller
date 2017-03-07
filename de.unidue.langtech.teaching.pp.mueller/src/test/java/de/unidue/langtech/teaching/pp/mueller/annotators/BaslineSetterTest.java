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

public class BaslineSetterTest {
	
    	String documentTextForTest = "Das ist der erste Test mit zwei mal dem Wort der";
	String sentimentForTest = "pos";
	String targetForTest ="Targettext";
	
	@Test
	public void testProcess() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText(documentTextForTest);
		//Da die Jcas nicht durch den Reader erzeugt wird, muss hier ein DetectedInformation Objekt erzeugt und an  die Jcas gebunden werden.
		DetectedInformation df_addOneToTheJcas = new DetectedInformation(jcas);
		df_addOneToTheJcas.addToIndexes();
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		BaselineSetter  bsSet = new BaselineSetter();
		AnalysisEngine analyseEngine = createEngine(bsSet.getClass(), BaselineSetter.PARAM_SENTIMENT, sentimentForTest,	BaselineSetter.PARAM_TARGET, targetForTest);
		analyseEngine.process(jcas);
		
		DetectedInformation di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
		
		assertEquals(sentimentForTest, di.getSentiment());
		assertEquals(targetForTest, di.getTarget());
	}
}
