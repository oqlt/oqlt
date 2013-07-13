package de.df.cfd.model;

import java.util.ArrayList;

/**
 * @author Marcel / DreamFlasher
 */
public class Question {
	private String text;
	private ArrayList<QuestionAnswer> answers = new ArrayList<QuestionAnswer>(6);

	public Question(String text, QuestionAnswer... answers) {
		super();
		this.text = text;
		for (QuestionAnswer answer : answers) {
			this.answers.add(answer);
		}
	}
	
	/**
	 * generates random points
	 * @param question
	 * @param answers
	 */
	public Question(String question, String answers[]) {
		super();
		this.text = question;
		int p = 0;
		int pointsLeft = 100;
		for (int i = 0; i < answers.length; i++) {
			p = pointsLeft / (i+2)^2;
			p -= Math.random()*10;
			p = p>0 ? p : 1;
			pointsLeft -= p;
			this.answers.add(new QuestionAnswer(answers[i], p));
		}
	}

	public Question(String text, ArrayList<QuestionAnswer> answers) {
		super();
		this.text = text;
		this.answers = answers;
	}



	public ArrayList<QuestionAnswer> getAnswers() {
		return answers;
	}

	public String getText() {
		return text;
	}

}
