package br.edu.uni7.ia.eightpuzzle;

public class PuzzleHeuristicValue {
	
	public final PuzzleState puzzleState;
	public final int treeHeight;
	public final int heuristicValue;
	
	public PuzzleHeuristicValue(PuzzleState puzzleState, int treeHeight, int heuristicValue) {
		this.puzzleState = puzzleState;
		this.treeHeight = treeHeight;
		this.heuristicValue = heuristicValue;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PuzzleHeuristicValue) {
			PuzzleHeuristicValue otherValue = (PuzzleHeuristicValue) other;
			
			if (puzzleState.equals(otherValue.puzzleState)) {
				return true;
			}
		}
		return false;
	}
}