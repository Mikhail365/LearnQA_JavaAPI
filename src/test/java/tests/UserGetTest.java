package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    @Test
    public void forbidenDataForOtherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        String header = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");
        ///Int userId = getIntFromJson(responseGetAuth, "user_id");

        Response getInformationUser = RestAssured
                .given()
                .cookies("auth_sid",cookie)
                .header("x-csrf-token",header)
                .get("https://playground.learnqa.ru/api/user/101910")
                .andReturn();
        getInformationUser.prettyPrint();



    }
}
