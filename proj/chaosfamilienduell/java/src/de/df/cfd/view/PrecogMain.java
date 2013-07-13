package de.df.cfd.view;

import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import de.df.cfd.controller.Game;
import de.df.cfd.model.GameData;

public class PrecogMain {
	private Game game;
	private int heighestVote;
	private int wrongAnswers = 0;

	private JTable tFamilies;
	private JComboBox cbVariant;
	private JButton bInitGame;
	private JTextField tfVariant;
	private JButton bStartRound;
	private JButton bShowAnswer;
	private JLabel lHeighestVote;
	private JButton bSwitchTeam;
	private JLabel lCurrentTeam;
	private JButton bStartFinal;
	private JTextArea taDebug;
	private JLabel lInitGame;
	private JButton bFinishRound;
	private JTextField tfTeam1Points;
	private JTextField tfTeam2Points;
	private JButton bUpdateCorrection;
	private JButton bMax;

	public PrecogMain(Game g) {
		super();

		game = g;

		JFrame f = new JFrame("Main Precog");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(560, 800);

		Panel p = new Panel(new MigLayout("wrap 3"));
		p.setSize(600, 800);
		p.setBackground(new Color(250, 250, 250));

		tFamilies = new javax.swing.JTable();
		tFamilies.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { "Family Name", "oqlt", "c3ma" }, { "Player 1", "Scytale", "pens" },
				{ "Player 2", "Inte", "dome" }, { "Player 3", "DreamFlasher", "flo" }, { "Player 4", "Abrock", "frame" },
				{ "Player 5", "silent_shadow", "heckpiet" } }, new String[] { "", "Family 1", "Family 2" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, true, true };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		p.add(tFamilies, "span 3, grow");

		cbVariant = new JComboBox();
		cbVariant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Variant 1 (1,1,2,3)", "Variant 2 (1,1.5,2)", "Variant 3 (1,1,1,2,3,3)" }));
		p.add(cbVariant, "span 2");

		p.add(new JLabel("rounds: "), "split 2");

		tfVariant = new JTextField("1,1,2,3");
		p.add(tfVariant, "grow");
		
		bMax = new JButton("maximize");
		bMax.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bMaxClicked(evt);
			}
		});
		p.add(bMax, "grow");

		bInitGame = new JButton("init game");
		bInitGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bInitGameClicked(evt);
			}
		});
		p.add(bInitGame, "grow");

		lInitGame = new JLabel("init game when ready");
		p.add(lInitGame);

		bStartRound = new JButton("start round");
		bStartRound.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bStartRoundClicked(evt);
			}
		});
		p.add(bStartRound, "grow");

		bShowAnswer = new JButton("show answer");
		bShowAnswer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bShowAnswerClicked(evt);
			}
		});
		p.add(bShowAnswer, "grow");

		p.add(new JLabel("heighest vote: "), "split 2");
		lHeighestVote = new JLabel();
		p.add(lHeighestVote);

		p.add(new JLabel());

		bSwitchTeam = new JButton("switch team");
		bSwitchTeam.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bSwitchTeamClicked(evt);
			}
		});
		p.add(bSwitchTeam, "grow");

//		p.add(new JLabel("current team: "), "split 2");
		lCurrentTeam = new JLabel();
		p.add(lCurrentTeam);

		bFinishRound = new JButton("finish round");
		bFinishRound.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bFinishRoundClicked(evt);
			}
		});
		p.add(bFinishRound, "wrap, grow");
		
		bStartFinal = new JButton("start final");
		bStartFinal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bStartFinalClicked(evt);
			}
		});
		p.add(bStartFinal, "wrap, grow");
		
		p.add(new JLabel(" "), "wrap");
		p.add(new JLabel("Correction: "), "wrap");
		p.add(new JLabel("team 1"));
		p.add(new JLabel("team 2"), "wrap");
		
		tfTeam1Points = new JTextField();
		p.add(tfTeam1Points, "grow");
		tfTeam2Points = new JTextField();
		p.add(tfTeam2Points, "grow");
		bUpdateCorrection = new JButton("update");
		bUpdateCorrection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bUpdateCorrectionCorrection(evt);
			}
		});
		p.add(bUpdateCorrection, "wrap");

		p.add(new JLabel(" "), "wrap");
		p.add(new JLabel("Debug: "), "wrap");

		taDebug = new JTextArea();
		taDebug.setAutoscrolls(true);
		taDebug.setRows(20);
		taDebug.setLineWrap(true);
		taDebug.setBorder(new LineBorder(Color.GRAY));
		p.add(taDebug, "span, grow, height 330!, width 540!");

		f.add(p);
		f.setVisible(true);

	}

	protected void bMaxClicked(ActionEvent evt) {
		game.getGuiPresentation().windowMinMax();
	}

	protected void bUpdateCorrectionCorrection(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void bFinishRoundClicked(ActionEvent evt) {
		game.finishRound();
	}

	protected void bSwitchTeamClicked(ActionEvent evt) {
		 int newTeam = game.switchTeam();
		 lCurrentTeam.setText("current team: " + game.getGameData().getTeamMemberName(newTeam, 0));
	}

	protected void bShowAnswerClicked(ActionEvent evt) {
		if (heighestVote == 0) {
			wrongAnswers++;
			String x = "";
			switch (wrongAnswers) {
			case 1:
				x = "X";
				break;
			case 2:
				x = "XX";
				break;
			case 3:
				x = "XXX";
				break;
			}
			game.getGuiPresentation().wrongAnswer(x);
		} else {
			game.getGuiPresentation().correctAnswer(heighestVote);
		}
		game.nextAnswer();
		lHeighestVote.setText("");
	}

	protected void bStartRoundClicked(ActionEvent evt) {
		game.startRound();

	}

	protected void bInitGameClicked(ActionEvent evt) {
		TableModel model = tFamilies.getModel();
		String[][] players = new String[model.getColumnCount() - 1][model.getRowCount()];
		for (int i = 1; i < model.getColumnCount(); i++) {
			for (int j = 0; j < model.getRowCount(); j++) {
				players[i - 1][j] = (String) model.getValueAt(j, i);
			}
		}

		String roundStrings[] = tfVariant.getText().split(",");
		float[] rounds = new float[roundStrings.length];
		for (int i = 0; i < roundStrings.length; i++) {
			rounds[i] = Float.parseFloat(roundStrings[i]);
		}

		game.initGame(new GameData(players, rounds));
		lInitGame.setText(game.getGameData().getTeamMemberName(0, 0) + " : " + game.getGameData().getTeamMemberName(1, 0));
		lCurrentTeam.setText(game.getGameData().getTeamMemberName(0, 0));

	}

	protected void bStartFinalClicked(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	public void addDebug(String string) {
		taDebug.append(string + System.getProperty("line.separator"));
	}

	public void updateVote(int heighestVote) {
		this.heighestVote = heighestVote;
		lHeighestVote.setText("" + heighestVote);
	}

}
