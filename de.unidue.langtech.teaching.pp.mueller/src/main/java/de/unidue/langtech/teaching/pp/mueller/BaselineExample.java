package de.unidue.langtech.teaching.pp.mueller;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.DetectedLanguage;

/**
 * The baseline always identifies "EN" as the document language.
 * 
 * @author zesch
 *
 */
public class BaselineExample
    extends JCasAnnotator_ImplBase
{


    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        System.out.println("Document is: " + jcas.getDocumentText());
        
        Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
        System.out.println("CAS contains " + tokens.size() + " tokens.");
        
        DetectedLanguage languageAnno = new DetectedLanguage(jcas);
        
        Integer language[] = {0,0,0}; //0 equals EN, 1 equals FR, 2 equals DE
       
        for (Token t : tokens)
        {
        	switch(t.getCoveredText())
        	{
        		case "the": language[0]++; break;
        		case "tu" : language[1]++; break;
        		case "tes": language[1]++; break;
        		case "des": language[1]++; break;
        		case "ist": language[2]++; break;
        		case "ein": language[2]++; break;
        	}
        }
        
        //Auswerten des Arrays
        if (language[0] > language[1] && language[0] > language[2])
        {
        	languageAnno.setLanguage("EN");
        }
        else if (language[1] > language[0] && language[1] > language[2])
        {
        	languageAnno.setLanguage("FR");
        }
        else if (language[2] > language[0] && language[2] > language[1])
        {
        	languageAnno.setLanguage("DE");
        }
        
        languageAnno.addToIndexes();
    }
}