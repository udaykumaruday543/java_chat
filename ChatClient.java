import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            // Thread for reading messages from the server
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages to the server
            String userMessage;
            while ((userMessage = stdIn.readLine()) != null) {
                out.println(userMessage);
            }
        } catch (IOException e) {
            System.out.println("Error in client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}