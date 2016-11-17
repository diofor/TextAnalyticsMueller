package de.unidue.langtech.teaching.pp.mueller;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;

public class SomeOtherExample extends JCasAnnotator_ImplBase {

	public static final String INPUT= "InputFile";
    @ConfigurationParameter(name = INPUT, mandatory = true, defaultValue = "Eingabe")
    private String inputString;
    
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(inputString);	
	}

}
