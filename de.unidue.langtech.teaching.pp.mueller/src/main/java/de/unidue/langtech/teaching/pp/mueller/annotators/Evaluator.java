package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.io.TableList;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class Evaluator
    extends JCasAnnotator_ImplBase
{
	protected static int[] tp_sentiment;
	protected static int[] fp_sentiment;
	protected static int[] fn_sentiment;
	protected static int[] tn_sentiment;
	protected static int[] tp_target;
	protected static int[] fp_target;
	protected static int[] fn_target;
	protected static int[] tn_target;
	private String[] sentiments = {"pos", "neg", "other"};
	private String[] targets = {"Legalization of Abortion", "Atheism", "Hillary Clinton", "Climate Change is a Real Concern", "Feminist Movement"};
    private int correct_sentiment;
    private int correct_target;
    protected static int nrOfDocuments;
    
   
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        correct_sentiment = 0;
        nrOfDocuments = 0;
        tp_sentiment = new int[sentiments.length];
        fp_sentiment = new int[sentiments.length];
        fn_sentiment = new int[sentiments.length];
        tn_sentiment = new int[sentiments.length];
        tp_target = new int[targets.length];
        fp_target = new int[targets.length];
        fn_target = new int[targets.length];
        tn_target = new int[targets.length];
    }
    
    
    
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
    	DetectedInformation detec = JCasUtil.selectSingle(jcas, DetectedInformation.class);
    	
    	//Überprüfungen für das Sentiment (bestimmen von tp fn fp tn)
    	for (int i = 0; i < sentiments.length; ++i)
    	{
	    	if (gold.getSentiment().equals(sentiments[i]))
	    	{
	    		if(detec.getSentiment().equals(sentiments[i]))	    		
	    		{
	    			++tp_sentiment[i];
	    			++correct_sentiment;
	    		}else{
	    			++fn_sentiment[i];
	    		}
	    	}
	    	else
	    	{
	    		if(detec.getSentiment().equals(sentiments[i]))
	    		{
	    			++fp_sentiment[i];
	    		}
	    		else
	    		{
	    			++tn_sentiment[i];
	    		}
	    	}
    	}	
    	
    	//Überprüfungen für das Target (bestimmen von tp fn fp tn)
    	for (int i = 0; i < targets.length; ++i)
    	{
	    	if (gold.getTarget().equals(targets[i]))
	    	{
	    		if(detec.getTarget().equals(targets[i]))	    		
	    		{
	    			++tp_target[i];
	    			++correct_target;
	    		}else{
	    			++fn_target[i];
	    		}
	    	}
	    	else
	    	{
	    		if(detec.getTarget().equals(targets[i]))
	    		{
	    			++fp_target[i];
	    		}
	    		else
	    		{
	    			++tn_target[i];
	    		}
	    	}
    	}	
    	++nrOfDocuments;
    }


    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        //Sentiment
        double precision_sentiment[] = {0,0,0};
        double recall_sentiment[] = {0,0,0};
        double f1_sentiment[] = {0,0,0};
        for(int i = 0; i < sentiments.length; ++i){
        	recall_sentiment[i] = (double) tp_sentiment[i] / (tp_sentiment[i]+fn_sentiment[i]);
        	precision_sentiment[i] = (double) tp_sentiment[i]/(tp_sentiment[i]+fp_sentiment[i]);
        	f1_sentiment[i] = 2*((precision_sentiment[i]*recall_sentiment[i])/(precision_sentiment[i]+recall_sentiment[i]));
        }
                        
        System.out.println(); //leerzeile
        TableList results_sentiment = new TableList(4, "Sentiment", "Precision", "Recall", "F-measure");
        for(int i = 0; i < sentiments.length; ++i){
        	String precision_text = String.format("%.4f (%d/%d)", precision_sentiment[i], tp_sentiment[i], tp_sentiment[i]+fp_sentiment[i]);
        	String recall_text = String.format("%.4f (%d/%d)", recall_sentiment[i], tp_sentiment[i], tp_sentiment[i]+fn_sentiment[i]); 
        	String fmeasure_text = String.format("%.4f", f1_sentiment[i]);
	        results_sentiment.addRow(sentiments[i], precision_text, recall_text, fmeasure_text);
        }
        results_sentiment.print();
        System.out.printf("%nAccuracy:   %.7f (%d/%d)%n", (double)correct_sentiment/nrOfDocuments, correct_sentiment, nrOfDocuments);
        
        
        //Target
        double precision_target[] = {0,0,0,0,0};
        double recall_target[] = {0,0,0,0,0};
        double f1_target[] = {0,0,0,0,0};
        for(int i = 0; i < targets.length; ++i){
        	recall_target[i] = (double) tp_target[i] / (tp_target[i]+fn_target[i]);
        	precision_target[i] = (double) tp_target[i]/(tp_target[i]+fp_target[i]);
        	f1_target[i] = 2*((precision_target[i]*recall_target[i])/(precision_target[i]+recall_target[i]));
        }
        
        System.out.println("\n "); //leerzeile
        TableList results_target = new TableList(4, "Target", "Precision", "Recall", "F-measure");
        for(int i = 0; i < targets.length; ++i){
        	String precision_text = String.format("%.4f (%d/%d)", precision_target[i], tp_target[i], tp_target[i]+fp_target[i]);
        	String recall_text = String.format("%.4f (%d/%d)", recall_target[i], tp_target[i], tp_target[i]+fn_target[i]); 
        	String fmeasure_text = String.format("%.4f", f1_target[i]);
	        results_target.addRow(targets[i], precision_text, recall_text, fmeasure_text);
        }
        results_target.print();
        System.out.printf("%nAccuracy:   %.7f (%d/%d)%n", (double)correct_target/nrOfDocuments, correct_target, nrOfDocuments);
    }
    
    //for the JUnit tests
    public int getNumberOfDocuments() {
    	return nrOfDocuments;
    }
    
    public int[] getTP_Sentiment() {
    	return tp_sentiment;
    }
    
    public int[] getFP_Sentiment() {
    	return fp_sentiment;
    }
    
    public int[] getFN_Sentiment() {
    	return fn_sentiment;
    }
    
    public int[] getTN_Sentiment() {
    	return tn_sentiment;
    }
    
    public int[] getTP_Target() {
    	return tp_target;
    }
    
    public int[] getFP_Target() {
    	return fp_target;
    }
    
    public int[] getFN_Target() {
    	return fn_target;
    }
    
    public int[] getTN_Target() {
    	return tn_target;
    }
}