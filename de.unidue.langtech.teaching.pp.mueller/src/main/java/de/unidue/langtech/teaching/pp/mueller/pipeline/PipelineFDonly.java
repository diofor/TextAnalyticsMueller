package de.unidue.langtech.teaching.pp.mueller.pipeline;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;
import de.unidue.langtech.teaching.pp.mueller.annotators.BuildFDsAndWriteToDisk;
import de.unidue.langtech.teaching.pp.mueller.annotators.DecisionOfSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.DetectionAndDecisionOfTargetWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.DetectionOfSentimentWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.Evaluator;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;

public class PipelineFDonly
{
	private static final File STOPWORD_FILE = new File("src/main/resources/stopwords_en.txt");
	

    public static void main(String[] args)
        throws Exception
    {
    	System.out.println("#########################");
    	System.out.println("# Nur mit Hilfe von FDs #");
    	System.out.println("#########################\n");
    	buildFDs();
    	detectViaFDsAdvanced();
    }
    
    public static void buildFDs() throws ResourceInitializationException, UIMAException, IOException
    {
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/train.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(BuildFDsAndWriteToDisk.class)
         );
    }
    
    public static void detectViaFDsAdvanced() throws ResourceInitializationException, UIMAException, IOException
    {
    	
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(StopWordRemover.class, StopWordRemover.PARAM_MODEL_LOCATION, STOPWORD_FILE),
                AnalysisEngineFactory.createEngineDescription(DetectionAndDecisionOfTargetWithFD.class),
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentimentWithFD.class),
                AnalysisEngineFactory.createEngineDescription(DecisionOfSentiment.class),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
        );
    }
}