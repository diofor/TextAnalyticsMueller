package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;

/*
 * Es soll anhand der Werte aus der CFD und dem Wert aus dem Wordnet eine Sinvolle Entscheidung 
 * für das Sentiment getroffen werden.
 */


public class DecisionOfSentiment extends JCasAnnotator_ImplBase{

	String[] sentiments = {"pos", "neg", "other"};
	long[] sentimentScores = new long[sentiments.length];
	double sentimentValue;
	
	protected static final int faktor = 8; //erfahrungswert
	
	
	//für interne Auswertung des Effekts des Wordnet-Wertes
	private int counter, up, down;
	
	@Override
	public void initialize(UimaContext context)
		throws ResourceInitializationException
	{
		super.initialize(context);
		sentimentValue = 0;
		
		counter = 0;
		up = 0;
		down = 0;
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		sentimentScores[0] = di.getSent_count_pos();
		sentimentScores[1] = di.getSent_count_neg();
		sentimentScores[2] = di.getSent_count_other();
		
		sentimentValue = di.getSentiment_value_wordnet();
		
		//Um 0-Values im SentimentScore aus zu schießen addiere ich auf allen Feldern
		//Das ändern nichst am Verhältnis
		for(int j = 0; j < sentimentScores.length; ++j)
			++sentimentScores[j];
		
		if (sentimentValue != 0)
		{
			if (sentimentValue < 0) //Die Einschätzung von Wordnet geht eher ins negative.
			{
				 sentimentScores[1] *= (Math.abs(sentimentValue)+1)*faktor; //+1 damit der Faktor immer größer als 1 ist.
				 ++down;
			}
			else //Die Einschätzung von Wordnet geht eher ins positve.
			{
				sentimentScores[0] *= (Math.abs(sentimentValue)+1)*faktor; //+1 damit der Faktor immer größer als 1 ist.
				++up;
			}
			++counter;
		}
		
		long max_score = 1;
		int stelle = -1; 
		for(int i = 0; i < sentimentScores.length; ++i)
		{
			if(sentimentScores[i] > max_score) {
				max_score = sentimentScores[i];
				stelle = i;
			}
		}
		
		
		
		if (stelle == -1) 
		{
			di.setSentiment("neg"); //weil auf den Trainignsdaten das häufigste Sentiment neg ist. #DefaultValue
		} else {
			di.setSentiment(sentiments[stelle]);
		}
		
		di.addToIndexes();
	}

	
	@Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        if (up > 0 || down > 0){
        	System.out.println("\nBEGIN Auswertung DecisionOfSentiment");
        	System.out.println("Hier ist direkt die Auswirkung von SentiWordnet auf die Ergebnisse zu sehen.");
	        System.out.printf("Es wurde %d (%.2f%%) mal ins positive und %d (%.2f%%) mal ins negative korrigiert.%n", up, (double)up/counter*100 , down, (double)down/counter*100);
	        System.out.println("ENDE Auswertung DecisionOfSentiment\n");
        }
        
    }
}
