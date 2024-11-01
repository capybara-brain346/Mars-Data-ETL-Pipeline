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
                System.out.println("Connected to " + socket.getRemoteSocketAddress());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                List<String> request = new ArrayList<>();
                String line;
                while (!(line = bufferedReader.readLine()).isEmpty()) {
                    request.add(line);
                }

                System.out.println("Here" + request);

                String jsonResponse = "{\"message\": \"Hello, Client!\"}";
                int contentLength = jsonResponse.length();

                String httpResponse = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "Content-Length: " + contentLength + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n"
                        + jsonResponse;


                bufferedWriter.write(httpResponse);
                bufferedWriter.flush();
            }
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
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
