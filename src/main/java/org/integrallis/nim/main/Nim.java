package org.integrallis.nim.main;

//import org.drools.KnowledgeBase;
//import org.drools.KnowledgeBaseFactory;
//import org.drools.builder.KnowledgeBuilder;
//import org.drools.builder.KnowledgeBuilderError;
//import org.drools.builder.KnowledgeBuilderErrors;
//import org.drools.builder.KnowledgeBuilderFactory;
//import org.drools.builder.ResourceType;
//import org.drools.io.ResourceFactory;
//import org.drools.logger.KnowledgeRuntimeLogger;
//import org.drools.logger.KnowledgeRuntimeLoggerFactory;
//import org.drools.runtime.StatefulKnowledgeSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import org.integrallis.nim.Board;
import org.integrallis.nim.Move;

import static org.integrallis.nim.Move.*;

public class Nim {

	public static final void main(String[] args) {
		KieSession knowledgeSession = null;
		try {
	        KieServices ks = KieServices.Factory.get();
    	        KieContainer kContainer = ks.getKieClasspathContainer();
    	        knowledgeSession = kContainer.newKieSession("ksession-rules");

			Board board = new Board();
			System.out.println(board);
		    
			knowledgeSession.insert(board);
			//Move[] moves = new Move[] { TAKE_THREE, TAKE_THREE, TAKE_THREE }; // LOSS for Human
			Move[] moves = new Move[] { TAKE_TWO, TAKE_TWO, TAKE_THREE, TAKE_THREE };  // WIN for Human
			
			for (Move move : moves) {
				knowledgeSession.insert(move);
				knowledgeSession.fireAllRules();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			knowledgeSession.dispose();
		}
	}
}