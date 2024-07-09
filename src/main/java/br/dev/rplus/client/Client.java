package br.dev.rplus.client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6080;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            JSONObject json = new JSONObject();
            json.put("client_id", UUID.randomUUID().toString());
            json.put("task_code", "1");
            json.put("cron_script", "");
            json.put("priority", 5);
            json.put("script", "UPDATE produtos SET preco = preco * 1.10;");
            output.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
