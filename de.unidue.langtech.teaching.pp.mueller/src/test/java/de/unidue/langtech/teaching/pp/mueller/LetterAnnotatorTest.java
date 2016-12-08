package de.unidue.langtech.teaching.pp.mueller;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.unidue.langtech.teaching.pp.mueller.newType.LetterAnnotator;
import de.unidue.langtech.teaching.pp.type.NewType;

public class LetterAnnotatorTest {

	@Test
	public void testLetterAnnotator() throws UIMAException
	{
		String text = "Peter test";

		// We don't have a pipeline here,
		// thus we create an empty document by hand,
		// the following utility-method call helps us
		JCas jcas = JCasFactory.createJCas();
		// We set the text to our empty document
		jcas.setDocumentText(text);

		// We just instantiate our annotator by hand and
		// call process() by-hand (in a pipeline, the framework does that for
		// us)

		// Now we can let run our language detection
		AnalysisEngineDescription letterDetector = createEngineDescription(LetterAnnotator.class);
		AnalysisEngine letterDetectEngine = createEngine(letterDetector);
		letterDetectEngine.process(jcas);

		// Get the detected language - we know that there is only one annotation
		// of the type
		// 'DetectedLanguage', thus we can use selectSingle()
		// this will throw an exception if there is more than just one
		// annotation of this type!
		NewType count = JCasUtil.selectSingle(jcas,
				NewType.class);
		

		// The essential part of a test - the test itself - are expected and
		// actual result the same?
		assertEquals(3, count.getCountLetterE());
		
	}
}
