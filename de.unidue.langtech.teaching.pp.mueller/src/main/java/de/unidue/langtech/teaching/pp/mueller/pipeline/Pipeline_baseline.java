package de.unidue.langtech.teaching.pp.mueller.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;
import de.unidue.langtech.teaching.pp.mueller.annotators.BaselineEvaluator;
import de.unidue.langtech.teaching.pp.mueller.annotators.FindMostFrequentSentiment;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;

public class Pipeline_baseline {

	public static void main(String[] args)
	        throws Exception
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
	        
	        //Pipeline um eine Basline mit dem häufigsten Sentiment zu bestimmen.
	        SimplePipeline.runPipeline(
	                CollectionReaderFactory.createReader(
	                        Reader.class,
	                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
	                ),
	                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
	                AnalysisEngineFactory.createEngineDescription(BaselineEvaluator.class)
	        );
	    }

}
