package de.df.cfd.view;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Marcel / DreamFlasher
 */
public class PrecogClient {

	public static void main(String args[]) throws IOException, InterruptedException {
		System.out.println("Enter hostname:");
		// Scanner scanner = new Scanner(System.in); TODO
		// String hostname = scanner.next();
		String hostname = "localhost";
		int precogID = 0;

		Socket server = null;
		try {
			boolean connecting = true;
			System.out.print("connecting ");
			while(connecting) {
				try {
					server = new Socket(hostname, 31337);
					connecting = false;
				} catch (ConnectException e) {
					System.out.print(".");
					Thread.sleep(1000);
				}
			}
			System.out.println("");

			DataInputStream in = new DataInputStream(server.getInputStream());
			OutputStream out = server.getOutputStream();
			out.write(0);
			out.write(0);
			precogID = in.read();
			System.out.println("connection successful: precogID=" + precogID);

			while (true) {
				if (in.available() > 0) {
					System.out.println("Top Antworten:");
					System.out.println(in.readUTF());
					System.out.println("Antwort X ist richtig:");
					boolean success = false;
					while(!success) {
						int numberOfCorrectAnswer = System.in.read() - 48;
						if(0 <= numberOfCorrectAnswer && numberOfCorrectAnswer <= 6) {
							out.write(precogID);
							out.write(numberOfCorrectAnswer);
						} else {
							System.out.println("You n00b. Type 1-6 or 0!");
						}
						while (System.in.read() != 10) {
						}
						if (in.read() == 0) {
							System.out.println("ok");
							success = true;
						} else {
							System.out.println("error");
						}
						while(in.available() > 0) {
							in.read();
						}
					}
					System.out.println("");
				}
				Thread.sleep(1000);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
