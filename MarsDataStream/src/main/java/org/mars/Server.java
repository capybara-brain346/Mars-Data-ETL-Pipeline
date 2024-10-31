package org.mars;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to " + socket.getRemoteSocketAddress());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                Object[] request = bufferedReader.lines().toArray();
                System.out.println(request[request.length - 1]);
                bufferedWriter.write((String) request[request.length - 1]);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeServer() {
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
