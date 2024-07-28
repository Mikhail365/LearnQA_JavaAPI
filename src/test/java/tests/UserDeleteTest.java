package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static lib.DataGenerator.generateDatafromCreatedUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Deleted user")
public class UserDeleteTest extends BaseTestCase {
    @Description("Deleted user with id = 2")
    @DisplayName("Negative test deleting the course author account")
    @Test
    public void deletedKotovAuth(){

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        int userId = getIntFromJson(responseGetAuth, "user_id");

        //Deleted Kotov User

        Response editResponse = RestAssured
                .given()
                .cookie("auth_sid", getCookie(responseGetAuth,"auth_sid"))
                .header("x-csrf-token",getHeader(responseGetAuth,"x-csrf-token"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        Assertions.assertJsonByName(editResponse,"error","Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

    }
    @Description("Deleted generated user")
    @DisplayName("Positive test for deleting generated user")
    @Test
    public void deletedGenerateUser(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        JsonPath responseCreatedUser = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        int userId = getIntFromJson(responseGetAuth, "user_id");

        //Deleted GENERATE User

        Response editResponse = RestAssured
                .given()
                .cookie("auth_sid", getCookie(responseGetAuth,"auth_sid"))
                .header("x-csrf-token",getHeader(responseGetAuth,"x-csrf-token"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        Assertions.assertJsonByName(editResponse,"success","!");

        //Check Deleted User

        Response checkUser = RestAssured
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        assertEquals("User not found",checkUser.asString(),"Response not Equals");

    }
    @Description("Deleted other user w/o his auth")
    @DisplayName("Negative test for deleting other user")
    @Test
    public void deletedOtherUser(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        Response responseCreatedUser = RestAssured
                .given()
                .body(data)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCode(responseCreatedUser,200);

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        int userId = 101910;

        //Deleted Other User

        Response editResponse = RestAssured
                .given()
                .cookie("auth_sid", getCookie(responseGetAuth,"auth_sid"))
                .header("x-csrf-token",getHeader(responseGetAuth,"x-csrf-token"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        Assertions.assertJsonByName(editResponse,"error","This user can only delete their own account.");

        //Check Deleted User

        Response checkUser = RestAssured
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();
        Assertions.assertResponseHasKeys(checkUser,"username");
    }
}
