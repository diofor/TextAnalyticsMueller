package de.unidue.langtech.teaching.pp.mueller.pipeline;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.annotators.BaselineSetter;
import de.unidue.langtech.teaching.pp.mueller.annotators.Evaluator;
import de.unidue.langtech.teaching.pp.mueller.annotators.FindMostFrequentSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.FindMostFrequentTarget;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;

public class PipelineBaseline {

	public static void main(String[] args)
	        throws Exception
    {
		findMostFrequentSentimentOnTraining();
//		findMostFrequentSentimentOnTest();
			
		findMostFrequentTargetOnTraining();
//		findMostFrequentTargetOnTest();
		
		calculateBasline("neg", "Hillary Clinton"); //Die Ergebnisse aus den beiden vorherigen Methoden. 
    }
	
	public static void calculateBasline(String mostFrequentSentimentOnTraining, String mostFrequentTargetOnTraining) throws ResourceInitializationException, UIMAException, IOException
	{
		System.out.printf("%nMit dem Sentiment %s und dem Target %s wird nun also eine Basline durch einfaches setzen dieser Werte für alle CAS-Elemente errechnet.%n", mostFrequentSentimentOnTraining, mostFrequentTargetOnTraining);
		
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/train.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(BaselineSetter.class,
                		BaselineSetter.PARAM_SENTIMENT, mostFrequentSentimentOnTraining,
                		BaselineSetter.PARAM_TARGET, mostFrequentTargetOnTraining),
                AnalysisEngineFactory.createEngineDescription(Evaluator.class)
                
        );
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
}
