package HomeWork2_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerStartAndClientConnectionAccept {

    private final String SERVER_ADDR = "myServer";
    private final int SERVER_PORT = 8190;
    private DataInputStream in;
    private DataOutputStream out;

    public void startServerAndAcceptConnectionFromClient() {
        try (ServerSocket socketServer = new ServerSocket(SERVER_PORT)) {
            System.out.println("Сервер запущен");
            Socket socketClient = socketServer.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socketClient.getInputStream());
            out = new DataOutputStream(socketClient.getOutputStream());
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
            System.out.println("Отправленное клиенту сообщение - " + strOut);
            if (strOut.equals("/end")){
                break;
            }
        }
    }

    private void readMessage() throws IOException {
        while (true) {
            String strIn = in.readUTF();
            System.out.println("Полученное от клиента сообщение - " + strIn);
            if (strIn.equals("/end")) {
                break;
            }
        }
    }
}
