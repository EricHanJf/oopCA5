package org.example.Multithreaded;

import com.google.gson.*;
import org.example.DTOs.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 8888;
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");

            // Accept user input
//            System.out.println("Enter the ID of the entity you want to display:");
            while (true) {
                displayMenu();
                System.out.println("Please Enter Your Choice");

                String Choice = consoleInput.readLine();
                switch (Choice) {
                    case "1":
                        displayEntityById(out, in, consoleInput);
                        break;
                    case "2":
                        displayAllEntities(out, in);
                        break;
                    case "0":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in client: " + e.getMessage());
        }
    }
            // Send request to server
//            out.println("get " + entityId);
//
//            // Receive and process response
//            String response = in.readLine();
//            System.out.println("Response from server:");
//            System.out.println(response);
//
//        } catch (IOException e) {
//            System.err.println("Error in client: " + e.getMessage());
//        }
//    }

    private void displayMenu(){
        System.out.println("=========================================");
        System.out.println("|               Main Menu:              |");
        System.out.println("=========================================");
        System.out.println("|        1.  Display Entity by ID       |");
        System.out.println("|        2.  Display All Entities       |");
        System.out.println("|        0.  ->      Exit      <-       |");
        System.out.println("=========================================");
    }

    private void displayAllEntities(PrintWriter out, BufferedReader in)throws IOException{
        out.println("get all");

        System.out.println("Response from server (All Entities):");
        String line;
        while((line = in.readLine()) != null){
            System.out.println(line);
        }
    }

    private void displayEntityById(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        // Accept user input
        System.out.println("Enter the ID of the entity you want to display:");
        String entityId = consoleInput.readLine();

        // Send request to server
        out.println("get " + entityId);

        // Receive and display response
        String response = in.readLine();
        System.out.println("Response from server:");
        System.out.println(response);
    }

}










//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//
//public class Client {
//    private static final String SERVER_ADDRESS = "localhost";
//    private static final int SERVER_PORT = 8888;
//
//    public static void main(String[] args) {
//        Client client = new Client();
//        client.start();
//    }
//
//    public void start() {
//        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
//
//            System.out.println("Connected to server.");
//
//            // Accept user input
//            System.out.println("Enter the ID of the entity you want to display:");
//            String entityId = consoleInput.readLine();
//
//            // Send request to server
//            out.println("get " + entityId);
//
//            // Receive and process response
//            String response = in.readLine();
//            System.out.println("Response from server:");
//            displayFormattedData(response);
//
//        } catch (IOException e) {
//            System.err.println("Error in client: " + e.getMessage());
//        }
//    }
//
//
//
//    private void displayFormattedData(String jsonData) {
//        try {
////            Gson gson = new Gson();
//            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
//            Gson gson = new GsonBuilder().setDateFormat(((SimpleDateFormat) dateFormat).toPattern()).create();
//
//            Task task = gson.fromJson(jsonData, Task.class);
//
//            // Display header
//            System.out.println("+----+------------+------------+------------+------------------------------------------+---------------------+");
//            System.out.println("| ID |   Title    |   Status   |  Priority  |        Description                       |     Due Date        |");
//            System.out.println("+----+------------+------------+------------+------------------------------------------+---------------------+");
//
//            // Display task data
//            System.out.printf("| %-2d | %-10s | %-10s | %-10s | %-40s | %-19s |%n",
//                    task.getTaskId(), task.getTitle(), task.getStatus(), task.getPriority(), task.getDescription(), task.getDueDate());
//
//            // Display footer
//            System.out.println("+----+------------+------------+------------+------------------------------------------+---------------------+");
//        } catch (JsonSyntaxException e) {
//            System.out.println("Error parsing JSON data: " + e.getMessage());
//        }
//    }
//
//
//}

