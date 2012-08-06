package org.integrallis.nim;

public enum Move {
    TAKE_ONE(1),
    TAKE_TWO(2),
    TAKE_THREE(3);
    
    private Move(Integer howMany) { this.howMany = howMany; }

	public Integer getHowMany() { return howMany; }

	private Integer howMany;
}
