package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests extends BaseTestCase{

    @Step("Make a post request for create user")
    public  Response makeCreatedUserRequest(Map<String,String> data, String url){
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post(url)
                .andReturn();
    }
    @Step("Make a post request for auth")
    public  Response makeAuthUserRequest(Map<String,String> data, String url){
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post(url)
                .andReturn();
    }
    @Step("Make a put request for edit User")
    public  Response makeEditUserRequest(Response Response, String url, String userId, Map<String,String> data){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", getCookie(Response,"auth_sid"))
                .header("x-csrf-token",getHeader(Response,"x-csrf-token"))
                .body(data)
                .put(url+userId)
                .andReturn();
    }
    @Step("Make a put request for edit User w/o Auth")
    public  Response makeEditUserRequest(String url, String userId, Map<String,String> data){
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .put(url+userId)
                .andReturn();
    }
    @Step("Make a get request for Authorication or get user data with cookie and header")
    public  Response makeGetUserRequestAuthoricationOrGetDataUser(String header, String cookie, String url){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token",header)
                .cookie("auth_sid",cookie)
                .get(url)
                .andReturn();
    }
    @Step("Make a get request for Authorication or get user data with cookie and header")
    public  Response makeGetUserRequestAuthoricationOrGetDataUser(String url){
        return given()
                .filter(new AllureRestAssured())
                .get(url)
                .andReturn();
    }
    @Step("Make a delete request for delete User")
    public  Response makeDeleteRequestForUser(String header, String cookie, String url, int userId){
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token",header)
                .cookie("auth_sid",cookie)
                .delete(url + userId)
                .andReturn();
    }
}
