package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegisterTest {
     @Test
     public void createdUserWithErrorEmail(){
          Map<String,String> data = new HashMap<>();
          data.put("username","test");
          data.put("firstName","Mikhail");
          data.put("lastName","Test");
          data.put("email","mikhailexample.com");
          data.put("password","12345");
          Response response = RestAssured
                  .given()
                  .body(data)
                  .post("https://playground.learnqa.ru/api/user/")
                  .andReturn();

          assertEquals("Invalid email format",response.asString(),"response not Equals");
     }
}
