package tests.examples;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class LoginTests extends TestBase {

    @Test
    void loginWithUiTest() {
        open("/login");
        $("#Email").val("qaguru@qa.guru");
        $("#Password").val("qaguru@qa.guru1").pressEnter();
        $(".account").shouldHave(text("qaguru@qa.guru"));
    }

    @Test
    void loginWithCookieTest() {
        Map<String, String> cookiesMap =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        //.contentType(ContentType.URLENC)
                        .formParam("Email", "qaguru@qa.guru")
                        .formParam("Password", "qaguru@qa.guru1")
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(302)
                        .log().body()
                        //.body("success", is(true))
                        .extract().cookies();

        open("http://demowebshop.tricentis.com/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookiesMap.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookiesMap.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookiesMap.get("ARRAffinity")));

        open("");
        $(".account").shouldHave(text("qaguru@qa.guru"));
    }
}
