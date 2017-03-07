package de.unidue.langtech.teaching.pp.mueller.io;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;

public class vergleicheCFDs {
	
	//Hilfsklasse um bei den Tests CFDs vergleichen zu können.

	//Vergleich zwei CFDs und beurteilt, ob sie in allen Werten gleich sind.
	public static boolean vergleiche(ConditionalFrequencyDistribution<String, String> cfd1, ConditionalFrequencyDistribution<String, String> cfd2)
	{
		if (cfd1.getConditions().size() == cfd2.getConditions().size())
		{
			List<String> condition1 = new ArrayList<String>(cfd1.getConditions());
			List<String> condition2 = new ArrayList<String>(cfd2.getConditions());
			condition1.sort(null);
			condition2.sort(null);
			
			
			for(int i = 0; i < condition1.size(); ++i)
			{
				if(!(condition1.get(i).equals(condition2.get(i)))) return false;
			}
		}
		else
		{
			return false;
		}
		
		for(String condition : cfd1.getConditions())
		{
			for (String key :cfd1.getFrequencyDistribution(condition).getKeys())
			{
				if (!(cfd1.getCount(condition, key) == cfd2.getCount(condition, key))) return false; 
			}
		}
		return true;
	}

}
