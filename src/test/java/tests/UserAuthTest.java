package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
@Epic("Authorication cases")
@Feature("Authoracation")
public class UserAuthTest extends BaseTestCase {
    ApiCoreRequests requests = new ApiCoreRequests();
    String cookie;
    String header;
    int userIdOnAuth;

@BeforeEach
    public void loginUser(){
    Map<String,String> authData = new HashMap<>();
    authData.put("email", "vinkotov@example.com");
    authData.put("password", "1234");

    Response responseGetAuth = requests
            .makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");
    this.cookie = this.getCookie(responseGetAuth, "auth_sid");
    this.header = this.getHeader(responseGetAuth, "x-csrf-token");
    this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");
}
@Test
@Description("Authorication with Kotov data")
@DisplayName("Positive authorication")
    public void testAuthUser(){
    Response responseCheckAuth = requests
            .makeGetUserRequestAuthoricationOrGetDataUser(this.header,this.cookie,"https://playground.learnqa.ru/api/user/auth");

    Assertions.assertJsonByName(responseCheckAuth,"user_id",this.userIdOnAuth);
}
@ParameterizedTest
@Description("Authorication w/o cookie or headers")
@DisplayName("Negative test for Authorication")
@ValueSource(strings = {"cookie","headers"})
    public void testNegativeAuthUser(String condition){
    RequestSpecification spec = RestAssured
            .given();
    spec.baseUri("https://playground.learnqa.ru/api/user/auth");

    if (condition.equals("cookie")) {
        spec.cookie("auth_sid", this.cookie);
    } else if (condition.equals("headers")){
        spec.header("x-csrf-token",this.header);
    } else{
        throw new IllegalArgumentException("Value is not true" + condition);
    }
    Response responseForCheck = spec.get().andReturn();
    Assertions.assertJsonByName(responseForCheck,"user_id", 0);

}

}
