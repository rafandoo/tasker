package br.dev.rplus.client;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class Client2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            JSONObject json = new JSONObject();
            json.put("client_id", UUID.randomUUID().toString());
            json.put("task_code", "2");
            json.put("cron_script", "06-07-2024 16:14:00");
            json.put("priority", 2);
            json.put("script", "UPDATE usuarios SET senha_expirada = 1 WHERE DATE(data_atualizacao) < DATE('now', '-30 days');");

            // Enviar informações da tarefa ao servidor
            output.println(json.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
