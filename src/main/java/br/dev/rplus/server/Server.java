package br.dev.rplus.server;

import br.dev.rplus.manager.ClientHandler;
import br.dev.rplus.manager.TaskManager;
import br.dev.rplus.manager.TaskProcessor;
import br.dev.rplus.model.ScheduledTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Server {
    private static final int PORT = 6080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private static final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando servidor de execução de tarefas...");
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado e escutando na porta " + PORT);
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }

        // Iniciar thread para processar a fila de tarefas
        new Thread(new TaskProcessor(taskQueue, executorService)).start();

        TaskManager manager = new TaskManager(scheduler);

        while (true) {
            assert serverSocket != null;
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket, manager, taskQueue)).start();
        }
    }
}