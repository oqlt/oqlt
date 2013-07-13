package de.df.cfd.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.df.cfd.model.QuestionAnswer;

/**
 * @author Marcel / DreamFlasher
 */
public class GameServer extends Thread {

    private ServerSocket server;
    private int newPrecogID = 10;
    private Game game;
    private ArrayList<ServerThread> servers;

    public GameServer(int port, Game game) {
        try {
            server = new ServerSocket(port);
            servers = new ArrayList<ServerThread>();
            this.game = game;
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                ServerThread st = new ServerThread(client);
                st.start();
                servers.add(st);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    public static void main(String args[]) throws IOException, InterruptedException {
//        GameServer gs = new GameServer(31337, new Game());
//        gs.start();
//        while (true) {
//            System.out.println("press enter to push example answers");
//            System.in.read();
//            gs.pushExampleAnswers();
//        }
//    }

//    private void pushExampleAnswers() {
//        ArrayList<QuestionAnswer> answers = new ArrayList<QuestionAnswer>();
//        answers.add(new QuestionAnswer("A", 40));
//        answers.add(new QuestionAnswer("B", 30));
//        answers.add(new QuestionAnswer("C", 20));
//        answers.add(new QuestionAnswer("D", 10));
//        answers.add(new QuestionAnswer("E", 5));
//        answers.add(new QuestionAnswer("F", 5));
//        pushTopAnswers("foobar",answers);
//    }

    public void pushTopAnswers(String question, ArrayList<QuestionAnswer> answers) {
        for (ServerThread st : servers) {
            st.pushTopAnswers(question, answers);
        }
    }

    private class ServerThread extends Thread {

        private Socket client;
        private InputStream in;
        private DataOutputStream out;

        private ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    in = client.getInputStream();
                    out = new DataOutputStream(client.getOutputStream());
                    int precogID = in.read();
                    int rightAnswer = in.read(); //number 1-6 are allowed, rest==no right answer in the top list
                    if (precogID == 0) {
                        //new precog
                        Game.logDebug("new precog with id " + newPrecogID);
                        out.write(newPrecogID++);
                    } else {
                    	if(0 <= rightAnswer && rightAnswer <= 6) {
                    		game.addVote(precogID, rightAnswer);
                    		Game.logDebug("new vote from precog " + precogID + ": " + rightAnswer);
                    		out.write(0); //success
                    	} else {
                    		out.write(-1); //failure
                    	}
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public void pushTopAnswers(String question, ArrayList<QuestionAnswer> answers) {
            System.out.println("pushingTopAnswers");
            StringBuffer s = new StringBuffer();
            int i = 0;
            s.append(question);
            s.append('!');
            s.append(System.getProperty("line.separator"));
            for (QuestionAnswer answer : answers) {
                i++;
                s.append(i);
                s.append(" ");
                s.append(answer.getText());
                s.append(System.getProperty("line.separator"));
            }
            //game.debug(s.toString());
            try {
                out.writeUTF(s.toString());
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
