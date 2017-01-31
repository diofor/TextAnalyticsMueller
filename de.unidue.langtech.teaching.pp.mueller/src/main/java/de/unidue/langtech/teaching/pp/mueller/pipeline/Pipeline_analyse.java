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
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;
import de.tudarmstadt.ukp.dkpro.core.textcat.LanguageIdentifier;
import de.unidue.langtech.teaching.pp.mueller.annotators.AnalyseOfSentimentWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.DecisionOfSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.DetectionOfSentimentWithWordnet;
import de.unidue.langtech.teaching.pp.mueller.annotators.BuildFDsAndWriteToDisk;
import de.unidue.langtech.teaching.pp.mueller.annotators.DetectionOfSentimentWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.Evaluator;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;

public class Pipeline_analyse
{
	private static final File STOPWORD_FILE = new File("src/main/resources/stopwords_en.txt");
	private static final File WORDNET_FILE = new File("src/main/resources/SentiWordNet_3.0.0.txt");
	
	

    public static void main(String[] args)
        throws Exception
    {
    	buildFDs();
    	detectViaFDs();
    	detectViaFDsAdvanced();
    	detectViaFDandWordnet();
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
    
    public static void detectViaFDs() throws ResourceInitializationException, UIMAException, IOException
    {
    	
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(AnalyseOfSentimentWithFD.class),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
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
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentimentWithFD.class),
                AnalysisEngineFactory.createEngineDescription(DecisionOfSentiment.class),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
        );
    }
    
    public static void detectViaFDandWordnet() throws ResourceInitializationException, UIMAException, IOException
    {
    	
    	SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, "en"),
                AnalysisEngineFactory.createEngineDescription(StopWordRemover.class, StopWordRemover.PARAM_MODEL_LOCATION, STOPWORD_FILE),
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentimentWithFD.class),
                AnalysisEngineFactory.createEngineDescription(DetectionOfSentimentWithWordnet.class, DetectionOfSentimentWithWordnet.PARAM_WORDNET_FILE, WORDNET_FILE),
                AnalysisEngineFactory.createEngineDescription(DecisionOfSentiment.class),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
        );
    }
}