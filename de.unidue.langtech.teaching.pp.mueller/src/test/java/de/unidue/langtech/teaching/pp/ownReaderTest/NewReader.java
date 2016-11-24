package de.unidue.langtech.teaching.pp.ownReaderTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class NewReader extends JCasCollectionReader_ImplBase {
	public static final String PARAM_INPUT_FILE = "InputFile";
	@ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
	private File inputFile;

	private List<String> lines;
	private int currentLine;
	private int callCounter;

	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		try {
			lines = FileUtils.readLines(inputFile);
			currentLine = 0;
			callCounter = 0;
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	public boolean hasNext() throws IOException, CollectionException {
		return currentLine < lines.size();
	}

	public Progress[] getProgress() {
		return null;
	}

	public void getNext(JCas aJCas) throws IOException, CollectionException {
		String klasse = this.toString();
		klasse.substring(klasse.indexOf("@"));
		System.out.println(++callCounter + " "+ klasse);
		List<String> l = new ArrayList<String>();
		l.clear();
		
		while (lines.get(currentLine).isEmpty())
		{
			currentLine++;
		}
		while (!(lines.get(currentLine).isEmpty()))
		{
			l.add(lines.get(currentLine));
			// increment to avoid infinite looping
			currentLine++;
			if (currentLine == lines.size())
				break;
		}
		
//		for (String i : l)
//        {
//        	System.err.println(i);
//        }
		
		// add gold standard value as annotation
		GoldLanguage goldLanguage = new GoldLanguage(aJCas);
		goldLanguage.setLanguage(l.get(0));
		goldLanguage.addToIndexes();
		
		String output = "";
		int start;
		int end;
		
		for(int i = 1; i<l.size(); ++i)
		{
			output += l.get(i);
			start = output.length()-l.get(i).length();
			end = output.length();
			Token t = new Token(aJCas, start, end);
			t.addToIndexes();
			output += " ";
		}
			
		aJCas.setDocumentText(output.trim());
	}

}
