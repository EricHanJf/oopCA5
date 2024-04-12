package org.example.Multithreaded;

import org.example.DAOs.MySqlTaskDAO;
import org.example.DAOs.TaskDaoInterface;
import org.example.DTOs.Task;
import org.example.Exceptions.DaoException;
import org.example.JSON.JsonConv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
//    private static final int PORT = 8888;
    final int SERVER_PORT_NUMBER = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket and client number,
                // pass the handler into a new thread, and start the handler running in the thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

class ClientHandler implements Runnable {
    private final BufferedReader socketReader;
    private final PrintWriter socketWriter;
    private final TaskDaoInterface taskDao;
    private final JsonConv jsonConverter;

    public ClientHandler(Socket clientSocket, int clientNumber) {
        try {
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.taskDao = new MySqlTaskDAO(); // Instantiate the TaskDaoInterface implementation
            this.jsonConverter = new JsonConv(); // Instantiate the JsonConv
        } catch (IOException e) {
            throw new RuntimeException("Error initializing client handler", e);
        }
    }

    @Override
    public void run() {
        String request;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client: " + request);

                // Process the request and send response accordingly
                String response = handleRequest(request);
                socketWriter.println(response);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socketReader.close();
                socketWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler is terminating...");
    }

    private String handleRequest(String request) {
        String[] tokens = request.split("\\s+");
        String command = tokens[0].toLowerCase();
        try {
            switch (command) {
                case "get": // Example: "get 1" (Get task by ID)
                    int taskId = Integer.parseInt(tokens[1]);
                    Task task = taskDao.getTaskById(taskId);
                    return jsonConverter.entityToJson(task);
                // Add more cases for other commands (e.g., insert, update, delete, filter) as needed
                default:
                    return "Unknown command: " + command;
            }
        } catch (DaoException e) {
            return "Error: " + e.getMessage();
        }
    }
}

