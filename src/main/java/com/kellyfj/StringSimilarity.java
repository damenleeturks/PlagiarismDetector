package com.kellyfj;

import java.util.List;

public interface StringSimilarity {

	public int getNumMatches(List<NTuple> nTuples1, List<NTuple> nTuples2);
}
