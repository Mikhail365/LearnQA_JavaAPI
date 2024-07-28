package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
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
    ApiCoreRequests requests = new ApiCoreRequests();
    @Description("Deleted user with id = 2")
    @DisplayName("Negative test deleting the course author account")
    @Test
    public void deletedKotovAuth(){

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = requests
                .makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        int userId = getIntFromJson(responseGetAuth, "user_id");

        //Deleted Kotov User

        Response editResponse = requests
                .makeDeleteRequestForUser(
                        (getHeader(responseGetAuth,"x-csrf-token")),
                        (getCookie(responseGetAuth,"auth_sid")),
                "https://playground.learnqa.ru/api/user/",userId);


        Assertions.assertJsonByName(editResponse,"error","Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

    }
    @Description("Deleted generated user")
    @DisplayName("Positive test for deleting generated user")
    @Test
    public void deletedGenerateUser(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        JsonPath responseCreatedUser = requests
                .makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/")
                .jsonPath();

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = requests
                .makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        int userId = getIntFromJson(responseGetAuth, "user_id");

        //Deleted GENERATE User

        Response editResponse = requests
                .makeDeleteRequestForUser(
                        (getHeader(responseGetAuth,"x-csrf-token")),
                        (getCookie(responseGetAuth,"auth_sid")),
                        "https://playground.learnqa.ru/api/user/",
                        userId
                                );

        Assertions.assertJsonByName(editResponse,"success","!");

        //Check Deleted User

        Response checkUser = requests.makeGetUserRequestAuthoricationOrGetDataUser("https://playground.learnqa.ru/api/user/" + userId);
        assertEquals("User not found",checkUser.asString(),"Response not Equals");

    }
    @Description("Deleted other user w/o his auth")
    @DisplayName("Negative test for deleting other user")
    @Test
    public void deletedOtherUser(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        Response responseCreatedUser = requests
                .makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/");
        Assertions.assertResponseCode(responseCreatedUser,200);

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = requests
                .makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        int userId = 101910;

        //Deleted Other User

        Response editResponse = requests
                .makeDeleteRequestForUser(
                        (getHeader(responseGetAuth,"x-csrf-token")),
                        (getCookie(responseGetAuth,"auth_sid")),
                        "https://playground.learnqa.ru/api/user/",
                        userId
                );

        Assertions.assertJsonByName(editResponse,"error","This user can only delete their own account.");

        //Check Deleted User

        Response checkUser = requests
                .makeGetUserRequestAuthoricationOrGetDataUser("https://playground.learnqa.ru/api/user/" + userId);

        Assertions.assertResponseHasKeys(checkUser,"username");
    }
}
