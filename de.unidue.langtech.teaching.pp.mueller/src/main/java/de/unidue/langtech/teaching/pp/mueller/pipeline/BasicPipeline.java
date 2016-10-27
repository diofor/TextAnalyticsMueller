package de.unidue.langtech.teaching.pp.mueller.pipeline;

import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.unidue.langtech.teaching.pp.mueller.BaselineExample;
import de.unidue.langtech.teaching.pp.mueller.EvaluatorExample;
import de.unidue.langtech.teaching.pp.mueller.ReaderExample;

public class BasicPipeline
{

    public static void main(String[] args)
        throws Exception
    {
        SimplePipeline.runPipeline(
                CollectionReaderFactory.createReader(
                        ReaderExample.class,
                        ReaderExample.PARAM_INPUT_FILE, "src/test/resources/test/input.txt"
                ),
                AnalysisEngineFactory.createEngineDescription(BaselineExample.class),
                AnalysisEngineFactory.createEngineDescription(EvaluatorExample.class)
        );
    }
}
