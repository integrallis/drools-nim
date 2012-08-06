package org.integrallis.nim;

import static org.integrallis.nim.Move.TAKE_THREE;
import static org.integrallis.nim.Move.TAKE_TWO;
import static org.integrallis.nim.Outcome.OutcomeType.LOSE;
import static org.integrallis.nim.Outcome.OutcomeType.WIN;
import static org.junit.Assert.assertTrue;

import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import org.integrallis.drools.junit.BaseDroolsTestCase;
import org.junit.Before;
import org.junit.Test;


public class NimTest extends BaseDroolsTestCase {

	public NimTest() {
		super("nim.drl");
	}
	
	@Before
	public void configureGame() {
		Board board = new Board();
		knowledgeSession.insert(board);
	}
	
	@Test
	public void testWinningGame() {
		Move[] moves = new Move[] { TAKE_TWO, TAKE_TWO, TAKE_THREE, TAKE_THREE };
		
		for (Move move : moves) {
			knowledgeSession.insert(move);
			knowledgeSession.fireAllRules();
		}
		
		QueryResults results = knowledgeSession.getQueryResults( "GetOutcome" );
		for ( QueryResultsRow row : results ) {
			Outcome outcome = (Outcome) row.get( "outcome" );
			assertTrue(outcome.getResult() == WIN);
		}
	}
	
	@Test
	public void testLosingGame() {
		Move[] moves = new Move[] { TAKE_THREE, TAKE_THREE, TAKE_THREE };
		
		for (Move move : moves) {
			knowledgeSession.insert(move);
			knowledgeSession.fireAllRules();
		}
		
		QueryResults results = knowledgeSession.getQueryResults( "GetOutcome" );
		for ( QueryResultsRow row : results ) {
			Outcome outcome = (Outcome) row.get( "outcome" );
			assertTrue(outcome.getResult() == LOSE);
		}
		
	}

}
