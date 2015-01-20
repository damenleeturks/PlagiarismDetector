package com.kellyfj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NTuple {

	List<String> wordsInTuple = new ArrayList<String>();
	
	public void addWord(String string) {
		wordsInTuple.add(string);
	}
	
	public String getWord(int i) {
		return wordsInTuple.get(i);
	}
	
	public int size() {
		return wordsInTuple.size();
	}

	public static List<NTuple> loadTuplesFromFile(String fileName, int tupleSize) throws IOException{
		BufferedReader br=null;
		List<NTuple> tuples = new ArrayList<NTuple>();
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

	private static List<NTuple> lineToTuple(String line, int tupleSize) {
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
