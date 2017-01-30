package de.unidue.langtech.teaching.pp.mueller.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedInformation;
//import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class Writer
    extends JCasAnnotator_ImplBase
{
	
	/*
	 * TODO! komlpett unfertig.
	 */
    public static final String PARAM_OUTPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    private File outputFile;
    private FileWriter writer;

    private List<String> lines;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
    	
		try {
			// new FileWriter(file) - falls die Datei bereits existiert
			// wird diese überschrieben
			writer = new FileWriter(outputFile);
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
		lines = new ArrayList<String>();
    }
    
    @Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
    	String line ="";
    	String tweet = aJCas.getDocumentText();
    	DetectedInformation di = new DetectedInformation(aJCas);
    	String target = di.getTarget();
    	String stance = di.getStance();
    	String opinion = di.getOpinion();
    	String sentiment = di.getSentiment();
    	
    	//tweet target stance opinion sentiment
    	line += "\""+tweet+"\","+ target + "," + stance+",\""+opinion + "\","+sentiment; //noch fertig machen.
    	
    	lines.add(line);
	}
    
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
    	try {
    		for(String line : lines)
        	{
    			writer.write(line);
        	}
    		writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    
}
