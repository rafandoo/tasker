package br.edu.ifc.resource;

import br.edu.ifc.entity.Category;
import br.edu.ifc.service.CategoryService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestSecurity(user = "admin")
class TaskResourceTest {

    @Inject
    CategoryService categoryService;

    private long categoryId;

    @BeforeAll
    @Transactional
    void setup() {
        Category category = new Category();
        category.setName("TesteTarefa");
        this.categoryService.create(category);
        this.categoryId = category.getId();
    }

    private JsonObject getTask(String description, int priority) {
        return new JsonObject()
            .put("description", description)
            .put("category", new JsonObject().put("id", this.categoryId))
            .put("priority", priority);
    }

    private JsonObject invalidTaskJson() {
        return new JsonObject()
            .put("desctp", "Valor inválido")
            .put("categorya", new JsonObject().put("id", this.categoryId))
            .put("stat", 5);
    }

    @Test
    @Order(1)
    void createTaskSuccessfully() {
        given()
            .body(this.getTask("Teste", 5).toString())
            .contentType(ContentType.JSON)
        .when()
            .post("/task")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("message", is("Tarefa criada com sucesso"));
    }

    @Test
    @Order(2)
    void createTaskWithoutBody() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .post("/task")
        .then()
            .statusCode(500)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(3)
    void createTaskWithInvalidBody() {
        given()
            .body(this.invalidTaskJson().toString())
            .contentType(ContentType.JSON)
        .when()
            .post("/task")
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("message", is("Erro de validação"));
    }

    @Test
    @Order(4)
    void getAllTasks() {
        given()
        .when()
            .get("/task")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(1));
    }

    @Test
    @Order(5)
    void getTasksById() {
        given()
        .when()
            .get("/task/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("description", is("Teste"),
                "id", is(1));
    }

    @Test
    @Order(6)
    void getTaskByInvalidId() {
        given()
        .when()
            .get("/task/100")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Tarefa não encontrada"));
    }

    @Test
    @Order(7)
    void getTasksByQuery() {
        given()
            .param("categoryId", this.categoryId)
            .param("priority", 5)
        .when()
            .get("/task")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(1));
    }

    @Test
    @Order(8)
    void updateTaskSuccessfully() {
        given()
            .body(this.getTask("TesteNovo", 4).toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/task/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", is("Tarefa atualizada com sucesso"),
                "data.description", is("TesteNovo"),
                "data.priority", is(4));
    }

    @Test
    @Order(9)
    void updateTaskWithInvalidBody() {
        given()
            .body(this.invalidTaskJson().toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/task/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(10)
    void updateTestNotFound() {
        given()
            .body(this.getTask("TesteNovo", 4).toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/task/100")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Tarefa não encontrada"));
    }

    @Test
    @Order(11)
    void markAsDoneBeforeDoing() {
        given()
        .when()
            .patch("/task/1/done")
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("error", is("A tarefa só pode ser marcada como 'concluída' se estiver como 'em andamento'."));
    }

    @Test
    @Order(12)
    void markAsDoing() {
        given()
        .when()
            .patch("/task/1/doing")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", is("Status da tarefa atualizado com sucesso para 'em andamento'."));
    }

    @Test
    @Order(13)
    void markAsDoingNotFound() {
        given()
        .when()
            .patch("/task/100/doing")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Tarefa não encontrada"));
    }

    @Test
    @Order(14)
    void markAsDone() {
        given()
        .when()
            .patch("/task/1/done")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", is("Status da tarefa atualizado com sucesso para 'concluída'."));
    }

    @Test
    @Order(15)
    void markAsDoneNotFound() {
        given()
        .when()
            .patch("/task/100/done")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Tarefa não encontrada"));
    }

    @Test
    @Order(16)
    void deleteTaskSuccessfully() {
        given()
        .when()
            .delete("/task/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", is("Tarefa removida com sucesso"));
    }

    @Test
    @Order(17)
    void deleteTaskNotFound() {
        given()
        .when()
            .delete("/task/100")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Tarefa não encontrada"));
    }
}
