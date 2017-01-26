package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

//import de.unidue.langtech.teaching.pp.mueller.type.DetectedLanguage;
//import de.unidue.langtech.teaching.pp.mueller.type.GoldLanguage;

public class Evaluator
    extends JCasAnnotator_ImplBase
{

	private int[] tp = {0,0,0};
	private int[] fp = {0,0,0};
	private int[] fn = {0,0,0};
	private int[] tn = {0,0,0};
	private String[] sentiments = {"pos", "neg", "other"};
    private int correct;
    private int nrOfDocuments;
    private int countOfOther;
    private int rightCountOfOther;
    
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
        countOfOther = 0;
        rightCountOfOther = 0;
    }
    
    
    /* 
     * This is called ONCE for each document
     */
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
    	DetectedInformation detec = JCasUtil.selectSingle(jcas, DetectedInformation.class);
    	
    	for (int i = 0; i < sentiments.length; ++i)
    	{
	    	if (gold.getSentiment().equals(sentiments[i]))
	    	{
	    		if(detec.getSentiment().equals(sentiments[i]))	    		{
	    			++tp[i];
	    		}else{
	    			++fn[i];
	    		}
	    	}
	    	else
	    	{
	    		if(detec.getSentiment().equals(sentiments[i]))
	    		{
	    			++fp[i];
	    		}
	    		else
	    		{
	    			++tn[i];
	    		}
	    	}
    	}	
    	if (gold.getSentiment().equals(detec.getSentiment())) ++correct;
    	if (gold.getSentiment().equals("other"))
    	{
    		++countOfOther;
    		if (detec.getSentiment().equals("other"))
    			++rightCountOfOther;
    	}
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
        double precision[] = {0,0,0};
        double recall[] = {0,0,0};
        double f1[] = {0,0,0};
        for(int i = 0; i < sentiments.length; ++i){
        	recall[i] = (double) tp[i] / (tp[i]+fn[i]);
        	precision[i] = (double) tp[i]/(tp[i]+fp[i]);
        	f1[i] = 2*((precision[i]*recall[i])/(precision[i]+recall[i]));
        }
        
        System.out.println("\n---------------------------------------------------------------------------------");
        System.out.println("label\\measure\t|Precision\t\t|Recall\t\t\t|F-measure\t|");
        System.out.println("---------------------------------------------------------------------------------");
        for(int i = 0; i < sentiments.length; ++i){
        	//|0,8101(401/495 %.2f
        	if (tp[i] == 0) System.out.printf(sentiments[i]+"\t\t|%.4f(%d/%d)        \t|%.4f(%d/%d)       \t|%.4f\t\t|%n", precision[i], tp[i], tp[i]+fp[i], recall[i], tp[i], tp[i]+fn[i], f1[i]);
        	else 	System.out.printf(sentiments[i]+"\t\t|%.4f(%d/%d)   \t|%.4f(%d/%d)\t\t|%.4f\t|%n", precision[i], tp[i], tp[i]+fp[i], recall[i], tp[i], tp[i]+fn[i], f1[i]);
        }
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("Accuracy:\t%.7f\t(%d/%d)%n", (double)correct/nrOfDocuments, correct, nrOfDocuments);
        
//        System.out.println("\n"+correct + " out of " + nrOfDocuments + " are correct.");
//        System.out.println((double)correct/nrOfDocuments*100 +"% is the Accurency.");
    }
}