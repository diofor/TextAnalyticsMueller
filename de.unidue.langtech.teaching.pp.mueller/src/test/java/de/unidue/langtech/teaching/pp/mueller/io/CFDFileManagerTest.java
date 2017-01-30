package de.unidue.langtech.teaching.pp.mueller.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;

public class CFDFileManagerTest {

	@Test
	public void testCFDFileManager()
	{
		String satz = "Das hier ist ein Satz in dem sich das Satz doppelt";
		String[] tokens = satz.split(" ");
		String sentimentOfTheSentence = "other";
		
		ConditionalFrequencyDistribution<String, String> cfd = new ConditionalFrequencyDistribution<String, String>();
		for(String token: tokens)
		{
			cfd.inc(token, sentimentOfTheSentence);
		}
		
		CFDFileManager fm = new CFDFileManager();
		fm.write(cfd, "test");
		
		ConditionalFrequencyDistribution<String, String> cfd_2 = fm.read("test");
		
		assertEquals(true, vergleicheCFDs.vergleiche(cfd, cfd_2));
	}
}
