package org.integrallis.nim;

import static org.integrallis.nim.Turn.COMPUTER;
import static org.integrallis.nim.Turn.HUMAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	public Board() {
		pieces.addAll(Arrays.asList(Piece.values()));
	}

	public void move(Move move) {
		for (int i = 0; i < move.getHowMany(); i++) {
			pieces.remove(pieces.size() - 1);
		}
		System.out.println("* You took " + move.getHowMany());
		System.out.println(this);
		toggleTurn();
	}

	public void remove(Piece... piecesToRemove) {
		for (Piece piece : piecesToRemove) {
			System.out.println(">> Removing " + piece);
		}
		pieces.removeAll(Arrays.asList(piecesToRemove));
		System.out.println("+ Computer took " + piecesToRemove.length);
		System.out.println(this);
		toggleTurn();
	}

	public Integer piecesLeft() {
		return pieces.size();
	}

	public Turn turn() {
		return currentTurn;
	}

	private void toggleTurn() {
		switch (currentTurn) {
		case HUMAN:
			currentTurn = COMPUTER;
			break;
		case COMPUTER:
			currentTurn = HUMAN;
			break;
		}
		System.out.println("It is now " + currentTurn + " turn");
	}

	public String toString() {
		return pieces + " ==> board has " + piecesLeft() + " pieces left";
	}

	private List<Piece> pieces = new ArrayList<Piece>();
	private Turn currentTurn = HUMAN;
}
