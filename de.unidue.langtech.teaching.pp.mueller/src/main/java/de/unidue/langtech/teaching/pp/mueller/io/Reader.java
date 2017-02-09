package de.unidue.langtech.teaching.pp.mueller.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;
//import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class Reader
    extends JCasCollectionReader_ImplBase
{
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File inputFile;

    private List<String> lines;
    private int currentLine;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        try {
            lines = FileUtils.readLines(inputFile);
            currentLine = 1;
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    public boolean hasNext()
        throws IOException, CollectionException
    {
        return currentLine < lines.size();
    }

    public Progress[] getProgress()
    {
        return null;
    }

    public void getNext(JCas aJCas)
        throws IOException, CollectionException
    {
    	String line = lines.get(currentLine);
    	line.replace("#SemST", " "); //verfälscht nur das Ergebnis
    	String tweet = "";
    	String target = "";
    	String stance = "";
    	String opinion = "";
    	String sentiment = "";
    	int index = 0;
    	int indexUpper = 0;
    	if (line.startsWith("\""))
    	{
    		//Tweet mit "
    		index = line.indexOf("\",", 2);
    		tweet = line.substring(1, index);
    		//Target - immer ohne "
    		indexUpper = line.indexOf(",", index+2);
    		target = line.substring(index+2, indexUpper);
    	}
    	else 
    	{
    		//Tweet ohne "
    		index = line.indexOf(",");
    		tweet = line.substring(0, index);
    		//Target - immer ohne " aber nach Tweet mit "
    		indexUpper = line.indexOf(",", index+1);
    		target = line.substring(index+1, indexUpper);
    	}
    	//Stance - immer ohne "
		index = indexUpper+1;
		indexUpper = line.indexOf(",", index);
		stance = line.substring(index, indexUpper);
		//opinion towards
		index = indexUpper+1;
		if (line.charAt(index) == '\"'){
			index++;
			indexUpper = line.indexOf("\",", index);
			opinion = line.substring(index, indexUpper);
			indexUpper++;
		}else
		{
			indexUpper = line.indexOf(",", index);
		}
		//sentiment
		index = ++indexUpper;
		sentiment = line.substring(index);

		
		target.trim();
		opinion.trim();

        // add gold standard value as annotation
        GoldInformation goldInf = new GoldInformation(aJCas);
        goldInf.setTarget(target);
        goldInf.setStance(stance);
        goldInf.setSentiment(sentiment);
        goldInf.setOpinion(opinion);
        
        goldInf.addToIndexes();
        
        DetectedInformation df = new DetectedInformation(aJCas);
        df.addToIndexes();
        
        tweet.trim();
        aJCas.setDocumentText(tweet);
        currentLine++;

    }

}
