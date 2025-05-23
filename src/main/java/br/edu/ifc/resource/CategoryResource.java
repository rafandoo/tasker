package br.edu.ifc.resource;

import br.edu.ifc.entity.Category;
import br.edu.ifc.service.CategoryService;
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

@Path("/category")
@Authenticated
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lista todas as categorias")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Listagem de categorias."),
    })
    public Response get(
        @Parameter(description = "Filtrar pelo nome da categoria") @QueryParam("name") String name
    ) {
        List<Category> categories = this.categoryService.get(name);
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Busca uma categoria pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Categoria encontrada com sucesso."),
        @APIResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    public Response getById(
        @Parameter(description = "Identificador da categoria", required = true) @PathParam("id") long id
    ) {
        Category category = this.categoryService.getById(id);
        return Response.ok(category).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Cria uma nova categoria")
    @RequestBody(description = "Categoria a ser criada", content = @Content(example = "{\"name\":\"categoria\"}"))
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Categoria criada com sucesso."),
        @APIResponse(responseCode = "400", description = "Erro de validação."),
        @APIResponse(responseCode = "500", description = "Erro no processo de criação da categoria.")
    })
    public Response create(Category category) {
        this.categoryService.create(category);
        JsonObject response = new JsonObject()
            .put("message", "Categoria criada com sucesso")
            .put("id", category.getId());

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Atualiza uma categoria existente")
    @RequestBody(description = "Dados para atualização da categoria", content = @Content(example = "{\"name\":\"categoria\"}"))
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Categoria atualizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Erro de validação."),
        @APIResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    public Response update(
        @Parameter(description = "Identificador da categoria", required = true) @PathParam("id") long id,
        Category category
    ) {
        Category updated = this.categoryService.update(id, category);
        JsonObject response = new JsonObject()
            .put("message", "Categoria atualizada com sucesso")
            .put("data", updated);

        return Response.ok(response).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Quantidade de categorias cadastradas")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Contagem de categorias."),
    })
    public Response count() {
        JsonObject response = new JsonObject()
            .put("categorias", this.categoryService.count());
        return Response.ok(response).build();
    }
}
