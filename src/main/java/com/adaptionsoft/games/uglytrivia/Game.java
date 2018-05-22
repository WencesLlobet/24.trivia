package com.adaptionsoft.games.uglytrivia;

public class Game {
    Players players = new Players();
    int[] board = new int[6];
    int[] score = new int[6];

    Board boardReal = new Board();
    QuestionsSource questionsSource = new QuestionsSource();
    
    Player currentPlayer;

    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){

    }

	public boolean add(String playerName) {
	    players.add(new Player(playerName,0, players.getNumberOfPlayers()));
	    board[players.getNumberOfPlayers()] = 0;
	    score[players.getNumberOfPlayers()] = 0;
	    currentPlayer = players.getAtPosition(0);
		return true;
	}

	public void roll(int roll) {
        printRollIntroduction(roll);
		if (currentPlayer.isInPenaltyBox()){
			if (isEven(roll)) {
                getOutOfPenalityBox();
                advanceCurrentPlayer(roll);
                askQuesion();
			} else {
                remainInPenalityBox();
			}
		} else {
            advanceCurrentPlayer(roll);
            askQuesion();
        }
		
	}

    private boolean isEven(int roll) {
        return roll % 2 != 0;
    }

    private void getOutOfPenalityBox() {
        isGettingOutOfPenaltyBox = true;
        System.out.println(currentPlayer + " is getting out of the penalty box");
    }

    private void remainInPenalityBox() {
        System.out.println(currentPlayer + " is not getting out of the penalty box");
        isGettingOutOfPenaltyBox = false;
    }

    private void askQuesion() {
        System.out.println("The category is " + boardReal.currentCategory(board[currentPlayer.getNumberOfPlayer()]));
        questionsSource.askQuestion(boardReal.currentCategory(board[currentPlayer.getNumberOfPlayer()]));
    }

    private void advanceCurrentPlayer(int roll) {
        board[currentPlayer.getNumberOfPlayer()] = board[currentPlayer.getNumberOfPlayer()] + roll;
        if (board[currentPlayer.getNumberOfPlayer()] > 11) board[currentPlayer.getNumberOfPlayer()] = board[currentPlayer.getNumberOfPlayer()] - 12;
        System.out.println(currentPlayer
                + "'s new location is "
                + board[currentPlayer.getNumberOfPlayer()]);
    }

    private void printRollIntroduction(int roll) {
        System.out.println(currentPlayer + " is the current player");
        System.out.println("They have rolled a " + roll);
    }

    public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer.increaseScoreByOne();
                printCurrentPlayerStatus();

                boolean winner = didPlayerWin();
                nextPlayer();
				
				return winner;
			} else {
                nextPlayer();
				return true;
			}
		} else {
		
			System.out.println("Answer was corrent!!!!");
            currentPlayer.increaseScoreByOne();
            printCurrentPlayerStatus();

            boolean winner = didPlayerWin();
            nextPlayer();
			
			return winner;
		}
	}

    private void nextPlayer() {
        currentPlayer = players.nextPlayerAfter(currentPlayer);
    }

    private void printCurrentPlayerStatus() {
        System.out.println(currentPlayer.getStatusString());
    }

    public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer+ " was sent to the penalty box");
		currentPlayer.setInPenaltyBox(true);
        nextPlayer();
		return true;
	}


	private boolean didPlayerWin() {
		return !(currentPlayer.getScore() == 6);
	}
}
