package hw14;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        String serverIp = "127.0.0.1";
        int serverPort = 1234;
        System.out.println("Установлено соединение с сервером " + serverIp + ":" + serverPort);
        Socket server = new Socket(serverIp, serverPort);
        BufferedReader inServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintWriter outServer = new PrintWriter(server.getOutputStream(), true);
        BufferedReader inConsole = new BufferedReader(new InputStreamReader(System.in));
        String dataFromUser, dataFromServer;
        System.out.println("Введите сообщение для отправки или exit для выхода");
        while ((dataFromUser = inConsole.readLine()) != null) {
            if ("exit".equalsIgnoreCase(dataFromUser)) {
                System.out.println("Соединение закрыто");
                break;
            }
            outServer.println(dataFromUser);
            dataFromServer = inServer.readLine();
            System.out.println(dataFromServer);
        }
        outServer.close();
        inServer.close();
        outServer.close();
        server.close();
    }
}