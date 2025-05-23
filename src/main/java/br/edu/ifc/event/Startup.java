package br.edu.ifc.event;

import br.edu.ifc.entity.User;
import br.edu.ifc.service.UserService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

/**
 * Classe responsável por gerir o carregamento de dados iniciais do sistema.
 */
@Singleton
public class Startup {

    @Inject
    UserService userService;

    /**
     * Função responsável por carregar os usuários iniciais do sistema.
     *
     * @param event evento de inicialização do sistema.
     */
    @Transactional
    public void loadUsers(@Observes StartupEvent event) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRoles("admin");
        if (this.userService.count() == 0) {
            this.userService.create(user);
        }
    }
}
