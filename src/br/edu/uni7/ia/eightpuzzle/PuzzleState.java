package br.edu.uni7.ia.eightpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.uni7.ia.util.Position;

public class PuzzleState {
	
	private PuzzleState parent;

	// row x column
	private int[][] state;

	public PuzzleState(int[][] state) {
		// TODO validar array de estados
		this.state = state;
	}
	
	public PuzzleState getParent() {
		return parent;
	}

	public int[][] getState() {
		return state;
	}
	
	public String getStateValue(int x, int y) {
		if (state[y][x] == 0) {
			return " ";
		}
		return "" + state[y][x];
	}

	public List<PuzzleState> getChildrenStates() {
		List<PuzzleState> puzzleStates = new ArrayList<>();

		Position emptyPosition = findEmptyPosition();
		
		if (emptyPosition.x < 2) {
			// Move para esquerda
			PuzzleState child = clone();
			child.state[emptyPosition.y][emptyPosition.x] = child.state[emptyPosition.y][emptyPosition.x + 1];
			child.state[emptyPosition.y][emptyPosition.x + 1] = 0;
			child.parent = this;
			puzzleStates.add(child);
		}
		if (emptyPosition.x > 0) {
			// Move para direita
			PuzzleState child = clone();
			child.state[emptyPosition.y][emptyPosition.x] = child.state[emptyPosition.y][emptyPosition.x - 1];
			child.state[emptyPosition.y][emptyPosition.x - 1] = 0;
			child.parent = this;
			puzzleStates.add(child);
		}
		if (emptyPosition.y < 2) {
			PuzzleState child = clone();
			child.state[emptyPosition.y][emptyPosition.x] = child.state[emptyPosition.y + 1][emptyPosition.x];
			child.state[emptyPosition.y + 1][emptyPosition.x] = 0;
			child.parent = this;
			puzzleStates.add(child);
		}
		if (emptyPosition.y > 0) {
			PuzzleState child = clone();
			child.state[emptyPosition.y][emptyPosition.x] = child.state[emptyPosition.y - 1][emptyPosition.x];
			child.state[emptyPosition.y - 1][emptyPosition.x] = 0;
			child.parent = this;
			puzzleStates.add(child);
		}

		return puzzleStates;
	}

	public Position findEmptyPosition() {
		int xEmpty = 0;
		int yEmpty = 0;
		for (int y = 0; y < state.length; y++) {
			for (int x = 0; x < state.length; x++) {
				if (state[y][x] == 0) {
					xEmpty = x;
					yEmpty = y;
				}
			}
		}
		return new Position(xEmpty, yEmpty);
	}
	
	public int compare(PuzzleState other) {
		int differentPieces = 0;
		for (int y = 0; y < state.length; y++) {
			for (int x = 0; x < state.length; x++) {
				if (state[y][x] != other.state[y][x]) {
					differentPieces++;
				}
			}
		}
		return differentPieces;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < state.length; y++) {
			for (int x = 0; x < state.length; x++) {
				if (state[y][x] == 0) {
					sb.append(" ");
				} else {
					sb.append(state[y][x]);
				}
				if (x < state.length - 1) {
					sb.append("|");
				}
			}
			sb.append(System.lineSeparator());
			if (y < state.length - 1) {
				sb.append("-----");
			}
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof PuzzleState) {
			boolean areStateEquals = true;

			int[][] stateThat = ((PuzzleState) other).state;

			for (int i = 0; i < state.length; i++) {
				int[] stateLineThis = state[i];
				int[] stateLineThat = stateThat[i];
				if (!Arrays.equals(stateLineThis, stateLineThat)) {
					areStateEquals = false;
					break;
				}
			}
			return areStateEquals;
		}
		return false;
	}

	@Override
	protected PuzzleState clone() {
		int[][] clonedState = new int[state.length][state.length];
		for (int i = 0; i < state.length; i++) {
			int[] stateLine = state[i];
			clonedState[i] = stateLine.clone();
		}
		PuzzleState clonedPuzzleState = new PuzzleState(clonedState);
		return clonedPuzzleState;
	}
}
