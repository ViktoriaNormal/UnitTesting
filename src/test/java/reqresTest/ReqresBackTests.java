package reqresTest;

import data.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Feature("Тестирование API Reqres")
public class ReqresBackTests extends BaseTest {

    Logger logger = LoggerFactory.getLogger(ReqresBackTests.class);

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса LIST USERS")
    @Test
    public void getListUsers() {
        UsersFromPage usersFromPage = given(requestSpecification)
                .when()
                .get("users?page=2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\UsersFromPageSchema.json"))
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Tobias"))
                .body("data.last_name", hasItem("Funke"))
                .extract()
                .as(UsersFromPage.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(usersFromPage)
                .extracting("page", "per_page", "total", "total_pages")
                .containsExactly(2, 6, 12, 2);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса SINGLE USER")
    @Test
    public void getSingleUser() {
        UserData userData = given(requestSpecification)
                .when()
                .get("users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\UserDataSchema.json"))
                .extract()
                .jsonPath().getObject("data", UserData.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(userData)
                .hasFieldOrPropertyWithValue("id", 2)
                .hasFieldOrPropertyWithValue("email", "janet.weaver@reqres.in")
                .hasFieldOrPropertyWithValue("first_name", "Janet")
                .hasFieldOrPropertyWithValue("last_name", "Weaver")
                .hasFieldOrPropertyWithValue("avatar", "https://reqres.in/img/faces/2-image.jpg");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса SINGLE USER NOT FOUND")
    @Test
    public void getSingleUserNotFound() {
        testObjectNotFound("users/23");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса LIST <RESOURCE>")
    @Test
    public void getListOfColors() {
        ColorsFromPage colorsFromPage = given(requestSpecification)
                .when()
                .get("unknown")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\ColorsFromPageSchema.json"))
                .body("data.id", not(hasItem(nullValue())))
                .body("data.name", hasItem("tigerlily"))
                .body("data.year", hasItem(2004))
                .extract()
                .as(ColorsFromPage.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(colorsFromPage)
                .extracting("page", "per_page", "total", "total_pages")
                .containsExactly(1, 6, 12, 2);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса SINGLE <RESOURCE>")
    @Test
    public void getSingleColor() {
        ColorData colorData = given(requestSpecification)
                .when()
                .get("unknown/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\ColorDataSchema.json"))
                .extract()
                .jsonPath().getObject("data", ColorData.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(colorData)
                .hasFieldOrPropertyWithValue("id", 2)
                .hasFieldOrPropertyWithValue("name", "fuchsia rose")
                .hasFieldOrPropertyWithValue("year", 2001)
                .hasFieldOrPropertyWithValue("color", "#C74375")
                .hasFieldOrPropertyWithValue("pantone_value", "17-2031");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса SINGLE <RESOURCE> NOT FOUND")
    @Test
    public void testGetSingleColorNotFound() {
        testObjectNotFound("unknown/23");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса CREATE")
    @Test
    public void createUser() {
        People people = People.builder()
                .name("morpheus")
                .job("leader")
                .build();

        PeopleCreated peopleCreated = given(requestSpecification)
                .body(people)
                .when()
                .post("users")
                .then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\UserCreatedSchema.json"))
                .extract()
                .as(PeopleCreated.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(peopleCreated)
                .hasFieldOrPropertyWithValue("name", "morpheus")
                .hasFieldOrPropertyWithValue("job", "leader");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование PUT запроса UPDATE")
    @Test
    public void updateUser() {
        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        PeopleUpdated peopleUpdated = given(requestSpecification)
                .body(people)
                .when()
                .put("users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\UserUpdatedSchema.json"))
                .extract()
                .as(PeopleUpdated.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(peopleUpdated)
                .hasFieldOrPropertyWithValue("name", "morpheus")
                .hasFieldOrPropertyWithValue("job", "zion resident");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование PATCH запроса UPDATE")
    @Test
    public void partialUpdateUser() {
        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        PeopleUpdated peopleUpdated = given(requestSpecification)
                .body(people)
                .when()
                .patch("users/2")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\UserUpdatedSchema.json"))
                .extract()
                .as(PeopleUpdated.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(peopleUpdated)
                .hasFieldOrPropertyWithValue("name", "morpheus")
                .hasFieldOrPropertyWithValue("job", "zion resident");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса DELETE")
    @Test
    public void deleteUser() {
        String body = given(requestSpecification)
                .when()
                .delete("users/2")
                .then()
                .statusCode(204)
                .extract().response().asString();

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(body).contains("");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса REGISTER - SUCCESSFUL")
    @Test
    public void registerSuccess() {
        Authentication authentication = Authentication.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        Register register = given(requestSpecification)
                .body(authentication)
                .when()
                .post("register")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\RegisterSchema.json"))
                .extract()
                .as(Register.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(register.getId()).isNotNull();
        Assertions.assertThat(register.getToken()).isNotNull();
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса REGISTER - UNSUCCESSFUL")
    @Test
    public void registerUnsuccessful() {
        authenticateObjectUnsuccessful("sydney@fife", "register");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса LOGIN - SUCCESSFUL")
    @Test
    public void loginSuccess() {
        Authentication authentication = Authentication.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        Login login = given(requestSpecification)
                .body(authentication)
                .when()
                .post("login")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\LoginSchema.json"))
                .extract()
                .as(Login.class);

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(login.getToken()).isNotNull();
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса LOGIN - UNSUCCESSFUL")
    @Test
    public void loginUnsuccessful() {
        authenticateObjectUnsuccessful("peter@klaven", "login");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса DELAYED RESPONSE")
    @Test
    public void delayedResponse() {
        given(requestSpecification)
                .when()
                .get("users?delay=3")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/UsersFromPageSchema.json"))
                .time(greaterThan(3000L))
                .time(lessThan(4000L));

        logger.info("Запрос успешно выполнен.");
    }

    public void testObjectNotFound(String request) {
        String body = given(requestSpecification)
                .when()
                .get(request)
                .then()
                .statusCode(404)
                .extract().response().asString();

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(body).contains("{}");
    }

    public void authenticateObjectUnsuccessful(String email, String request) {
        Authentication authentication = Authentication.builder()
                .email(email)
                .build();

        String error = given(requestSpecification)
                .body(authentication)
                .when()
                .post(request)
                .then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas\\ErrorSchema.json"))
                .extract()
                .response().jsonPath().get("error");

        logger.info("Запрос успешно выполнен.");

        Assertions.assertThat(error).isEqualTo("Missing password");
    }
}