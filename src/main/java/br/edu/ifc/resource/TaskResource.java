package br.edu.ifc.resource;

import br.edu.ifc.entity.Task;
import br.edu.ifc.resource.query.TaskQuery;
import br.edu.ifc.service.TaskService;
import br.edu.ifc.view.Mapper;
import br.edu.ifc.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.security.Authenticated;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/task")
@Authenticated
public class TaskResource {

    @Inject
    TaskService taskService;

    @Inject
    Mapper mapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @Operation(summary = "Lista todas as tarefas")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de tarefas encontradas com sucesso."),
    })
    public Response get(@BeanParam TaskQuery taskQuery) {
        List<Task> tasks = this.taskService.get(taskQuery);
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @Operation(summary = "Busca uma tarefa pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tarefa encontrada com sucesso."),
        @APIResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public Response getById(
        @Parameter(description = "Identificador da tarefa") @PathParam("id") long id
    ) {
        Task task = this.taskService.getById(id);
        return Response.ok(task).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Quantidade de tarefas cadastradas")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Contagem de tarefas."),
    })
    public Response count() {
        JsonObject response = new JsonObject()
            .put("tarefas", this.taskService.count());
        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Cria uma nova tarefa")
    @RequestBody(description = "Tarefa a ser criada", content = @Content(example = "{\"description\": \"Estudar Java\", \"category\": {\"id\": 1}, \"priority\": 3, \"dueDate\": \"2025-01-01T12:00:00.000+00:00\"}"))
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Tarefa criada com sucesso."),
        @APIResponse(responseCode = "400", description = "Dados inválidos para criação da tarefa."),
        @APIResponse(responseCode = "500", description = "Erro no processo de criação da tarefa.")
    })
    public Response create(Task task) {
        this.taskService.create(task);
        JsonObject response = new JsonObject()
            .put("message", "Tarefa criada com sucesso")
            .put("id", task.getId());

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(description = "Atualiza uma tarefa existente")
    @RequestBody(
        description = "Dados para atualização da tarefa",
        content = @Content(example = "{\"description\": \"Estudar Java\", \"category\": {\"id\": 1}, \"priority\": 3, \"dueDate\": \"2025-01-01T12:00:00.000+00:00\"}")
    )
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tarefa atualizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Dados inválidos para atualização da tarefa."),
        @APIResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public Response update(
        @Parameter(description = "Identificador da tarefa", required = true) @PathParam("id") long id,
        Task task
    ) throws JsonProcessingException {
        Task updated = this.taskService.update(id, task);
        JsonObject response = new JsonObject()
            .put("message", "Tarefa atualizada com sucesso")
            .put("data", mapper.toJson(updated, Views.Public.class));

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/doing")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(description = "Marca a tarefa como 'em andamento'")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tarefa atualizada com sucesso."),
        @APIResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public Response markAsDoing(
        @Parameter(description = "Identificador da tarefa", required = true) @PathParam("id") long id
    ) {
        this.taskService.markAsDoing(id);
        JsonObject response = new JsonObject()
            .put("message", "Status da tarefa atualizado com sucesso para 'em andamento'.");

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/done")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(description = "Marca a tarefa como 'concluída'")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tarefa atualizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Violação de regras de negócio."),
        @APIResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public Response markAsDone(
        @Parameter(description = "Identificador da tarefa", required = true) @PathParam("id") long id
    ) {
        this.taskService.markAsDone(id);
        JsonObject response = new JsonObject()
            .put("message", "Status da tarefa atualizado com sucesso para 'concluída'.");

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(description = "Remove uma tarefa")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tarefa removida com sucesso."),
        @APIResponse(responseCode = "404", description = "Tarefa não encontrada.")
    })
    public Response delete(
        @Parameter(description = "Identificador da tarefa", required = true) @PathParam("id") long id
    ) {
        this.taskService.delete(id);
        JsonObject response = new JsonObject()
            .put("message", "Tarefa removida com sucesso");

        return Response.ok(response).build();
    }
}
