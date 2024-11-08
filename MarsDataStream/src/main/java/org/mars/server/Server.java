package org.mars.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                try (socket;
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                ) {
                    System.out.println("Connected to " + socket.getRemoteSocketAddress());
                    List<String> requestHeaders = new ArrayList<>();
                    StringBuilder requestBody = new StringBuilder();
                    String line;

                    // TODO Needs to be fixed! Somehow leads to an infinite loop blocking the acknowledgement from the server
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.isEmpty()) {
                            break;
                        }
                        requestHeaders.add(line);
                    }

                    while ((line = bufferedReader.readLine()) != null) {
                        requestBody.append(line).append("\n");
                    }

                    System.out.println("Headers:");
                    for (String header : requestHeaders) {
                        System.out.println(header);
                    }

                    System.out.println("Body:");
                    System.out.println(requestBody.toString().trim());

                    String httpResponse = getHttpResponse();

                    bufferedWriter.write(httpResponse);
                    bufferedWriter.flush();
                } catch (IOException e) {
                    System.out.println("Error handling client request: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getHttpResponse() {
        String jsonResponse = "{\"message\": \"Attributes received!\"}";
        int contentLength = jsonResponse.length();

        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "Connection: close\r\n"
                + "\r\n"
                + jsonResponse;
    }

    private void closeServer() {
        try {
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(8080));
        server.startServer();
    }
}
