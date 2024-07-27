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
import static lib.DataGenerator.generateDatafromCreatedUser;
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
     @Test
     public void cutName(){
          Map<String,String> cutName = new HashMap<>();
          cutName.put("username","t");
          Response response = RestAssured
                  .given()
                  .body(generateDatafromCreatedUser(cutName))
                  .post("https://playground.learnqa.ru/api/user/")
                  .andReturn();
          assertEquals("The value of 'username' field is too short",response.asString(),"Error not Equals");
     }

     @Test
     public void veryLongName(){
          Map<String,String> longName = new HashMap<>();
          longName.put("username","Равным образом консультация с широким активом представляет собой интересный эксперимент"+
                          "проверки позиций, занимаемых участниками в отношении поставленных задач. Не следует, однако забывать,"+
                          "что дальнейшее развитие различных форм деятельности влечет за вывы");
          Response response = RestAssured
                  .given()
                  .body(generateDatafromCreatedUser(longName))
                  .post("https://playground.learnqa.ru/api/user/")
                  .andReturn();
          assertEquals("The value of 'username' field is too long",response.asString(),"Error not Equals");
     }
}
