package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

//import de.unidue.langtech.teaching.pp.mueller.type.DetectedLanguage;
//import de.unidue.langtech.teaching.pp.mueller.type.GoldLanguage;

public class BaselineEvaluatorCombined
    extends JCasAnnotator_ImplBase
{

    protected static int correct;
    protected static int nrOfDocuments;
    public static final String PARAM_TARGET = "Target";
    @ConfigurationParameter(name = PARAM_TARGET, mandatory = true)
    private String defaultTargetFromBasline;
    public static final String PARAM_SENTIMENT = "Sentiment";
    @ConfigurationParameter(name = PARAM_SENTIMENT, mandatory = true)
    private String defaultSentimentFromBasline;
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        correct = 0;
        nrOfDocuments = 0;
    }
    
    
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
    	if (gold.getTarget().equals(defaultTargetFromBasline) && gold.getSentiment().equals(defaultSentimentFromBasline)) ++correct;
    	++nrOfDocuments;
    }


    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        System.out.printf("%d out of %d are correct, if you choose %s every time as Target and %s every time as Sentiment.%n", correct, nrOfDocuments, defaultTargetFromBasline, defaultSentimentFromBasline);
        System.out.printf("The combined Baseline is %.6f%%.", (double)correct/nrOfDocuments*100);
    }
    
    public int getNrOfCorrectDocuments(){
	return correct;
    }
    
    public int getNrOfDocuments(){
	return nrOfDocuments;
    }
}