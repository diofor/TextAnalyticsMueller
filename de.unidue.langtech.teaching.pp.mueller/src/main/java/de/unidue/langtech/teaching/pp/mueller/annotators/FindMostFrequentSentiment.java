package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class FindMostFrequentSentiment  extends JCasAnnotator_ImplBase {

	protected static int pos, neg, other, sum;
	protected static String max; 
	
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
		switch (gold.getSentiment()) 
		{
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
//		max = "";
		if (pos > neg && pos > other) max = "pos"; 
		if (neg > pos && neg > other) max = "neg"; 
		if (other > neg && other > pos) max = "other";
		System.out.println("Die Verteilung der Sentiments:");
		System.out.printf("pos:\t%d \tvon %d - %.2f%% %n", pos, sum, ((double)pos/sum) * 100);
		System.out.printf("neg:\t%d \tvon %d - %.2f%% %n", neg, sum, ((double)neg/sum) * 100);
		System.out.printf("other:\t%d \tvon %d - %.2f%% %n", other, sum, ((double)other/sum) * 100);
		System.out.printf("The most frequent sentiment in this data was: \"%s\" %n %n", max);
	}
	
	public String getMax() {
		return max;
	}
	
	public int getPos() {
		return pos;
	}
	
	public int getNeg() {
		return neg;
	}

	public int getOther() {
		return other;
	}
	
	public int getSum() {
		return sum;
	}
}
