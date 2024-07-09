package br.dev.rplus.manager;

import br.dev.rplus.model.ScheduledTask;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.PriorityQueue;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final TaskManager taskManager;
    private final PriorityQueue<ScheduledTask> taskQueue;

    public ClientHandler(Socket clientSocket, TaskManager taskManager, PriorityQueue<ScheduledTask> taskQueue) {
        this.clientSocket = clientSocket;
        this.taskManager = taskManager;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                sb.append(line);
            }
            JSONObject request = new JSONObject(sb.toString());
            ScheduledTask task;

            String clientId = request.getString("client_id");
            System.out.println("Cliente conectado: " + clientId);

            System.out.println("Processando a requisição do cliente");
            task = taskManager.processRequest(request);

            JSONObject response = new JSONObject();
            if (task == null) {
                response.put("status", "error");
                response.put("message", "Tarefa inválida");
            } else {
                if (task.isScheduled()) {
                    // Agendar a tarefa no scheduler
                    taskManager.scheduleTask(task);

                    response.put("status", "scheduled");
                    response.put("message", "Tarefa agendada: " + task.getTaskCode());
                } else {
                    // Adicionar tarefa à fila de prioridade
                    synchronized (taskQueue) {
                        taskQueue.offer(task);
                        taskQueue.notify();

                        response.put("status", "queued");
                        response.put("message", "Tarefa adicionada à fila: " + task.getTaskCode());
                    }
                }
            }

            output.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}