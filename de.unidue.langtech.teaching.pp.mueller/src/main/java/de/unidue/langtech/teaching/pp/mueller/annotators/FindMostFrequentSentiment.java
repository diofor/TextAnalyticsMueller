package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class FindMostFrequentSentiment  extends JCasAnnotator_ImplBase {

	int pos, neg, other, sum;
	
	@Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        pos = 0;
        neg = 0;
        other = 0;
        sum = 0;
    }
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		switch (gold.getSentiment()) {
		case "pos":	++pos; break;
		case "neg":	++neg; break; 
		case "other": ++other; break;
		}
		++sum;
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException
	{
		super.collectionProcessComplete();
		String max = "";
		if (pos > neg && pos > other) max = "pos"; 
		if (neg > pos && neg > other) max = "neg"; 
		if (other > neg && other > pos) max = "other"; 
		System.out.println("Das häufigste Sentiment ist in diesen Daten \"" + max+"\"");
		System.out.println("pos:\t" + pos);
		System.out.println("neg:\t" + neg);
		System.out.println("other:\t" + other);
		
	}

	
}
