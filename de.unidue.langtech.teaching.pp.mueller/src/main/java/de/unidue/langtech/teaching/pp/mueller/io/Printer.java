package de.unidue.langtech.teaching.pp.mueller.io;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.mueller.type.DetectedLanguage;
import de.unidue.langtech.teaching.pp.mueller.type.GoldLanguage;
import de.unidue.langtech.teaching.pp.mueller.type.NewType;

public class Printer
    extends JCasAnnotator_ImplBase
{

    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        // This API always returns a collection even if you know that there should be only one
        Collection<NewType> letterECount = JCasUtil.select(jcas, NewType.class);

        // There is a special API for the case you know that there is exactly one annotation
        GoldLanguage gold = JCasUtil.selectSingle(jcas, GoldLanguage.class);
        DetectedLanguage detected = JCasUtil.selectSingle(jcas, DetectedLanguage.class);

        for (NewType t : letterECount) {
            System.out.println("Detected: " + detected.getLanguage() + " Gold:"
                    + gold.getLanguage());
            System.out.println("Number of e/E: " + t.getCountLetterE());
        }

    }

}