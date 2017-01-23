package de.unidue.langtech.teaching.pp.mueller.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;

public class CFDFileManager {
	
	static FileWriter writer;
	static File file;
	
	public CFDFileManager() {
		
	}
	
	public void write(ConditionalFrequencyDistribution<String, String> cfd, String goal)
	{
		List<String> liste = new ArrayList<String>();
		long haeufigkeit = 0;
		for(String condition : cfd.getConditions())
		{
			for(String key : cfd.getFrequencyDistribution(condition).getKeys())
			{
				haeufigkeit = cfd.getCount(condition, key);
				liste.add(condition + "\t" + key + "\t" + haeufigkeit+"\n");
			}
		}
		schreiben(goal ,liste);
	}
	
	public ConditionalFrequencyDistribution<String, String> read(String goal)
	{
		ConditionalFrequencyDistribution<String, String> cfd = new ConditionalFrequencyDistribution<String, String>();
		File file = new File(System.getProperty("user.dir")+"/src/main/resources/CFD_"+goal+".txt");
		String[] werte = new String[3];
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	werte = line.split("\\t");
		    	cfd.addSample(werte[0], werte[1], Long.valueOf(werte[2]));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cfd;
	}

	
	//Quelle: http://blog.mynotiz.de/programmieren/java-text-in-eine-datei-schreiben-450/
	private static void schreiben(String goal, List<String> fertigeZeilen){
		// File anlegen
		file = new File(System.getProperty("user.dir") + "/src/main/resources/CFD_"+goal+".txt");
	
		try {
			// new FileWriter(file ,true) - falls die Datei bereits existiert
			// werden die Bytes an das Ende der Datei geschrieben
			 
			// new FileWriter(file) - falls die Datei bereits existiert
			// wird diese überschrieben
			writer = new FileWriter(file);
			 
			for (String zeile: fertigeZeilen)
			{
				// Text wird in den Stream geschrieben	
				writer.write(zeile);
			}
			 
			// Schreibt den Stream in die Datei
			writer.flush();
			 
			// Schließt den Stream
			writer.close();
			System.out.println("\nCFD für "+ goal +" unter \"" +file.getAbsolutePath()+ "\" gespeichert.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
