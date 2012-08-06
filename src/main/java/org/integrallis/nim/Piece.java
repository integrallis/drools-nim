package org.integrallis.nim;

public enum Piece {
	ONE("1"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	ELEVEN("11"),
	TWELVE("12"),
	THIRTEEN("13"),
	FOURTEEN("14"),
	FIFTEEN("15");

	private Piece(String label) { this.label = label; }
	public String toString() { return label; }
	public String getLabel() { return label; }
	
	private String label;
}
