package br.edu.ifc.service;

import br.edu.ifc.entity.Task;
import br.edu.ifc.enums.Status;
import br.edu.ifc.exception.BusinessRuleViolationException;
import br.edu.ifc.exception.UnauthorizedException;
import br.edu.ifc.repository.TaskRepository;
import br.edu.ifc.resource.query.TaskQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public final class TaskService {

    @Inject
    TaskRepository taskRepository;

    @Inject
    UserService userService;

    public Task create(@Valid Task task) {
        task.setUser(this.userService.getBySC());
        this.taskRepository.persistAndFlush(task);
        return task;
    }

    public Task getById(long id) {
        Task task = this.taskRepository.findById(id);
        if (task == null) {
            throw new NotFoundException("Tarefa não encontrada");
        }
        if (!Objects.equals(task.getUser(), this.userService.getBySC())) {
            throw new UnauthorizedException("Usuário não autorizado a acessar a tarefa.");
        }
        return task;
    }

    public List<Task> get() {
        return this.taskRepository.find(
            "user.id = ?1 AND (status = ?2 OR status = ?3)",
            this.userService.getBySC().getId(),
            Status.TODO,
            Status.DOING
        ).list();
    }

    public List<Task> get(TaskQuery taskQuery) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (taskQuery.getCategoryId() != 0) {
            query.append(" AND category.id = :categoryId");
            params.put("categoryId", taskQuery.getCategoryId());
        }

        if (taskQuery.getStatus() != null) {
            query.append(" AND status = :status");
            params.put("status", taskQuery.getStatus());
        }

        if (taskQuery.getDescription() != null && !taskQuery.getDescription().isBlank()) {
            query.append(" AND LOWER(description) LIKE :description");
            params.put("description", "%" + taskQuery.getDescription().toLowerCase() + "%");
        }

        if (taskQuery.getPriority() > 0 && taskQuery.getPriority() <= 5) {
            query.append(" AND priority = :priority");
            params.put("priority", taskQuery.getPriority());
        }

        if (taskQuery.getDueDate() != null) {
            query.append(" AND dueDate = :dueDate");
            params.put("dueDate", taskQuery.getDueDate());
        }

        if (params.isEmpty()) {
            return this.get();
        } else {
            query.append(" AND user.id = :userId");
            params.put("userId", this.userService.getBySC().getId());
        }

        return this.taskRepository.find(query.toString(), params).list();
    }

    public long count() {
        return this.taskRepository.count();
    }

    public Task update(long id, Task updated) {
        Task existing = this.getById(id);

        if (updated.getDescription() != null && !updated.getDescription().isBlank()) {
            existing.setDescription(updated.getDescription());
        }

        if (updated.getCategory() != null && updated.getCategory().getId() > 0) {
            existing.setCategory(updated.getCategory());
        }

        if (updated.getPriority() > 0 && updated.getPriority() <= 5) {
            existing.setPriority(updated.getPriority());
        }

        if (updated.getDueDate() != null) {
            existing.setDueDate(updated.getDueDate());
        }

        return this.taskRepository.getEntityManager().merge(existing);
    }

    private Task updateStatus(long id, Status status) {
        Task task = this.getById(id);

        if (status == Status.DONE && task.getStatus() != Status.DOING) {
            throw new BusinessRuleViolationException("A tarefa só pode ser marcada como 'concluída' se estiver como 'em andamento'.");
        }

        task.setStatus(status);
        return this.taskRepository.getEntityManager().merge(task);
    }

    public Task markAsDoing(long id) {
        return this.updateStatus(id, Status.DOING);
    }

    public Task markAsDone(long id) {
        return this.updateStatus(id, Status.DONE);
    }

    public void delete(long id) {
        Task task = this.getById(id);
        this.taskRepository.delete(task);
    }
}
