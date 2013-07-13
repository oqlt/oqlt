package de.df.cfd.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import de.df.cfd.controller.Game;
import de.df.cfd.model.Question;
import de.df.cfd.model.QuestionAnswer;

public class Presentation {

	private JLabel lquestion;
	private ArrayList<JLabel> lnumbers;
	private ArrayList<JLabel> lanswers;
	private ArrayList<JLabel> lpoints;
	private JLabel lteam1name;
	private JLabel lxxx;
	private JLabel lsum;
	private JLabel lteam2name;
	private JLabel lteam1points;
	private JLabel lroundpoints;
	private JLabel lteam2points;
	private JFrame f;
	private boolean undecorated = false;

	private static Game game;
	private final static String LINE = "_____________________________________";
	private Question q;

	public Presentation(Game g) {
		super();

		game = g;

		// the frame is the application itself
		f = new JFrame("Presentation");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 786);
		// f.setUndecorated(true);

		// the panel contains all components and fills the application completely
		Panel p = new Panel(new MigLayout("wrap 5, ins 0", "[left, ::30][left, grow][center][right, grow][right, ::50]"));
		p.setBackground(Color.BLACK);
		p.setSize(1024, 786);

		JLabel lchaosfamilienduell = new JLabel("chaos familien duell");
		Font ericsson82 = new Font("Ericsson GA628", Font.PLAIN, 82);
		lchaosfamilienduell.setFont(ericsson82);
		lchaosfamilienduell.setForeground(Color.GREEN);
		p.add(lchaosfamilienduell, "span 5, gaptop 5, center");

		lquestion = new JLabel("...");
		Font texasLED41 = new Font("Texas LED", Font.PLAIN, 41);
		lquestion.setFont(texasLED41);
		lquestion.setForeground(Color.GREEN);
		p.add(lquestion, "span 5, gaptop 30");

		int numberOfAnswers = 6;
		lnumbers = new ArrayList<JLabel>(numberOfAnswers);
		lanswers = new ArrayList<JLabel>(numberOfAnswers);
		lpoints = new ArrayList<JLabel>(numberOfAnswers);
		for (int i = 0; i < numberOfAnswers; i++) {
			lnumbers.add(new JLabel("" + (i + 1))); // ? is this okay in a loop? Better String.valueOf()?
			lnumbers.get(i).setFont(texasLED41);
			lnumbers.get(i).setForeground(Color.GREEN);
			p.add(lnumbers.get(i));
			lanswers.add(new JLabel(LINE)); // ? Better to put in a constant?
			lanswers.get(i).setFont(texasLED41);
			lanswers.get(i).setForeground(Color.GREEN);
			p.add(lanswers.get(i), "gapleft 8, span 3");
			lpoints.add(new JLabel("--"));
			lpoints.get(i).setFont(texasLED41);
			lpoints.get(i).setForeground(Color.GREEN);
			p.add(lpoints.get(i));
		}

		p.add(new JLabel(" "), "wrap, height 40!");

		lteam1name = new JLabel("?");
		Font ericsson55 = new Font("Ericsson GA628", Font.PLAIN, 55);
		lteam1name.setFont(ericsson55);
		lteam1name.setForeground(Color.GREEN);
		p.add(lteam1name, "span 2 2, bottom");

		lxxx = new JLabel("");
		lxxx.setFont(new Font("Ericsson GA628", Font.PLAIN, 160));
		lxxx.setForeground(Color.RED);
		p.add(lxxx, "span 1 2, height 140!, gapleft 24");

		lsum = new JLabel("Σ 0");
		lsum.setFont(ericsson55);
		lsum.setForeground(Color.GREEN);
		p.add(lsum, "span 2, bottom");

		lteam2name = new JLabel("?");
		lteam2name.setFont(ericsson55);
		lteam2name.setForeground(Color.GREEN);
		p.add(lteam2name, "wrap, span 2, bottom");

		p.add(new JLabel(" "), "wrap, height 50!");

		JPanel greenBG = new JPanel(new MigLayout("wrap 3", "[left, grow][center, grow][right, grow]"));
		greenBG.setBackground(Color.GREEN);
		greenBG.setOpaque(true);
		p.add(greenBG, "span 5, height 100!, grow");

		lteam1points = new JLabel("0");
		lteam1points.setFont(ericsson82);
		lteam1points.setForeground(Color.BLACK);
		greenBG.add(lteam1points, "gaptop 8");

		lroundpoints = new JLabel("0");
		lroundpoints.setFont(ericsson82);
		lroundpoints.setForeground(Color.BLACK);
		greenBG.add(lroundpoints, "gaptop 8");

		lteam2points = new JLabel("0");
		lteam2points.setFont(ericsson82);
		lteam2points.setForeground(Color.BLACK);
		greenBG.add(lteam2points, "gaptop 8");

		f.add(p);
		f.setVisible(true);
	}

	public void newQuestion(Question q) {
		this.q = q;
		lquestion.setText("... " + q.getText() + "");
		for (int i = 0; i < 6; i++) {
			lanswers.get(i).setText(LINE);
			lpoints.get(i).setText("--");

			if (i < 6 - game.getGameData().getCurrentRound()) {
				lnumbers.get(i).setVisible(true);
				lanswers.get(i).setVisible(true);
				lpoints.get(i).setVisible(true);
			} else {
				lnumbers.get(i).setVisible(false);
				lanswers.get(i).setVisible(false);
				lpoints.get(i).setVisible(false);
			}
		}
		lxxx.setText("");
		lsum.setText("� 0");
	}

	public void correctAnswer(int i) {
		String answerText = q.getAnswers().get(i - 1).getText();
		int points = q.getAnswers().get(i - 1).getPoints();
		int newSum = Integer.parseInt(lsum.getText().substring(2)) + points;
		float weight = game.getGameData().getRoundWeights()[game.getGameData().getCurrentRound()];
		int newRoundPoints = (int) (newSum * weight);
		(new TypeWriter()).write(lanswers.get(i - 1), answerText, lpoints.get(i - 1), points, newSum, newRoundPoints);
	}

	public void wrongAnswer(String x) {
		lxxx.setText(x);
	}

	public void initGame(String team1name, String team2name) {
		newQuestion(new Question("", new ArrayList<QuestionAnswer>(6)));
		lsum.setText("� 0");
		lteam1name.setText(team1name);
		lteam1points.setText("0");
		lteam2name.setText(team2name);
		lteam2points.setText("0");
		lroundpoints.setText("0");
	}

	private class TypeWriter extends Thread {
		private JLabel lAnswer;
		private String answer;
		private JLabel lPoints;
		private int points;
		private int sum;
		private int roundpoints;

		public void write(JLabel lAnswer, String answer, JLabel lPoints, int points, int sum, int roundpoints) {
			this.lAnswer = lAnswer;
			this.answer = answer;
			this.lPoints = lPoints;
			this.points = points;
			this.sum = sum;
			this.roundpoints = roundpoints;
			this.start();
		}

		public void run() {
			super.run();

			try {
				for (int i = 0; i <= answer.length(); i++) {
					lAnswer.setText(answer.substring(0, i));
					Thread.sleep(250);
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			lPoints.setText("" + points);
			lsum.setText("� " + sum);
			lroundpoints.setText("" + roundpoints);
		}

	}
	
	private class CountPoints extends Thread {
		public void run() {
			super.run();
			
			int roundPoints = Integer.parseInt(lroundpoints.getText());
			JLabel lWinner = game.getGameData().getCurrentTeam() == 0 ? lteam1points: lteam2points;
			int oldPointsWinner = Integer.parseInt(lWinner.getText());
			for (; roundPoints >= 0; roundPoints--) {
				lroundpoints.setText("" + roundPoints);
				lWinner.setText("" + (oldPointsWinner++));
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void finishRound() {
		(new CountPoints()).start();
	}
	
	public void windowMinMax() {
		undecorated = !undecorated;
		f.dispose();
		f.setUndecorated(undecorated);
		f.setVisible(true);
	}
}
