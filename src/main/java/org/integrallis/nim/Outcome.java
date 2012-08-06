package org.integrallis.nim;

public class Outcome {
    public enum OutcomeType { WIN, LOSE }
    
    public Outcome(OutcomeType result) { this.result = result; }

	public OutcomeType getResult() { return result; }

	private OutcomeType result;
}
