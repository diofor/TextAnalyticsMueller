package de.unidue.langtech.teaching.pp.mueller.pipeline;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.annotators.BaselineEvaluatorSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.BaselineEvaluatorTarget;
import de.unidue.langtech.teaching.pp.mueller.annotators.FindMostFrequentSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.FindMostFrequentTarget;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;

public class Pipeline_baseline {

	public static void main(String[] args)
	        throws Exception
	    {
//			findMostFrequentSentimentOnTraining();
//			findMostFrequentSentimentOnTest();
		
//			calculateSentimentBasline("neg");
			
			findMostFrequentTargetOnTraining();
			findMostFrequentTargetOnTest();
			
			
//			calculateTargetBasline("Hillary Clinton");
	    }

	public static void findMostFrequentSentimentOnTraining() throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipline um das häufigste Sentiment in den Trainingsdaten zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/train.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(FindMostFrequentSentiment.class)
        );
	}
	
	public static void findMostFrequentSentimentOnTest() throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipline um das häufigste Sentiment in den Testdaten zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(FindMostFrequentSentiment.class)
        );
	}
	
	
	public static void findMostFrequentTargetOnTraining() throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipline um das häufigste Sentiment in den Trainingsdaten zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/train.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(FindMostFrequentTarget.class)
        );
	}
	
	public static void findMostFrequentTargetOnTest() throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipline um das häufigste Sentiment in den Testdaten zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(FindMostFrequentTarget.class)
        );
	}
	
	public static void calculateSentimentBasline(String mostFrequentSentimentOnTraining) throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipeline um eine Basline mit dem häufigsten Sentiment zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(BaselineEvaluatorSentiment.class, BaselineEvaluatorSentiment.PARAM_SENTIMENT, mostFrequentSentimentOnTraining)
        );
	}
	
	public static void calculateTargetBasline(String mostTargetSentimentOnTraining) throws ResourceInitializationException, UIMAException, IOException
	{
		//Pipeline um eine Basline mit dem häufigsten Sentiment zu bestimmen.
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(BaselineEvaluatorTarget.class, BaselineEvaluatorTarget.PARAM_TARGET, mostTargetSentimentOnTraining)
        );
	}
}
