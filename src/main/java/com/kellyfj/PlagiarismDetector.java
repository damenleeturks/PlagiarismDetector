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
		
		EqualityOrSynonymService e = new EqualityOrSynonymService();
		e.loadSynonymsFromFile(synonymsFileName);
		System.out.println("");
		List<NTuple> nTuples1 = NTuple.loadTuplesFromFile(inputFileName1, tupleSize);
		System.out.println("");
		List<NTuple> nTuples2 = NTuple.loadTuplesFromFile(inputFileName2, tupleSize);
		System.out.println("");
		
		int count = e.getNumMatches(nTuples1, nTuples2);
		System.out.println("Num matches "+count);
		
		double percentMatch = (100 * count)/nTuples1.size();		
		System.out.println("% of tuples that match: "+percentMatch);
		
		return percentMatch;
	}



	
}
