package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;

public class BaselineSetter
    extends JCasAnnotator_ImplBase
{

    public static final String PARAM_SENTIMENT = "Sentiment";
    @ConfigurationParameter(name = PARAM_SENTIMENT, mandatory = true)
    private String choosenSentiment;
    public static final String PARAM_TARGET = "Target";
    @ConfigurationParameter(name = PARAM_TARGET, mandatory = true)
    private String choosenTarget;
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
    }
    
    
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
	DetectedInformation di;
	try {
	    di = JCasUtil.selectSingle(jcas, DetectedInformation.class);
	} catch(IllegalArgumentException e) //is thrown if the Jcas doesent have an DetectedInformtion Objekt
	{
	    //add the missed Element to the Jcas
	    di = new DetectedInformation(jcas);
	    di.addToIndexes();
	}
    	
    	di.setSentiment(choosenSentiment);
    	di.setTarget(choosenTarget);
    	di.addToIndexes();
    }
}