
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;
import com.adaptionsoft.games.uglytrivia.Roll;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.add(new Player("Chet"));
		aGame.add(new Player("Pat"));
		aGame.add(new Player("Sue"));
		
		Random rand = new Random();
	
		do {
			
			aGame.roll(new Roll(rand.nextInt(5) + 1));
			
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.setCurrentPlayerInPenaltyBox();
			} else {
				aGame.playTurn();
				notAWinner = aGame.hasCurentPlayerNotWon();
			}
			
			
			
		} while (notAWinner);
		
	}
}
