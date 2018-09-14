package br.edu.uni7.ia.eightpuzzle;

import java.util.Comparator;

public class HeuristicComparator implements Comparator<PuzzleHeuristicValue> {

	@Override
	public int compare(PuzzleHeuristicValue o1, PuzzleHeuristicValue o2) {
		// Para ordenar do menor para o maior
		return (o1.heuristicValue + o1.treeHeight) - (o2.heuristicValue - o2.treeHeight);
	}

}
