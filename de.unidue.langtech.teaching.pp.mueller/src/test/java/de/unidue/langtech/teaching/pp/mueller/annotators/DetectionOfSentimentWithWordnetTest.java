package de.unidue.langtech.teaching.pp.mueller.annotators;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import java.io.File;
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
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class DetectionOfSentimentWithWordnetTest {
	
	@Before
	public void beforeTestBuildFDs() throws UIMAException
	{
		ArrayList<String> documentTextForCas = new ArrayList<>();
	    ArrayList<String> sentimentForCas = new ArrayList<>();
	    	
	    //Example One (0)
	    documentTextForCas.add("happy happy happy happy happy fun fun"); //5 times 'happy' an twice times 'fun'
	    sentimentForCas.add("pos");
	    	
	    //Example Two (1)
		documentTextForCas.add("happy happy happy happy happy happy"); //6 times 'happy'
		sentimentForCas.add("neg");
		
	    AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		
		BuildFDsAndWriteToDisk bfwd = new BuildFDsAndWriteToDisk();
		AnalysisEngine bfwdEngine = createEngine(bfwd.getClass());
	    	
		JCas[] jcass = new JCas[2];
		for (short i = 0; i < jcass.length; ++i){
		    jcass[i] = JCasFactory.createJCas();
		    jcass[i].setDocumentText(documentTextForCas.get(i));
		    GoldInformation temp = new GoldInformation(jcass[i]);
		    temp.setSentiment(sentimentForCas.get(i));
		    temp.addToIndexes();
		    segEngine.process(jcass[i]);
		    bfwdEngine.process(jcass[i]);
		}
		bfwdEngine.collectionProcessComplete();
	}
	
	@Test
	public void testProcess() throws UIMAException
	{
	    	JCas jcas = JCasFactory.createJCas();
		jcas.setDocumentText("happy happy");
		
		AnalysisEngineDescription segmenter = createEngineDescription(ArktweetTokenizer.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);
		
		AnalysisEngine posEngine = createEngine(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, "en");
		posEngine.process(jcas);
		
		DetectionOfSentimentWithFD deSFD= new DetectionOfSentimentWithFD();
		AnalysisEngine deSFDEngine = createEngine(deSFD.getClass());
		deSFDEngine.process(jcas);
		
		DetectedInformation di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
		
		DecisionOfSentiment dofs = new DecisionOfSentiment();
		AnalysisEngine dofsEngine = createEngine(dofs.getClass());
		dofsEngine.process(jcas);
		
		System.out.printf("pos: %d%nneg: %d%nother: %d%nsentiment: %s%n", di.getSent_count_pos(), di.getSent_count_neg(), di.getSent_count_other(), di.getSentiment());
		
		//do the Tests
		assertEquals(true, di.getSent_count_neg() > di.getSent_count_pos());
		assertEquals(true, di.getSent_count_neg() > di.getSent_count_other());
		assertEquals("neg", di.getSentiment());
		
		DetectionOfSentimentWithWordnet dosww = new DetectionOfSentimentWithWordnet();
		AnalysisEngine doswwEngine = createEngine(dosww.getClass(), 
						DetectionOfSentimentWithWordnet.PARAM_WORDNET_FILE, new File("src/main/resources/SentiWordNet_3.0.0.txt"));
		doswwEngine.process(jcas);
		
		dofsEngine.process(jcas);
		System.out.printf("pos: %d%nneg: %d%nother: %d%nsentiment: %s%n", di.getSent_count_pos(), di.getSent_count_neg(), di.getSent_count_other(), di.getSentiment());
		
		assertEquals("pos", di.getSentiment());
	}
}
