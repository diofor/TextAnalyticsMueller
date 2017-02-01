package de.unidue.langtech.teaching.pp.mueller.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.FrequencyDistribution;
import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class FindMostFrequentTarget  extends JCasAnnotator_ImplBase {

	FrequencyDistribution<String> fd;
	int sum;
	
	@Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        fd = new FrequencyDistribution<String>();
        sum = 0;
    }
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		GoldInformation gold = JCasUtil.selectSingle(aJCas, GoldInformation.class);
		fd.inc(gold.getTarget());
		++sum;
	}
	
	@Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException
	{
		super.collectionProcessComplete();
		String max = fd.getSampleWithMaxFreq();
		System.out.println("Die Verteilung der Targets:");
		for(String key : fd.getKeys())
		{
			System.out.printf("%d von %d - %.2f%% \t| %s%n", fd.getCount(key), sum, ((double)fd.getCount(key)/sum) * 100, key);
		}
		System.out.printf("The most frequent target in this data was: \"%s\" %n %n", max);
	}

	
}
