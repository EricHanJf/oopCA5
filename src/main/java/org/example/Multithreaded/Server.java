//package org.example.Multithreaded;
//
//import com.google.gson.Gson;
//import org.example.DAOs.MySqlTaskDAO;
//import org.example.DAOs.TaskDaoInterface;
//import org.example.DTOs.Task;
//import org.example.Exceptions.DaoException;
//import org.example.JSON.JsonConv;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.List;
//
//public class Server {
////    private static final int PORT = 8888;
//    final int SERVER_PORT_NUMBER = 8888;
//
//    public static void main(String[] args) {
//        Server server = new Server();
//        server.start();
//    }
//
//    public void start() {
//
//        ServerSocket serverSocket =null;
//        Socket clientSocket =null;
//
//        try {
//            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
//            System.out.println("Server has started.");
//            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)
//
//            while (true) {
//                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
//                clientSocket = serverSocket.accept();
//                clientNumber++;
//                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);
//
//                System.out.println("Server: Client " + clientNumber + " has connected.");
//                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
//                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());
//
//                // create a new ClientHandler for the requesting client, passing in the socket and client number,
//                // pass the handler into a new thread, and start the handler running in the thread.
//                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
//                t.start();
//
//                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");
//
//            }
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//        finally{
//            try {
//                if(clientSocket!=null)
//                    clientSocket.close();
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//            try {
//                if(serverSocket!=null)
//                    serverSocket.close();
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//
//        }
//        System.out.println("Server: Server exiting, Goodbye!");
//    }
//}
//
//class ClientHandler implements Runnable {
////    private final BufferedReader socketReader;
////    private final PrintWriter socketWriter;
////    private final TaskDaoInterface taskDao;
////    private final int clientNumber;
//
//    private Socket socket;
//
//    public ClientHandler(Socket socket) {
//        this.socket = socket;
//    }
////    private final JsonConv jsonConverter;
//
////    public ClientHandler(Socket clientSocket, int clientNumber) {
////        try {
////            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
////            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
////            this.clientNumber = clientNumber;
////            this.taskDao = new MySqlTaskDAO();
////        } catch (IOException e) {
////            throw new RuntimeException("Error initializing client handler", e);
////        }
////    }
//
//
//
////    @Override
////    public void run() {
////        try {
////            String requestType = socketReader.readLine();
////            switch (requestType) {
////                case "DISPLAY_BY_ID":
////                    displayEntityById();
////                    break;
////                default:
////                    System.out.println("Unsupported request type: " + requestType);
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//    @Override
//    public void run() {
//        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
//
//            String requestJson = in.readLine();
//            if (requestJson != null) {
//                Request request = new Gson().fromJson(requestJson, Request.class);
//
//                if ("DISPLAY_BY_ID".equals(request.type)) {
//                    displayEntityById(request.id, out);
//                } else {
//                    out.println("Unsupported request type");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error handling client: " + e.getMessage());
//        }
//    }
//
////    private void displayEntityById() {
////        try {
////            int entityId = Integer.parseInt(socketReader.readLine());
////            Task task = taskDao.getTaskById(entityId);
////
////            if (task != null) {
//////                String jsonData = JsonConv.TaskConversionToJson(task);
//////                socketWriter.println(jsonData);
//////                socketWriter.flush();
////                Gson gson = new Gson();
////                String jsonData = gson.toJson(task); // Convert Task object to JSON
////                socketWriter.println(jsonData);
////                socketWriter.flush();
////            } else {
////                socketWriter.println("null");
////                socketWriter.flush();
////            }
////        } catch (IOException | DaoException e) {
////            e.printStackTrace();
////        }
////    }
//
//    private void displayEntityById(int id, PrintWriter out) {
//        TaskDaoInterface taskDao = new MySqlTaskDAO();
//        try {
//            Task task = taskDao.getTaskById(id);
//            if (task != null) {
//                out.println(new Gson().toJson(task));
//            } else {
//                out.println("null");
//            }
//        } catch (DaoException e) {
//            out.println("null");
//            System.out.println("Error retrieving task: " + e.getMessage());
//        }
//    }
//
//}
//
////    private String handleRequest(String request) {
////        String[] tokens = request.split("\\s+");
////        String command = tokens[0].toLowerCase();
////        try {
////            switch (command) {
////                case "get": // Example: "get 1" (Get task by ID)
////                    int taskId = Integer.parseInt(tokens[1]);
////                    Task task = taskDao.getTaskById(taskId);
////                   if(task != null){
////                       String jsonTask = JsonConv.TaskConversionToJson(List.of(task));
////                        return jsonTask;
////                    }else {
////                        return "Task not found for ID: " + taskId;
////                    }
////                    // Add more cases for other commands (e.g., insert, update, delete, filter) as needed
////                default:
////                    return "Unknown command: " + command;
////            }
////        } catch (DaoException e) {
////            return "Error: " + e.getMessage();
////        }
////    }
//
//
//

package org.example.Multithreaded;

import com.google.gson.Gson;
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
import java.util.List;

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
    private Socket clientSocket;
    private int clientNumber;

    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestType = in.readLine();
//            out.println(requestType);
            if (requestType.startsWith("DISPLAY_BY_ID")) {
                int id = Integer.parseInt(requestType.substring(requestType.indexOf(":")+1));
//                out.println(id);
                displayEntityById(id, out);
            } else {
                out.println("Unsupported request type: " + requestType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayEntityById(int id, PrintWriter out) {
        try {
            TaskDaoInterface taskDao = new MySqlTaskDAO();
            Task task = taskDao.getTaskById(id);
            Gson gson = new Gson();
            String response = (task != null) ? gson.toJson(task) : "null";
            out.println(response);
        } catch (DaoException e) {
            out.println("Error accessing database: " + e.getMessage());
        }
    }
}


//    private String handleRequest(String request) {
//        String[] tokens = request.split("\\s+");
//        String command = tokens[0].toLowerCase();
//        try {
//            switch (command) {
//                case "get": // Example: "get 1" (Get task by ID)
//                    int taskId = Integer.parseInt(tokens[1]);
//                    Task task = taskDao.getTaskById(taskId);
//                   if(task != null){
//                       String jsonTask = JsonConv.TaskConversionToJson(List.of(task));
//                        return jsonTask;
//                    }else {
//                        return "Task not found for ID: " + taskId;
//                    }
//                    // Add more cases for other commands (e.g., insert, update, delete, filter) as needed
//                default:
//                    return "Unknown command: " + command;
//            }
//        } catch (DaoException e) {
//            return "Error: " + e.getMessage();
//        }
//    }



