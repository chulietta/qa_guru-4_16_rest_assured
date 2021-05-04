package api;

import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class Auth {
    public Map<String, String> getCookies(String login, String password) {
        return
                given()
                        .contentType("application/x-www-form-urlencoded")
                        //.contentType(ContentType.URLENC)
                        .formParam("Email", login)
                        .formParam("Password", password)
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(302)
                        .log().body()
                        .extract().cookies();
    }

    public void loginWithCookies(String login, String password) {
        Map<String, String> cookies = this.getCookies(login, password);
        open("http://demowebshop.tricentis.com/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookies.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookies.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookies.get("ARRAffinity")));

        open("");
    }

}
