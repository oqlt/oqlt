package de.df.cfd.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.df.cfd.data.DataBase;
import de.df.cfd.model.GameData;
import de.df.cfd.model.Question;
import de.df.cfd.view.PrecogMain;
import de.df.cfd.view.Presentation;

/**
 * @author Marcel / DreamFlasher
 */
public class Game extends Thread {

	private DataBase dataBase;
	private Presentation guiPresentation;
	private GameData gameData;
	private int[] votedPrecogs;
	private int[] votes;
	private int currentVote = 0;
	private int numberOfPrecogs = 3;
	private GameServer gameServer;
	private static PrecogMain precogMain;
	boolean running = true;
	private Question q;

	public GameData getGameData() {
		return gameData;
	}

	// Game configuration

	/**
	 * use this to start the game
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new Game().start();
	}

	/**
	 * this is the main game thread to rule everything
	 * 
	 * @throws java.lang.InterruptedException
	 */
	@Override
	public void run() {
		startUp();
		while (running) {
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tearDown();
	}

	public void initGame(GameData gameData) {
		Game.logDebug("added new game data: " + gameData);
		this.gameData = gameData;
		guiPresentation.initGame(gameData.getTeamMemberName(0, 0), gameData.getTeamMemberName(1, 0));
	}

	public void resetVotes() {
		votedPrecogs = new int[numberOfPrecogs];
		votes = new int[numberOfPrecogs];
		currentVote = 0;
	}

	public void addVote(int precogID, int rightAnswer) {
		if (rightAnswer > 6 - gameData.getCurrentRound()) {
			rightAnswer = 0;
		}
		if (currentVote > votedPrecogs.length) { // enough votes
		// int v = heighestVote();
			return;
		}
		for (int i = 0; i < votedPrecogs.length; i++) { // do not vote twice for one precog
			if (votedPrecogs[i] == precogID) {
				return;
			}
		}
		votedPrecogs[currentVote] = precogID;
		votes[currentVote] = rightAnswer;
		currentVote++;
		precogMain.updateVote(heighestVote());
//		precogMain.addDebug("Precog: " + precogID + " | Answer: " + rightAnswer);
	}

	private int heighestVote() {
		int voteCount[] = new int[7];

		for (int i = 0; i < votes.length; i++) {
			voteCount[votes[i]]++; // count the number of votes for a given Xth answer
		}

		int heighestVoteNumber = 0;

		for (int i = 0; i < voteCount.length; i++) {
			if (voteCount[i] > voteCount[heighestVoteNumber]) {
				heighestVoteNumber = i;
			}
		}

		return heighestVoteNumber;
	}

	private void startUp() {
		dataBase = new DataBase();
		guiPresentation = new Presentation(this);
		precogMain = new PrecogMain(this);
		gameServer = new GameServer(31337, this);
		gameServer.start();
	}

	private void tearDown() {
		// TODO clean up database and stuff
	}

	public void startRound() {
		resetVotes();
		if (gameData.getCurrentRound() > gameData.getRoundWeights().length) {
			logDebug("you should do the final now");
			return;
		}
		q = dataBase.getRandomQuestion();
		guiPresentation.newQuestion(q);

		logDebug("new question: " + q.getText());
		gameServer.pushTopAnswers(q.getText(), q.getAnswers());
		logDebug("pushed answers: " + q.getAnswers());

		gameData.nextRound();
		logDebug("round " + gameData.getCurrentRound() + " of " + gameData.getRoundWeights().length + " weighing "
				+ gameData.getRoundWeights()[gameData.getCurrentRound()]);
	}
	
	public void nextAnswer() {
		resetVotes();
		gameServer.pushTopAnswers(q.getText(), q.getAnswers());
	}

	public static void logError(String msg) {
		Logger.getLogger(Game.class.getName()).log(Level.SEVERE, msg);
		precogMain.addDebug(msg);
	}

	public static void logDebug(String msg) {
		System.out.println("DEBUG: " + msg);
		Logger.getLogger(Game.class.getName()).log(Level.FINER, msg);
		precogMain.addDebug(msg);
	}

	public static void logInfo(String msg) {
		Logger.getLogger(Game.class.getName()).log(Level.INFO, msg);
		precogMain.addDebug(msg);
	}

	public static void logWarn(String msg) {
		Logger.getLogger(Game.class.getName()).log(Level.WARNING, msg);
		precogMain.addDebug(msg);
	}

	public Presentation getGuiPresentation() {
		return guiPresentation;
	}

	public int switchTeam() {
		return gameData.switchTeam();
	}
	
	public void finishRound() {
		guiPresentation.finishRound();
	}

}
