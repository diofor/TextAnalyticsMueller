package de.unidue.langtech.teaching.pp.mueller.io;


import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.mueller.type.GoldInformation;

public class ReaderTest {
	
	private CollectionReaderDescription reader;
	
	@Before
	public void setUp() throws ResourceInitializationException
	{
		reader = CollectionReaderFactory.createReaderDescription(Reader.class,
						Reader.PARAM_INPUT_FILE,
						"src/test/resources/test.csv");
	}

		
	@Test
	public void testReader_Sentiment() throws Exception {
		
		int amountJCasElements = 0;
		int sentiment = 0;
		for (JCas jcas : new JCasIterable(reader)) {
			amountJCasElements++;
			GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
			switch (gold.getSentiment()) {
			case "pos": sentiment++; break;
			case "neg": sentiment++; break;
			case "other": sentiment ++; break;
			default: System.out.println(gold.getSentiment());
			}
		}
		assertEquals(amountJCasElements, sentiment);
	}
	
	@Test
	public void testReader_Target() throws Exception {
		
		int amountJCasElements = 0;
		int target = 0;
		for (JCas jcas : new JCasIterable(reader)) {
			amountJCasElements++;
			GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
			switch (gold.getTarget()) {
			case "Atheism": target++; break;
			case "Climate Change is a Real Concern": target++; break;
			case "Feminist Movement": target++; break;
			case "Hillary Clinton": target++; break;
			case "Donald Trump": target++; break;
			case "Legalization of Abortion": target++; break;
			default: System.out.println(gold.getTarget());
			}
		}
		assertEquals(amountJCasElements, target);
	}
	
	@Test
	public void testReader_Stance() throws Exception {
		
		int amountJCasElements = 0;
		int stance = 0;
		for (JCas jcas : new JCasIterable(reader)) {
			amountJCasElements++;
			GoldInformation gold = JCasUtil.selectSingle(jcas, GoldInformation.class);
			switch (gold.getStance()) {
			case "AGAINST": stance++; break;
			case "NONE": stance++; break;
			case "FAVOR": stance++; break;
			default: System.out.println(gold.getStance());
			}
		}
		assertEquals(amountJCasElements, stance);
	}
	
	
	@Test
	public void testReader_tweet() throws Exception {
		// Tweets sollen in einer Form ohne Anführungszeichen vorliegen.
		int amountJCasElements = 0;
		int tweetsWithoutQuotes = 0;
		for (JCas jcas : new JCasIterable(reader)) {
			amountJCasElements++;
			if(!(jcas.getDocumentText().startsWith("\""))) tweetsWithoutQuotes++;
			else if(!(jcas.getDocumentText().endsWith("\""))) tweetsWithoutQuotes++;
			else System.out.println(jcas.getDocumentText());
		}
		assertEquals(amountJCasElements, tweetsWithoutQuotes);
	}

}
