package org.integrallis.nim.main;

import static org.integrallis.nim.Move.TAKE_ONE;
import static org.integrallis.nim.Move.TAKE_THREE;
import static org.integrallis.nim.Move.TAKE_TWO;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import org.integrallis.nim.Board;
import org.integrallis.nim.Outcome;

public class NimCLI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			knowledgeBuilder.add(ResourceFactory.newClassPathResource("nim.drl"), ResourceType.DRL);
			KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error: errors) {
					System.err.println(error);
				}
				throw new IllegalArgumentException("Could not parse knowledge.");
			}
			
			KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
			knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
			
			StatefulKnowledgeSession knowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
			
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(knowledgeSession, "test");
			
			Board board = new Board();
			System.out.println(board);
		    
			knowledgeSession.insert(board);
			
			String input = ""; // Line read from standard in

			System.out.println("Shall we play a game?");
			System.out.println("Your turn job turkey, pick 1, 2 or 3 pieces: ");
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
			
			Boolean quit = false;

			while (!quit) {
                if (!quit) {
                	input = in.readLine();
                	quit = input.equals("quit");
                }
				if (!quit) {
					System.out.println("You chose: " + input);
					Integer howMany = new Integer(input);
					switch (howMany) {
					case 1:
						knowledgeSession.insert(TAKE_ONE);
						break;
					case 2:
						knowledgeSession.insert(TAKE_TWO);
						break;
					case 3:
						knowledgeSession.insert(TAKE_THREE);
						break;						
					}
					knowledgeSession.fireAllRules();
					
					QueryResults results = knowledgeSession.getQueryResults( "GetOutcome" );
					if (results.size() != 0) { quit = true; };
					for ( QueryResultsRow row : results ) {
						Outcome outcome = (Outcome) row.get( "outcome" );
						switch (outcome.getResult()) {
						case WIN:
							System.out.println("You won't smarty pants!");
							break;
						case LOSE:
							System.out.println("You lost loser!");
							break;

						}
					}
				}
			}		
			
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
