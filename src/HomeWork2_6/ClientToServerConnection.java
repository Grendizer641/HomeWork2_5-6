package HomeWork2_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientToServerConnection {

    private final String SERVER_ADDR = "myServer";
    private final int SERVER_PORT = 8190;
    private DataInputStream in;
    private DataOutputStream out;

    public void openConnectionClientToServer() {
        try (Socket socket = new Socket(SERVER_ADDR,SERVER_PORT)) {
            System.out.println("Осуществлено подключение Клиента к Серверу");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        readMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String strOut = scanner.nextLine();
            out.writeUTF(strOut);
            System.out.println("Отправленное серверу сообщение - " + strOut);
            if (strOut.equals("/end")){
                break;
            }
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            String strIn = in.readUTF();
            System.out.println("Полученное от сервера сообщение - " + strIn);
            if (strIn.equals("/end")) {
                break;
            }
        }
    }
}
