package tests.demoWebShopTests;

import api.Auth;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AddAddressTests extends TestBase {

    @Test
    void successfulAddAddressTest() {
        String login = "9757975@gmail.com",
                password = "Qwerty123";

        new Auth().loginWithCookies(login, password);

        $(".account").shouldHave(text("9757975@gmail.com"));

        $(".my-account").$(byText("Addresses")).click();

        int addressCount = $$(".address-item").size();

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookies(new Auth().getCookies(login, password))
                .body("Address.Id=0&Address.FirstName=Yuliya&Address.LastName=Koronkevich&Address.Email=9757975%40gmail.com&Address.Company=&Address.CountryId=1&Address.StateProvinceId=3&Address.City=Tambov&Address.Address1=Great+Street&Address.Address2=&Address.ZipPostalCode=125363&Address.PhoneNumber=9099999099&Address.FaxNumber=")
                .when()
                .post("/customer/addressadd")
                .then()
                .statusCode(302)
                .log().body();

        refresh();
        $$(".address-item").shouldHave(size(addressCount + 1));
    }
}
