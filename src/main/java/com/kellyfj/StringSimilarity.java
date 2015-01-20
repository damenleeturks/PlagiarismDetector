package com.kellyfj;

import java.util.List;

/**
 * Interface that we could re-use to define String similarity
 * mechanisms e.g. VonLevenstein distance that we can call
 * on our tuples  
 * 
 * @author kellyfj
 */
public interface StringSimilarity {

	/**
	 * Tells us the number of matches between two lists of tuples
	 */
	public int countMatches(List<NTuple<String>> nTuples1, List<NTuple<String>> nTuples2);
}
