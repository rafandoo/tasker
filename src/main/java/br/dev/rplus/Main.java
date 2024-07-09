package br.dev.rplus;

import br.dev.rplus.server.Server;
import br.dev.rplus.util.DatabaseFakerInitializer;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseFakerInitializer.run();

//        Server server = new Server();
//        server.main(args);
    }
}