package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Get data user")
public class UserGetTest extends BaseTestCase {
    ApiCoreRequests requests = new ApiCoreRequests();

    @Description("Get data about other user")
    @DisplayName("Positive test for get data other user")
    @Test
    public void forbidenDataForOtherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = requests
                .makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        String header = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");
        ///Int userId = getIntFromJson(responseGetAuth, "user_id");

        Response getInformationUser = requests
                .makeGetUserRequestAuthoricationOrGetDataUser(header,cookie,"https://playground.learnqa.ru/api/user/101910");

        String [] notKeys = {"firstName","lastName","email","password"};
        ///getInformationUser.prettyPrint();
        Assertions.assertResponseNotKeys(getInformationUser,notKeys);
        Assertions.assertResponseHasKeys(getInformationUser,"username");



    }
}
