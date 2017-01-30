package de.unidue.langtech.teaching.pp.mueller.pipeline;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetPosTagger;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;
import de.unidue.langtech.teaching.pp.mueller.annotators.AnalyseOfSentimentWithWordnet;
import de.unidue.langtech.teaching.pp.mueller.annotators.AnalyseWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.DetectionOfSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.Evaluator;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;
import de.unidue.langtech.teaching.pp.mueller.io.Writer;

public class Pipeline_analyse
{
	private static final File STOPWORD_FILE = new File("src/main/resources/stopwords_en.txt");
	private static final File WORDNET_FILE = new File("src/main/resources/SentiWordNet_3.0.0.txt");
	
	

    public static void main(String[] args)
        throws Exception
    {
    	buildFDs();
//    	detectOnTestdataViaFDs();
    	detectViaWordnet();
    }
    
    public static void buildFDs() throws ResourceInitializationException, UIMAException, IOException
    {
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/train.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(AnalyseWithFD.class)
         );
    }
    
    public static void detectOnTestdataViaFDs() throws ResourceInitializationException, UIMAException, IOException
    {
    	
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(StopWordRemover.class, StopWordRemover.PARAM_MODEL_LOCATION, STOPWORD_FILE),
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentiment.class),
                //AnalysisEngineFactory.createEngineDescription(Writer.class),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
        );
    }
    
    public static void detectViaWordnet() throws ResourceInitializationException, UIMAException, IOException
    {
    	
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
//                AnalysisEngineFactory.createEngineDescription(ArktweetPosTagger.class),
                AnalysisEngineFactory.createEngineDescription(StopWordRemover.class, StopWordRemover.PARAM_MODEL_LOCATION, STOPWORD_FILE),
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentiment.class),
                AnalysisEngineFactory.createEngineDescription(AnalyseOfSentimentWithWordnet.class, AnalyseOfSentimentWithWordnet.PARAM_WORDNET_FILE, WORDNET_FILE)
        );
    }
}