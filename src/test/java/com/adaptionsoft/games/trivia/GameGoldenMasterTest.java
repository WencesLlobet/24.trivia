package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;
import com.adaptionsoft.games.uglytrivia.Roll;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class GameGoldenMasterTest {

    private static final int SEED = 4001;
    private static final int GAMES_TO_PLAY = 2000;

    @Test
	public void goldenMasterTest() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(os);
		System.setOut(printStream);

        runGameTimes(GAMES_TO_PLAY);
        String output = os.toString("UTF8");

		Approvals.verify(output);
	}

    private void runGameTimes(int times) {
        while(times > 0) {
            GameRunnerTest gameRunnerTest = new GameRunnerTest(SEED);
            gameRunnerTest.run();
            times--;
        }
    }

    private class GameRunnerTest {
        private final int seed;
        private boolean notAWinner;

        GameRunnerTest(int seed) {
            this.seed = seed;
        }

        void run() {
            Game aGame = new Game();

            aGame.add(new Player("Chet"));
            aGame.add(new Player("Pat"));
            aGame.add(new Player("Sue"));

            Random rand = new Random(seed);

            do {

                aGame.roll(new Roll(rand.nextInt(5) + 1));

                if (rand.nextInt(9) == 7) {
                    aGame.setCurrentPlayerInPenaltyBox();
                } else {
                    aGame.playTurn();
                }

                notAWinner = aGame.hasCurentPlayerNotWon();

                aGame.nextPlayer();

            } while (notAWinner);
        }
    }
}
