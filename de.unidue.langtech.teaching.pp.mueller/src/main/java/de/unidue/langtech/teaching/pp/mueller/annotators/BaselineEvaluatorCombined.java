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

    private int correct;
    private int nrOfDocuments;
    public static final String PARAM_TARGET = "Target";
    @ConfigurationParameter(name = PARAM_TARGET, mandatory = true)
    private String choosenTarget;
    public static final String PARAM_SENTIMENT = "Sentiment";
    @ConfigurationParameter(name = PARAM_SENTIMENT, mandatory = true)
    private String choosenSentiment;
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        correct = 0;
        nrOfDocuments = 0;
    }
    
    
    /* 
     * This is called ONCE for each document
     */
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
    	if (gold.getTarget().equals(choosenTarget) && gold.getSentiment().equals(choosenSentiment)) ++correct;
    	++nrOfDocuments;
    }


    /* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        System.out.printf("%d out of %d are correct, if you choose %s every time as Target and %s every time as Sentiment.%n", correct, nrOfDocuments, choosenTarget, choosenSentiment);
        System.out.printf("The combined Baseline is %.6f%%.", (double)correct/nrOfDocuments*100);
    }
}