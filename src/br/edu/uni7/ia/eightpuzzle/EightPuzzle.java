package br.edu.uni7.ia.eightpuzzle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EightPuzzle {

	private int[][] initialStateValues = {
			{ 2, 8, 3 }, 
			{ 1, 6, 4 }, 
			{ 7, 0, 5 } };

//	private int[][] finalStateValues = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };

	private int[][] finalStateValues = { 
			{ 1, 2, 3 },
			{ 8, 0, 4 },
			{ 7, 6, 5 } };

	private static final HeuristicComparator puzzleHeuristicValueComparator = new HeuristicComparator();

	public EightPuzzle() {
		PuzzleState origin = new PuzzleState(initialStateValues);
		PuzzleState destiny = new PuzzleState(finalStateValues);

		heuristicSearch(origin, destiny);
	}

	public void heuristicSearch(PuzzleState origin, PuzzleState destiny) {
		// Estados já analisados
		LinkedList<PuzzleHeuristicValue> closedStates = new LinkedList<>();
		// Estados a serem analisados
		LinkedList<PuzzleHeuristicValue> openStates = new LinkedList<>();

		// Valor heuristico equivalente a g(n) + h(n), onde g(n) � a distância do estado
		// original
		// e h(n) é a quantidade de peças diferentes do estado destino
		int originHeuristicValue = 0 + origin.compare(destiny);
//		openStates.addLast(new PuzzleHeuristicValue(origin, 0, originHeuristicValue));

		// current é o x do algoritmo descrito no livro
		PuzzleHeuristicValue current = new PuzzleHeuristicValue(origin, 0, originHeuristicValue);

		openStates.add(current);

		while (!openStates.isEmpty()) {
			current = openStates.poll();
			if (current.puzzleState.equals(destiny)) {
				break;
			} else {
				List<PuzzleState> children = current.puzzleState.getChildrenStates();
				int childrenHeight = current.treeHeight + 1;
				for (PuzzleState child : children) {
					int heuristicValue = child.compare(destiny);
					PuzzleHeuristicValue childHeuristicValue = new PuzzleHeuristicValue(child, childrenHeight,
							heuristicValue);
					if (!closedStates.contains(childHeuristicValue) && !openStates.contains(childHeuristicValue)) {
						openStates.addLast(childHeuristicValue);
					} else if (openStates.contains(childHeuristicValue)) {
						int childInOpenIndex = openStates.indexOf(childHeuristicValue);
						PuzzleHeuristicValue childInOpen = openStates.get(childInOpenIndex);
						// Se o caminho atual for mais curto que o que está em aberto, deixa em aberto o
						// mais curto.

						if (childrenHeight < childInOpen.treeHeight) {
							openStates.set(childInOpenIndex, childHeuristicValue);
						}
					} else if (closedStates.contains(childHeuristicValue)) {
						int childInClosedIndex = closedStates.indexOf(childHeuristicValue);
						PuzzleHeuristicValue childInClosed = closedStates.get(childInClosedIndex);
						// Se o caminho atual for mais curto que o que est� em fechado, o mais curto
						// volta para abertos e o longo sai de fechados.
						if (childrenHeight < childInClosed.treeHeight) {
							closedStates.remove(childInClosed);
							openStates.addLast(childHeuristicValue); // TODO conferir se estado j� existe em abertos
						}
					}
				}
				closedStates.add(current);

				System.out.println("heuristicValue " + current.heuristicValue);
				System.out.println("height " + current.treeHeight);
				System.out.println(current.puzzleState);

				Collections.sort(openStates, puzzleHeuristicValueComparator);
			}
		}

		// TODO
		if (!current.puzzleState.equals(destiny) || current == null) {
			// Não achou
		} else {
			// TODO retornar sequência
			// TODO gerar do estado final, buscando pelos pais até a origem
			LinkedList<PuzzleState> stateSequence = new LinkedList<>();

			PuzzleState lastState = current.puzzleState;
			do {
				stateSequence.addFirst(lastState);
				lastState = lastState.getParent();
			} while (lastState.getParent() != null);

			printSequenceInline(stateSequence);
			
//			printSequenceInline(closedStates);
		}
	}

	private void printSequenceInline(List<PuzzleState> stateSequence) {
		StringBuilder line1 = new StringBuilder();
		for (PuzzleState puzzleState : stateSequence) {
			line1.append(puzzleState.getStateValue(0, 0));
			line1.append("|");
			line1.append(puzzleState.getStateValue(1, 0));
			line1.append("|");
			line1.append(puzzleState.getStateValue(2, 0));

			line1.append("  ");
		}

		StringBuilder separators = new StringBuilder();
		for (int i = 0; i < stateSequence.size(); i++) {
			separators.append("-----  ");
		}

		StringBuilder line2 = new StringBuilder();
		for (PuzzleState puzzleState : stateSequence) {
			line2.append(puzzleState.getStateValue(0, 1));
			line2.append("|");
			line2.append(puzzleState.getStateValue(1, 1));
			line2.append("|");
			line2.append(puzzleState.getStateValue(2, 1));

			line2.append("  ");
		}

		StringBuilder line3 = new StringBuilder();
		for (PuzzleState puzzleState : stateSequence) {
			line3.append(puzzleState.getStateValue(0, 2));
			line3.append("|");
			line3.append(puzzleState.getStateValue(1, 2));
			line3.append("|");
			line3.append(puzzleState.getStateValue(2, 2));

			line3.append("  ");
		}

		// printing
		System.out.println(line1.toString());
		System.out.println(separators.toString());
		System.out.println(line2.toString());
		System.out.println(separators.toString());
		System.out.println(line3.toString());
	}

//		for (PuzzleHeuristicValue stateHeuristicValue: stateSequence) {
//			System.out.println(stateHeuristicValue.puzzleState);
//		}

	public static void main(String[] args) {
		new EightPuzzle();
	}
}
