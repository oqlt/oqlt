package de.df.cfd.data;

import java.util.ArrayList;

import de.df.cfd.model.Question;

/**
 * @author Marcel / DreamFlasher
 */
public class DataBase {

    private ArrayList<Question> questionPool = new ArrayList<Question>(20);

    public DataBase() {
//    	questionPool.add(new Question("", new String[]{"", "", "", "", "", ""}));
    	questionPool.add(new Question("einen Hacker", new String[]{"Linus Torvalds", "ich", "R.S.", "Harald Welte", "Neal Patrick", "Kevin Flynn"}));
    	questionPool.add(new Question("eine freie Software, die nicht unter der GPL steht", new String[]{"Apache HTTPD", "Mozilla Firefox", "BIND", "OpenBSD", "Ruby on Rails", "curl"}));
    	questionPool.add(new Question("eine Microarchitektur", new String[]{"x68", "RISC", "CISC", "x64", "Branch Prediction", "Dei Mudda"}));
    	questionPool.add(new Question("einen Hersteller von Diskettenlaufwerken", new String[]{"Fujitsu Siemens", "Sony", "Alps", "Iomega", "HP", "Toshiba"}));
    	questionPool.add(new Question("einen Problempolitiker", new String[]{"Wolfgang Schäuble", "Ursula von der Leyen", "Brigitte Zypries", "Angela Merkel", "Franz Josef Jung", "Wolfgang Tiefensee"}));
    	questionPool.add(new Question("ein Betriebssystem-Maskottchen", new String[]{"Tux", "Puffy", "Fred", "Beastie", "Tuz", "Duke"}));
    	questionPool.add(new Question("ein Betriebssystem", new String[]{"Windows", "Linux", "MacOS", "Solaris", "Android", "Symbian"}));
    	questionPool.add(new Question("einen nicht grafischen Editor", new String[]{"vi", "", "", "", "", ""}));
    	questionPool.add(new Question("eine obskure Programmiersprache", new String[]{"Whitespace", "Brainfuck", "Ook!", "Java2K", "LOLCODE", "Pascal"}));
    	questionPool.add(new Question("einen beliebten Webcomic", new String[]{"xkcd", "ctrl+alt+del", "Dilbert", "Garfield minus Garfield", "", ""}));
    	questionPool.add(new Question("eine Newsseite (außer heise)", new String[]{"fefe", "", "", "", "", ""}));
    	questionPool.add(new Question("eine Quelle für kostenlose Pornografie", new String[]{"youporn", "", "", "", "", ""}));
    	questionPool.add(new Question("das schlechteste Kernel-Modul", new String[]{"", "", "", "", "", ""}));
    	questionPool.add(new Question("ein Bugtracker", new String[]{"", "", "", "", "", ""}));
    	questionPool.add(new Question("eine überbewertete Technologie", new String[]{"SOA", "", "", "", "", ""}));
    	questionPool.add(new Question("eine Softwarelizenz", new String[]{"GPL", "", "", "", "", ""}));
    	questionPool.add(new Question("eine bekannte Internetkultfigur", new String[]{"", "", "", "", "", ""}));
    	questionPool.add(new Question("einen bekannten Programmierer", new String[]{"Linus Torvalds", "Bill Gates", "", "", "", ""}));
    	questionPool.add(new Question("einen asymmetrischen Verschlüsselungsalgorithmus", new String[]{"RSA", "", "", "", "", ""}));
    	questionPool.add(new Question("ein beliebtes Versionskontrollsystem", new String[]{"SVN", "CVS", "GIT", "Mercury", "", ""}));
    	questionPool.add(new Question("ein koffeinhaltiges Getränk", new String[]{"Kaffee", "Schwarzer Tee", "Mate", "Red Bull", "", ""}));
    	questionPool.add(new Question("einen möglichst unbekanten Unix-Befehl", new String[]{"", "", "", "", "", ""}));
    	questionPool.add(new Question("ein alternatives Tastaturlayout", new String[]{"Dvorak", "neo", "", "", "", ""}));
    	questionPool.add(new Question("totes Datenträgerformat", new String[]{"HD-DVD", "", "", "", "", ""}));
    	questionPool.add(new Question("eine Verschwörungstheorie", new String[]{"Mondlandung", "", "", "", "", ""}));
    }

    public Question getRandomQuestion(){
        int i = (int) (Math.random()*questionPool.size());
        Question q = questionPool.get(i);
        questionPool.remove(i);
        return q;
    }
}
