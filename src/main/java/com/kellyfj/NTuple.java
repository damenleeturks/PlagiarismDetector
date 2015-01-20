package com.kellyfj;

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

}
