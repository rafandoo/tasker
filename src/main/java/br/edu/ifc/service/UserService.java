package br.edu.ifc.service;

import br.edu.ifc.entity.User;
import br.edu.ifc.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public final class UserService {

    @Inject
    UserRepository userRepository;

    @Context
    SecurityContext securityContext;

    public User create(@Valid User user) {
        this.userRepository.persistAndFlush(user);
        return user;
    }

    public User getBySC() {
        if (this.securityContext.getUserPrincipal() == null) {
            throw new NotFoundException("Usuário autenticado não encontrado no contexto de segurança.");
        }
        return this.getByUsername(this.securityContext.getUserPrincipal().getName());
    }

    public User getByUsername(String username) {
        User user = this.userRepository.find("username = ?1", username).firstResult();
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado: " + username);
        }
        return user;
    }

    public long count() {
        return this.userRepository.count();
    }

}
