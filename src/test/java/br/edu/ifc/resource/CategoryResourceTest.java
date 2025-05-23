package br.edu.ifc.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestSecurity(user = "admin")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryResourceTest {

    private JsonObject getCategory(String name) {
        return new JsonObject()
            .put("name", name);
    }

    private JsonObject invalidCategoryJson() {
        return new JsonObject().put("namea", "Valor inválido");
    }

    @Test
    @Order(1)
    void createCategorySuccessfully() {
        given()
            .body(this.getCategory("Teste").toString())
            .contentType(ContentType.JSON)
        .when()
            .post("/category")
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("message", is("Categoria criada com sucesso"));
    }

    @Test
    @Order(2)
    void createCategoryWithoutBody() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .post("/category")
        .then()
            .statusCode(500)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(3)
    void createCategoryWithInvalidBody() {
        given()
            .body(this.invalidCategoryJson().toString())
            .contentType(ContentType.JSON)
        .when()
            .post("/category")
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("message", is("Erro de validação"));
    }

    @Test
    @Order(4)
    void getAllCategories() {
        given()
        .when()
            .get("/category")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(1));
    }

    @Test
    @Order(5)
    void getCategoriesByName() {
        given()
            .param("name", "Teste")
        .when()
            .get("/category")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(1));
    }

    @Test
    @Order(6)
    void getCategoriesByInvalidName() {
        given()
            .param("name", "Inexistente")
        .when()
            .get("/category")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(0));
    }

    @Test
    @Order(7)
    void getCategoryById() {
        given()
        .when()
            .get("/category/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name", is("Teste"),
                "id", is(1));
    }

    @Test
    @Order(8)
    void getCategoryByInvalidId() {
        given()
        .when()
            .get("/category/100")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Categoria não encontrada"));
    }

    @Test
    @Order(9)
    void updateCategorySuccessfully() {
        given()
            .body(this.getCategory("TesteNovo").toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/category/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("message", is("Categoria atualizada com sucesso"),
                "data.name", is("TesteNovo"));
    }

    @Test
    @Order(10)
    void updateCategoryWithInvalidBody() {
        given()
            .body(this.invalidCategoryJson().toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/category/1")
        .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("message", is("Erro de validação"));
    }

    @Test
    @Order(11)
    void updateCategoryNotFound() {
        given()
            .body(this.getCategory("TesteNovo").toString())
            .contentType(ContentType.JSON)
        .when()
            .put("/category/100")
        .then()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("error", is("Categoria não encontrada"));
    }

    @Test
    @Order(12)
    void updateCategoryWithoutBody() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .put("/category/1")
        .then()
            .statusCode(500)
            .contentType(ContentType.JSON);
    }

    @Test
    @Order(13)
    void countCategories() {
        given()
        .when()
            .get("/category/count")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("categorias", is(1));
    }
}
