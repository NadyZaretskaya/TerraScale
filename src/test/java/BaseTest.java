import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseTest {
    String item = "{\"name\":\"Summer dress\",\"section\":\"Платья\",\"description\":\"Colorful dress\"}";

    public int createItem() {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(item).
                        when()
                .post("http://shop.bugred.ru/api/items/create").
                        then()
                .log().all()
                .extract()
                .response();
        int itemId = response.jsonPath().getInt("result.id");
        return itemId;
    }

}
