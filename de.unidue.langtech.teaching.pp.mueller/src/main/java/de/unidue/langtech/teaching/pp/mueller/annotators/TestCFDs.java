package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import de.unidue.langtech.teaching.pp.mueller.io.CFDFileManager;

public class TestCFDs extends JCasAnnotator_ImplBase {
	ConditionalFrequencyDistribution<String, String> cfd;
	
		
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		
	}
	
	/* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        CFDFileManager fm = new CFDFileManager();
        cfd = fm.read("Target");
        for (String cond : cfd.getConditions())
        {
        	System.out.println(cond + " "+ cfd.getFrequencyDistribution(cond).getB());
        }
    }

}
