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

@Epic("User edit cases")
public class UserEditTest extends BaseTestCase {
    ApiCoreRequests requests = new ApiCoreRequests();
    @Test
    @Description("Edit user w/o Auth")
    @DisplayName("Negative test for edit user")
    public void editUserWithoutAuth(){


        String userId = "101910";
        String newName = "newName";
        Map<String,String> name = new HashMap<>();
        name.put("firstName",newName);

        //EditUserWithoutAuth
        Map<String,String> newData = generateDatafromCreatedUser(name);
        Response editResponse = requests.makeEditUserRequest("https://playground.learnqa.ru/api/user/",userId,newData);


        Assertions.assertResponseCode(editResponse,400);
        Assertions.assertJsonByName(editResponse,"error","Auth token not supplied");
    }
    @Test
    @Description("Edit user by other User")
    @DisplayName("Negative test for edit user by other User")
    public void editUserWithAuthOtherUser(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        Response responseCreatedUser = requests.makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/");
        Assertions.assertResponseCode(responseCreatedUser,200);

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = requests.makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        //Edit User With Auth Other User
        String userId = "101910";
        String newName = "newName";
        Map<String,String> name = new HashMap<>();
        name.put("firstName",newName);

        Response editResponse = requests.makeEditUserRequest(responseGetAuth,"https://playground.learnqa.ru/api/user/",userId,name);
        Assertions.assertJsonByName(editResponse,"error","This user can only edit their own data.");

    }
    @Test
    @Description("Edit email from the user on the bad email")
    @DisplayName("Negative test for edit email")
    public void editUserErrorEmail(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        JsonPath responseCreatedUser = (JsonPath) requests
                .makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreatedUser.getString("id");

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = requests.makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");


        //Edit User With Auth Other User
        String newEmail = "errorexapmle.com";
        Map<String,String> error = new HashMap<>();
        error.put("email",newEmail);

        Response editResponse = requests.makeEditUserRequest(responseGetAuth,"https://playground.learnqa.ru/api/user/",userId,error);
        Assertions.assertJsonByName(editResponse,"error","Invalid email format");

    }
    @Test
    @Description("Edit firstName from the user on the cut value")
    @DisplayName("Negative test for edit firstName")
    public void editUserWithCutName(){
        //GENERATE USER
        Map<String,String> data = generateDatafromCreatedUser();
        JsonPath responseCreatedUser = (JsonPath) requests
                .makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/")
                .jsonPath();
        String userId = responseCreatedUser.getString("id");

        //Login User
        Map<String,String> authData = new HashMap<>();
        authData.put("email", data.get("email"));
        authData.put("password", data.get("password"));

        Response responseGetAuth = requests.makeAuthUserRequest(authData,"https://playground.learnqa.ru/api/user/login");

        //Edit User With bad firstName
        String newName = "T";
        Map<String,String> cutName = new HashMap<>();
        cutName.put("firstName",newName);

        Response editResponse = requests.makeEditUserRequest(responseGetAuth,"https://playground.learnqa.ru/api/user/",userId,cutName);
        Assertions.assertJsonByName(editResponse,"error","The value for field `firstName` is too short");

    }
}
