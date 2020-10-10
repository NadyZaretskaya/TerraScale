import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class ApiTest extends BaseTest {
    String item = "{\"name\":\"Summer dress\",\"section\":\"Платья\",\"description\":\"Colorful dress\"}";
    int itemId;

    @Test (description = "Create new item")
    public void testCreate() {
        given()
               .log().all()
               .contentType(ContentType.JSON)
               .body(item).
        when()
               .post("http://shop.bugred.ru/api/items/create").
        then()
                .log().all()
                .statusCode(SC_OK)
                .assertThat()
                .body("result.name", is("Summer dress"))
                .body("result.section", is("Платья"))
                .body("result.description", is("Colorful dress"));
    }

    @Test (description = "Update the item & check")
    public void testUpdate() {
        itemId = createItem();
        String updateItem = "{\"id\" :"+ itemId +",\"name\" : \"Summer Dress\",\"section\" : \"Платья\",\"description\" : \"Colorful summer dress with flowers\"}";
        String newItem = "{\"id\" :" + itemId+"}";

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(updateItem).
        when()
                .post("http://shop.bugred.ru/api/items/update").
        then()
                .log().all()
                .statusCode(SC_OK)
                .body("method", is("/items/update"))
                .body("status", is("ok"))
                .body("result", is("Товар обновлен!"));
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newItem).
                when()
                .post("http://shop.bugred.ru/api/items/get").
                then()
                .log().all()
                .statusCode(SC_OK)
                .body("result.description", is("Colorful summer dress with flowers"));
    }

    @Test (description = "Get the item")
    public void testGet() {
        itemId = createItem();
        String newItem = "{\"id\" :" + itemId+"}";

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newItem).
        when()
                .post("http://shop.bugred.ru/api/items/get").
        then()
                .log().all()
                .statusCode(SC_OK)
                .body("method", is("/items/update"))
                .body("status", is("ok"))
                .body("result.id", hasToString(String.valueOf(itemId)))
                .body("result.name", is("Summer dress"));

    }
}
