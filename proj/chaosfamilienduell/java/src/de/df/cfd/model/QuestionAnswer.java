package de.df.cfd.model;

/**
 *
 * @author Marcel / DreamFlasher
 */
public class QuestionAnswer {
    private String text;
    private int points;

    public QuestionAnswer(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "<"+this.text+"("+this.points+")>";
    }

    
}
