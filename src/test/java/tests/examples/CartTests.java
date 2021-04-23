package tests.examples;

import api.Auth;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class CartTests extends TestBase {

    @Test
    void successAddToCartTest() {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookie("Nop.customer=e8e77ff6-3136-4f3e-9b53-d8ba210f7741; ARRAffinity=06e3c6706bb7098b5c9133287f2a8d510a64170f97e4ff5fa919999d67a34a46; __utma=78382081.487208505.1619107849.1619107849.1619107849.1; __utmc=78382081; __utmz=78382081.1619107849.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=16; __atuvc=1%7C16; __atuvs=6081a15d7b12d84f000; __utmb=78382081.4.10.1619107849")
                        .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15&product_attribute_16_3_6=18&product_attribute_16_4_7=44&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=1")
                        .when()
                        .post("/addproducttocart/details/16/1")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .body("success", is(true))
                        .extract().response();

        System.out.println(response);
    }

    @Test
    void successAddToCartWithCooliesTest() {
        Map<String, String> cookies = new Auth().login("qaguru@qa.guru", "qaguru@qa.guru1");
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookies(cookies)
                        .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15&product_attribute_16_3_6=18&product_attribute_16_4_7=44&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=1")
                        .when()
                        .post("/addproducttocart/details/16/1")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .body("success", is(true))
                        .extract().response();

        System.out.println(response);
    }
}
