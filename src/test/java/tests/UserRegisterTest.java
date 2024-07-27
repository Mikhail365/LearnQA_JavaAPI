package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static lib.DataGenerator.deletedParamInBody;
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
     @ParameterizedTest
     @ValueSource(strings = {"username", "firstName", "lastName", "email", "password"})
     public void notAllParameters(String parametrDeleted){
          Map<String,String> data = deletedParamInBody(parametrDeleted);

          Response response = RestAssured
                  .given()
                  .body(data)
                  .post("https://playground.learnqa.ru/api/user/")
                  .andReturn();
          assertEquals("The following required params are missed: "+parametrDeleted,response.asString(),"Error not Equals");



     }
}
