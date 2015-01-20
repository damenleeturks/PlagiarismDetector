package com.kellyfj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent our Tuple of type <T>
 * @author kellyfj
 */
public class NTuple<T> {

	List<T> wordsInTuple = new ArrayList<T>();
	
	public void addWord(T string) {
		wordsInTuple.add(string);
	}
	
	public T get(int i) {
		return wordsInTuple.get(i);
	}
	
	public int size() {
		return wordsInTuple.size();
	}

	public static List<NTuple<String>> loadTuplesFromFile(String fileName, int tupleSize) throws IOException{
		BufferedReader br=null;
		List<NTuple<String>> tuples = new ArrayList<NTuple<String>>();
		try {
			br = new BufferedReader(new InputStreamReader(NTuple.class.getClassLoader().getResourceAsStream(fileName)));
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

	/**
	 * Convert a line of synonyms e.g. "run sprint jog" to a List of tupleSized tuples
	 * 
	 * @param line
	 * @param tupleSize
	 * @return
	 */
	private static List<NTuple<String>> lineToTuple(String line, int tupleSize) {
		List<NTuple<String>> tuplesInLine = new ArrayList<NTuple<String>>();
		
		String[] splitwords = line.toLowerCase().split(" "); //Note lower case preprocessing done once here
		//TODO what if length is zero or 1 should we throw an exception?
		for(int i=0; i< splitwords.length - (tupleSize-1); i++) {
			
			NTuple<String> n = new NTuple<String>();
			for(int j=i; j < i+tupleSize; j++ ) {
				n.addWord(splitwords[j]);
			}
			//System.out.println("Tuple: "+tuple);
			tuplesInLine.add(n);
		}
		return tuplesInLine;
	}
}
