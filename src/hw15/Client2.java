package hw15;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client2 extends JFrame {
    JPanel jPanel;
    JTextArea client;
    JLabel label = new JLabel("Ответ от сервера");
    JTextArea server;
    JButton send = new JButton("Отправить");
    Container cont;
    public Client2() {
        cont = getContentPane();
        setSize(600, 100);
        setTitle("Многопоточный echo-сервер");
        jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        client = new JTextArea(1, 15);
        server = new JTextArea(1, 5);
        jPanel.add(client);
        jPanel.add(send);
        jPanel.add(label);
        jPanel.add(server);
        cont.add(jPanel);
        send.addActionListener(e -> {
            Socket s;
            try {
                s = new Socket("127.0.0.1", 1234);
            } catch (IOException unknownHostException) {
                unknownHostException.printStackTrace();
                s = null;
            }
            if (s == null)
                System.exit(-1);
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream(), true);
                out.flush();
                String temp = client.getText();
                out.println(temp);
                server.setText(in.readLine());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                try {
                    Objects.requireNonNull(out).close();
                    in.close();
                    s.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client Client2 = new Client();
            Client2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Client2.setVisible(true);
        });
    }
}