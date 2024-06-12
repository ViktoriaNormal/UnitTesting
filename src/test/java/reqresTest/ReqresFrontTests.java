package reqresTest;

import data.Authentication;
import data.People;
import generalSettings.DriverStart;
import generalSettings.TestListener;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@Feature("Тестирование WEB на главной странице Reqres")
@ExtendWith(TestListener.class)
public class ReqresFrontTests extends DriverStart {

    ReqresPage reqresPage;
    Logger logger = LoggerFactory.getLogger(ReqresFrontTests.class);

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса LIST USERS")
    @Test
    public void checkWebListUsers() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("users");
        checkStatusCode("get", "users?page=2");
        checkResponse("get", "users?page=2");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса SINGLE USER")
    @Test
    public void checkWebSingleUser() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("users-single");
        checkStatusCode("get", "users/2");
        checkResponse("get", "users/2");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса SINGLE USER NOT FOUND")
    @Test
    public void checkWebSingleUserNotFound() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("users-single-not-found");
        checkStatusCode("get", "users/23");
        checkResponse("get", "users/23");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса LIST <RESOURCE>")
    @Test
    public void checkWebListResource() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("unknown");
        checkStatusCode("get", "unknown");
        checkResponse("get", "unknown");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса SINGLE <RESOURCE>")
    @Test
    public void checkWebSingleResource() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("unknown-single");
        checkStatusCode("get", "unknown/2");
        checkResponse("get", "unknown/2");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса SINGLE <RESOURCE> NOT FOUND")
    @Test
    public void checkWebSingleResourceNotFound() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("unknown-single-not-found");
        checkStatusCode("get", "unknown/23");
        checkResponse("get", "unknown/23");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса CREATE")
    @Test
    public void checkWebCreate() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        People people = People.builder()
                .name("morpheus")
                .job("leader")
                .build();

        clickRequestButton("post");
        checkStatusCode("post", "users", people);
        checkResponse("post", "users", people);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения PUT запроса UPDATE")
    @Test
    public void checkWebPutUpdate() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        clickRequestButton("put");
        checkStatusCode("put", "users/2", people);
        checkResponse("put", "users/2", people);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения PATCH запроса UPDATE")
    @Test
    public void checkWebPatchUpdate() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        People people = People.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        clickRequestButton("patch");
        checkStatusCode("patch", "users/2", people);
        checkResponse("patch", "users/2", people);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса DELETE")
    @Test
    public void checkWebDelete() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("delete");
        checkStatusCode("delete", "users/2");
        checkResponse("delete", "users/2");
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса REGISTER - SUCCESSFUL")
    @Test
    public void checkWebRegisterSuccessful() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        Authentication authentication = Authentication.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        clickRequestButton("register-successful");
        checkStatusCode("post", "register", authentication);
        checkResponse("post", "register", authentication);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса REGISTER - UNSUCCESSFUL")
    @Test
    public void checkWebRegisterUnsuccessful() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        Authentication authentication = Authentication.builder()
                .email("sydney@fife")
                .build();

        clickRequestButton("register-unsuccessful");
        checkStatusCode("post", "register", authentication);
        checkResponse("post", "register", authentication);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса LOGIN - SUCCESSFUL")
    @Test
    public void checkWebLoginSuccessful() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        Authentication authentication = Authentication.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        clickRequestButton("login-successful");
        checkStatusCode("post", "login", authentication);
        checkResponse("post", "login", authentication);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование кнопки для выполнения запроса LOGIN - UNSUCCESSFUL")
    @Test
    public void checkWebLoginUnsuccessful() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        Authentication authentication = Authentication.builder()
                .email("peter@klaven")
                .build();

        clickRequestButton("login-unsuccessful");
        checkStatusCode("post", "login", authentication);
        checkResponse("post", "login", authentication);
    }

    @Owner("Почтова Виктория")
    @DisplayName(value="Тестирование запроса DELAYED RESPONSE")
    @Test
    public void checkWebDelayedResponse() {
        reqresPage = new ReqresPage(driver);
        driver.get(reqresPage.reqresURL);

        clickRequestButton("delay");
        checkStatusCode("get", "users?delay=3");
        checkResponse("get", "users?delay=3");
        checkDelayResponse();
    }

    @Step("Нажать кнопку для выполнения запроса.")
    public void clickRequestButton(String dataId) {
        reqresPage.findRequestButtonByDataId(dataId).click();
        logger.info("Нажата кнопка для выполнения запроса.");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Проверить на совпадение коды ответов в WEB и API.")
    public void checkStatusCode(String httpMethod, String request) {
        String webResponseCode = reqresPage.responseCode.getText().strip();
        logger.info("Полученный код ответа в WEB = {}", webResponseCode);

        String apiResponseCodeString = getStatusCodeFromRequest(httpMethod, request);

        logger.info("Полученный код ответа API = {}", apiResponseCodeString);

        Assertions.assertEquals(webResponseCode, apiResponseCodeString);
        logger.info("Проверка на совпадение кодов ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить на совпадение коды ответов в WEB и API.")
    public void checkStatusCode(String httpMethod, String request, People people) {
        String webResponseCode = reqresPage.responseCode.getText().strip();
        logger.info("Полученный код ответа в WEB = {}", webResponseCode);

        String apiResponseCodeString = getStatusCodeFromRequest(httpMethod, request, people);
        logger.info("Полученный код ответа API = {}", apiResponseCodeString);

        Assertions.assertEquals(webResponseCode, apiResponseCodeString);
        logger.info("Проверка на совпадение кодов ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить на совпадение коды ответов в WEB и API.")
    public void checkStatusCode(String httpMethod, String request, Authentication authentication) {
        String webResponseCode = reqresPage.responseCode.getText().strip();
        logger.info("Полученный код ответа в WEB = {}", webResponseCode);

        String apiResponseCodeString = getStatusCodeFromRequest(httpMethod, request, authentication);
        logger.info("Полученный код ответа API = {}", apiResponseCodeString);

        Assertions.assertEquals(webResponseCode, apiResponseCodeString);
        logger.info("Проверка на совпадение кодов ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить на совпадение ответы в WEB и API.")
    public void checkResponse(String httpMethod, String request) {
        String webResponseBody = regexResponseFormatter(reqresPage.getFormatWebResponse());
        logger.info("Полученный ответ в WEB: {}", webResponseBody);

        String apiResponseBody = regexResponseFormatter(getResponseFromRequest(httpMethod, request));
        logger.info("Полученный ответ API: {}", apiResponseBody);

        Assertions.assertEquals(webResponseBody, apiResponseBody);
        logger.info("Проверка на совпадение ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить на совпадение ответы в WEB и API.")
    public void checkResponse(String httpMethod, String request, People people) {
        String webResponseBody = regexResponseFormatter(reqresPage.getFormatWebResponse());
        logger.info("Полученный ответ в WEB: {}", webResponseBody);

        String apiResponseBody = regexResponseFormatter(getResponseFromRequest(httpMethod, request, people));
        logger.info("Полученный ответ API: {}", apiResponseBody);

        Assertions.assertEquals(webResponseBody, apiResponseBody);
        logger.info("Проверка на совпадение ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить на совпадение ответы в WEB и API.")
    public void checkResponse(String httpMethod, String request, Authentication authentication) {
        String webResponseBody = regexResponseFormatter(reqresPage.getFormatWebResponse());
        logger.info("Полученный ответ в WEB: {}", webResponseBody);

        String apiResponseBody = regexResponseFormatter(getResponseFromRequest(httpMethod, request, authentication));
        logger.info("Полученный ответ API: {}", apiResponseBody);

        Assertions.assertEquals(webResponseBody, apiResponseBody);
        logger.info("Проверка на совпадение ответов в WEB и API прошла успешно!");
    }

    @Step("Проверить время ожидания ответа")
    public void checkDelayResponse() {
        Assertions.assertEquals(normalDelayForApiResponse(), reqresPage.normalDelayForWebResponse());
        logger.info("Время задержки ответа в WEB равно времени задержки ответа API. Проверка прошла успешно!");
    }

    public String getStatusCodeFromRequest(String httpMethod, String request) {
        RequestSpecification requestSpecification = given()
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        int apiResponseCode = sendRequest
                .then()
                .extract()
                .statusCode();

        String apiResponseCodeString = String.valueOf(apiResponseCode);

        return apiResponseCodeString;
    }

    public String getStatusCodeFromRequest(String httpMethod, String request, People people) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(people)
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        int apiResponseCode = sendRequest
                .then()
                .extract()
                .statusCode();

        String apiResponseCodeString = String.valueOf(apiResponseCode);

        return apiResponseCodeString;
    }

    public String getStatusCodeFromRequest(String httpMethod, String request, Authentication authentication) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(authentication)
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        int apiResponseCode = sendRequest
                .then()
                .extract()
                .statusCode();

        String apiResponseCodeString = String.valueOf(apiResponseCode);

        return apiResponseCodeString;
    }

    public String getResponseFromRequest(String httpMethod, String request) {
        RequestSpecification requestSpecification = given()
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        String apiResponseBody = sendRequest
                .then()
                .extract()
                .asPrettyString();

        return apiResponseBody;
    }

    public String getResponseFromRequest(String httpMethod, String request, People people) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(people)
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        String apiResponseBody = sendRequest
                .then()
                .extract()
                .asPrettyString();

        return apiResponseBody;
    }

    public String getResponseFromRequest(String httpMethod, String request, Authentication authentication) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(authentication)
                .when();

        var sendRequest = switch (httpMethod) {
            case "delete" -> requestSpecification.delete(BaseTest.BASE_URL + "/" + request);
            case "get" -> requestSpecification.get(BaseTest.BASE_URL + "/" + request);
            case "put" -> requestSpecification.put(BaseTest.BASE_URL + "/" + request);
            case "patch" -> requestSpecification.patch(BaseTest.BASE_URL + "/" + request);
            case "post" -> requestSpecification.post(BaseTest.BASE_URL + "/" + request);
            default -> throw new IllegalStateException("Некорректный http метод: " + httpMethod);
        };

        String apiResponseBody = sendRequest
                .then()
                .extract()
                .asPrettyString();

        return apiResponseBody;
    }

    public boolean normalDelayForApiResponse() {
        given()
                .when()
                .get(BaseTest.BASE_URL + "/users?delay=3")
                .then()
                .statusCode(200)
                .time(greaterThan(3000L))
                .time(lessThan(4000L));

        return true;
    }

    public String regexResponseFormatter(String response) {
        return response.replaceAll("\"(id|createdAt|updatedAt)\":\\s*\"[^\"]*?\\d[^\"]*\"", "\"$1\": \"\"");
    }
}


