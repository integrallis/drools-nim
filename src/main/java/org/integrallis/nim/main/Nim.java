package org.integrallis.nim.main;

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
import org.integrallis.nim.Board;
import org.integrallis.nim.Move;

import static org.integrallis.nim.Move.*;

public class Nim {

	public static final void main(String[] args) {
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
			//Move[] moves = new Move[] { TAKE_THREE, TAKE_THREE, TAKE_THREE }; // LOSS for Human
			Move[] moves = new Move[] { TAKE_TWO, TAKE_TWO, TAKE_THREE, TAKE_THREE };  // WIN for Human
			
			for (Move move : moves) {
				knowledgeSession.insert(move);
				knowledgeSession.fireAllRules();
			}
			
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}