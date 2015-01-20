package com.kellyfj;

import java.io.IOException;
import java.util.List;

/**
 * Top level Facade that loads the synonyms, loads the tuples, instantiates the appropriate service
 * and scores the tuples
 * 
 * @author kellyfj
 */
public class PlagiarismDetector {
	
	/**
	 * Get the Plagiarism score - the higher the number the more likelihood. 
	 * 
	 * @return The percentage of tuples in input file 1 that have a match in input file 2
	 * @throws IOException if we are unable to read any of the three files specified
	 */
	public double getPlagiarismScore(String synonymsFileName, String inputFileName1, String inputFileName2, int tupleSize) throws IOException{
		
		EqualityOrSynonymService service = new EqualityOrSynonymService(synonymsFileName);
		System.out.println("");
		List<NTuple<String>> nTuples1 = NTuple.loadTuplesFromFile(inputFileName1, tupleSize);
		System.out.println("");
		List<NTuple<String>> nTuples2 = NTuple.loadTuplesFromFile(inputFileName2, tupleSize);
		System.out.println("");
		
		int count = service.countMatches(nTuples1, nTuples2);
		System.out.println("Num matches "+count);
		
		double percentMatch = 0.0;
		if(nTuples1.size() > 0)
			percentMatch = (100 * count)/nTuples1.size();		
		System.out.println("% of tuples that match: "+percentMatch);
		
		return percentMatch;
	}


	/**
	 * Main for the command line (and also for some testing)
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {	
		PlagiarismDetector pd = new PlagiarismDetector();
		String synonymsFileName;
		String comparisonFileName;
		String baseFileName;
		int numTuples = 3;
		
		if (args.length > 0) {
			if(args.length < 3 || args.length > 4) {
				System.err.println("USAGE: <synonyms_file_name> <comparison_file_name> <base_file_name> [<num_tuples>]");
				System.exit(-1);
			}
			
			synonymsFileName = args[0];
			comparisonFileName = args[1];
			baseFileName = args[2];
			if(args.length==4) {
				String s  = args[3];
				numTuples = Integer.valueOf(s);
			}
			
			pd.getPlagiarismScore(synonymsFileName, comparisonFileName,
					baseFileName, numTuples);
			
		}else { //In case user sets no args lets just run our tests - normally this would be in JUnit
			System.out.println("Running Tests");
			System.out.println("=== TEST 1 OF 2===");
			synonymsFileName = "synonyms.txt";
		    comparisonFileName = "file1.txt";
			baseFileName = "file2.txt";
			
			double rate = pd.getPlagiarismScore(synonymsFileName, comparisonFileName,
					baseFileName, numTuples);
			if (rate != 100.0) {
				System.err
						.println("Test failed expected 100.0 but got " + rate);
				System.exit(-1);
			}

			System.out.println("\n=== TEST 2 OF 2===");

			baseFileName = "file3.txt";
			rate = pd.getPlagiarismScore(synonymsFileName, comparisonFileName, baseFileName, numTuples);
			if (rate != 50.0) {
				System.err
						.println("Test failed expected 100.0 but got " + rate);
				System.exit(-1);
			}

			System.out.println("=== SUCCESS: All tests passed ===");
		}		
	}
	
}
