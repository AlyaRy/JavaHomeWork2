package hw14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static class Handler implements Runnable {
        private final Socket socket;
        public Handler (Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ": Установлено входящее соединение");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String clientMessage;
                System.out.println("Ожидаем сообщений");
                while ((clientMessage = in.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(clientMessage)) {
                        break;
                    }
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formatDateTime = LocalDateTime.now().format(format);
                    out.println("Ответ от сервера: " + formatDateTime + ": " + clientMessage);
                    System.out.println("Входящее сообщение: " + clientMessage);
                }
                System.out.println("1 соединение было закрыто");
                out.close();
                in.close();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту : " + port);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new Handler (clientSocket));
        }
    }
}