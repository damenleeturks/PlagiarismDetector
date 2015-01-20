package com.kellyfj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualityOrSynonymService implements StringSimilarity{

	private Map<String, List<String>> synonyms = null;
	
	public Map<String, List<String>> getSynonyms() {
		return synonyms;
	}
	
	public void loadSynonymsFromFile(String fileName) throws IOException{
		synonyms = new HashMap<String,List<String>>();
		
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(EqualityOrSynonymService.class.getClassLoader().getResourceAsStream(fileName)));
			System.out.println("Synonyms File: "+fileName);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] words = line.split(" ");	
				List<String> list = Arrays.asList(words);
				
				for(String word : words) {
					if(synonyms.containsKey(word)) 
						throw new RuntimeException("The word "+word+" was already in our map of synonyms. For now we don't handle homonyms e.g. bark (of a dog) vs bark (of a tree)");
					else
						synonyms.put(word, list);
				}
			}
		}  finally {
			if(br!=null)
				br.close();
		}
	}
	
	@Override
	public int getNumMatches(List<NTuple<String>> nTuples1, List<NTuple<String>> nTuples2) {

			int count =0;
			for(NTuple<String> tuple1: nTuples1) {
				for(NTuple<String> tuple2: nTuples2) {
					//Design decision count each match just once
					if(isMatch(tuple1, tuple2, synonyms)) {
						count++;
						break;
					}
				}
				
			}
			return count;
	}
	
	public boolean isMatch(NTuple<String> tuple1, NTuple<String> tuple2,
			Map<String, List<String>> synonyms) {
		if (tuple1.size() != tuple2.size())
			throw new IllegalArgumentException("Your tuple sizes do not match");

		for (int i = 0; i < tuple1.size(); i++) {
			String word1 = tuple1.get(i);
			String word2 = tuple2.get(i);
			// Don't need to to equalsIgnoreCase as preprocessing done earlier
			if (!word1.equals(word2)) {
				//check if they are synonyms
				if (synonyms.containsKey(word1) && synonyms.containsKey(word2)) {

					List<String> syn1 = synonyms.get(word1);
					List<String> syn2 = synonyms.get(word2);
					if (!syn1.equals(syn2)) {
						return false;
					}
				} else {
					//Not synonyms
					return false;
				}
			} else {
				// continue if words are equals
			}

		}
		return true;
	}

}
