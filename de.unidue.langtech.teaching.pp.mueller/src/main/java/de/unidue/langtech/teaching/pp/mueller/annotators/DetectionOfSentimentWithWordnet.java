package de.unidue.langtech.teaching.pp.mueller.annotators;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.mueller.io.SentiWordNet;
import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;

public class DetectionOfSentimentWithWordnet extends JCasAnnotator_ImplBase
{
	public static final String PARAM_WORDNET_FILE = "WordnetFile";
    @ConfigurationParameter(name = PARAM_WORDNET_FILE, mandatory = true)
    private File wordnetFile;
    
    private SentiWordNet wordnet;
    private double sentimentValue;
    String[] sentiments = {"pos", "neg", "other"};
    
    private static final float seperator = 0.6f; //should set the border for evaluating an non sure tweet. 
    
    private static final boolean ausgabe = false;
    private int counter;
    private double min, max, ave;
    
   
    
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        try {
			wordnet = new SentiWordNet(wordnetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        sentimentValue = 0;
        
        counter = 0;
        min = 0;
        max = 0;
        ave = 0;
    }
	
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		DetectedInformation di = JCasUtil.selectSingle(aJCas, DetectedInformation.class);
		long[] scores = new long[3];
		scores[0] = di.getSent_count_pos();
		scores[1] = di.getSent_count_neg();
		scores[2] = di.getSent_count_other();
		
		long[] temp = Arrays.copyOf(scores, 3);
		Arrays.sort(temp);
		if ((temp[2]*seperator) <= temp[1])
		{
			Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
			
			for(Token t: tokens)
			{
				String pos_raw = t.getPos().getPosValue().toLowerCase();
				String pos = parse(pos_raw);
				
				if (!(pos.isEmpty())) sentimentValue += wordnet.extract(t.getCoveredText().toLowerCase(), pos);
			}
			++counter;
		}
		
		
		di.setSentiment_value_wordnet(sentimentValue);
		di.addToIndexes();
		
		
		//für die Auswertung, falls die Ausgabe gewünscht ist.
		if (sentimentValue > max) max = sentimentValue;
		if (sentimentValue < min) min = sentimentValue;
		if (sentimentValue != 0) ave += sentimentValue;
		
		
		//sentimentValue für die nächst jCas auf 0 setzen.
		sentimentValue = 0;
		
	}
	
	private String parse(String raw)
	{
		if (raw.charAt(0) == 'j') raw = "a";
		raw = raw.substring(0, 1);
		if (!("anvr".contains(raw))) raw = "";
		return raw;
	}

	@Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
		if (ausgabe == true) {
	        System.out.println("\nBEGIN Auswertung DetectionOfSentimentWithWordnet");
	        System.out.printf("Das minimum ist %.6f - das maximum ist %.6f %nDer Durchschnitt ist %.6f %n", min, max, ave/counter);
	        System.out.println("ENDE Auswertung DetectionOfSentimentWithWordnet\n");
        }

    }

}
