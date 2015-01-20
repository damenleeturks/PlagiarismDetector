package com.kellyfj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlagiarismDetector {

	public static void main(String[] args) throws IOException {
		
		PlagiarismDetector pd = new PlagiarismDetector();
		double rate = pd.runDetection("synonyms.txt", "file1.txt", "file2.txt", 3);
		if(rate != 100.0) {
			System.err.println("Test failed expected 100.0 but got "+rate);
			System.exit(-1);
		} 
		
		
		rate = pd.runDetection("synonyms.txt", "file1.txt", "file3.txt", 3);
		if(rate != 50.0) {
			System.err.println("Test failed expected 100.0 but got "+rate);
			System.exit(-1);
		}

		System.out.println("SUCCESS: Tests passed");
	}
	
	
	private double runDetection(String synonymsFileName, String inputFileName1, String inputFileName2, int tupleSize) throws IOException{
		
		Map<String, List<String>> synonyms = loadSynonymsFromFile(synonymsFileName);
		System.out.println("");
		List<NTuple> nTuples1 = loadTuplesFromFile(inputFileName1, tupleSize);
		System.out.println("");
		List<NTuple> nTuples2 = loadTuplesFromFile(inputFileName2, tupleSize);
		System.out.println("");
		
		int count = getNumSynonymMatches(synonyms, nTuples1, nTuples2);
		System.out.println("Num matches "+count);
		
		double percentMatch = (100 * count)/nTuples1.size();
		
		System.out.println("% of tuples that match: "+percentMatch);
		
		return percentMatch;
	}
	
	


	private int getNumSynonymMatches(Map<String, List<String>> synonyms,
			List<NTuple> nTuples1, List<NTuple> nTuples2) {

			int count =0;
			for(NTuple tuple1: nTuples1) {
				for(NTuple tuple2: nTuples2) {
					//Design decision count each match just once
					if(isMatch(tuple1, tuple2, synonyms)) {
						count++;
						break;
					}
				}
				
			}
			return count;
	}


	private boolean isMatch(NTuple tuple1, NTuple tuple2,
			Map<String, List<String>> synonyms) {
		if (tuple1.size() != tuple2.size())
			throw new IllegalArgumentException("Your tuple sizes do not match");

		for (int i = 0; i < tuple1.size(); i++) {
			String word1 = tuple1.getWord(i);
			String word2 = tuple2.getWord(i);
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


	private Map<String, List<String>> loadSynonymsFromFile(String fileName) throws IOException{
		Map<String, List<String>> ret = new HashMap<String,List<String>>();
		
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
			System.out.println("Synonyms File: "+fileName);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] words = line.split(" ");	
				List<String> synonyms = Arrays.asList(words);
				
				for(String word : words) {
					if(ret.containsKey(word)) 
						throw new RuntimeException("The word "+word+" was already in our map of synonyms. For now we don't handle homonyms e.g. bark (of a dog) vs bark (of a tree)");
					else
						ret.put(word, synonyms);
				}
			}
		}  finally {
			if(br!=null)
				br.close();
		}
		return ret;
		
	}
	
	private List<NTuple> loadTuplesFromFile(String fileName, int tupleSize) throws IOException{
		BufferedReader br=null;
		List<NTuple> tuples = new ArrayList<NTuple>();
		try {
			br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
			System.out.println("Tuple File: "+fileName);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				tuples.addAll(lineToTuple(line, tupleSize));
			}
			}  finally {
				if(br!=null)
					br.close();
			}
		
		return tuples;
	}

	private List<NTuple> lineToTuple(String line, int tupleSize) {
		List<NTuple> tuplesInLine = new ArrayList<NTuple>();
		
		String[] splitwords = line.toLowerCase().split(" ");
		for(int i=0; i< splitwords.length - (tupleSize-1); i++) {
			
			NTuple n = new NTuple();
			for(int j=i; j < i+tupleSize; j++ ) {
				n.addWord(splitwords[j]);
			}
			//System.out.println("Tuple: "+tuple);
			tuplesInLine.add(n);
		}
		return tuplesInLine;
	}
}
