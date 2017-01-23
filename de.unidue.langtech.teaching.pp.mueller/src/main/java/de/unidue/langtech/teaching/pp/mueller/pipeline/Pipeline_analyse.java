package de.unidue.langtech.teaching.pp.mueller.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.unidue.langtech.teaching.pp.mueller.annotators.AnalyseOfSentiment;
import de.unidue.langtech.teaching.pp.mueller.annotators.AnalyseWithFD;
import de.unidue.langtech.teaching.pp.mueller.annotators.TestCFDs;
import de.unidue.langtech.teaching.pp.mueller.io.Reader;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer;

public class Pipeline_analyse
{

    public static void main(String[] args)
        throws Exception
    {
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        Reader.class,
                        Reader.PARAM_INPUT_FILE, "src/main/resources/test.csv"
                ),
                AnalysisEngineFactory.createEngineDescription(ArktweetTokenizer.class),
                AnalysisEngineFactory.createEngineDescription(AnalyseOfSentiment.class)
        );
    }
}