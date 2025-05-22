package br.edu.ifc.event;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;

/**
 * Classe responsável por gerir o carregamento de dados iniciais do sistema.
 */
@Singleton
public class Startup {


    /**
     * Função responsável por carregar os usuários iniciais do sistema.
     *
     * @param event evento de inicialização do sistema.
     */
    public void loadUsers(@Observes StartupEvent event) {

    }
}
