package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldTarget;
import dkpro.toolbox.core.util.CFD;
import dkpro.toolbox.core.util.FD;

public class AnalyseWithFD extends JCasAnnotator_ImplBase
{
	private FrequencyDistribution<String> fd;
    private CFD<String, String> cfd;
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        fd = new FrequencyDistribution<String>();
        cfd = new CFD<String, String>();
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
		GoldTarget gold = JCasUtil.selectSingle(aJCas, GoldTarget.class);
		String cond = gold.getTargetText();
		for(Token t: tokens)
		{
			cfd.inc(cond, t.getCoveredText());
			//fd.inc(t.getCoveredText());
		}
		
	}
	
	/* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        /*
         * ToDo: es müssen gewöhnliche worte die in allen FD's vorkommen gefiltert werden. Sie behindern nur.
         */
        //FD<String> atheism, feminist, clinton, abortion, climate;
        
        for (String condition : cfd.getConditions())
        {
        	System.out.println("-------------------\n"+condition);
        	
        	/*
        	switch(condition){
        	case "Climate Change is a Real Concern": climate = cfd.getFreqDist(condition); break;
        	case "Legalization of Abortion": abortion = cfd.getFreqDist(condition); break;
        	case "Hillary Clinton": clinton = cfd.getFreqDist(condition); break;
        	case "Atheism": atheism = cfd.getFreqDist(condition); break;
        	case "Feminist Movement": feminist = cfd.getFreqDist(condition); break;
        	}
        	*/
        	
        	for (String t : cfd.getFreqDist(condition).getMostFrequentSamples(50))
        	{
        		System.out.println(t);
        	}
        	System.out.println("\n");
        }
        
        //.getMostFrequentSamples(50)
        
    }

}
