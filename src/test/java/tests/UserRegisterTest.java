package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static lib.DataGenerator.deletedParamInBody;
import static lib.DataGenerator.generateDatafromCreatedUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("User registration cases")
public class UserRegisterTest extends BaseTestCase {
     ApiCoreRequests requests = new ApiCoreRequests();
     @Test
     @Description("Created user with error email")
     @DisplayName("Negative test for created user")
     public void createdUserWithErrorEmail(){
          Map<String,String> paramEmail = new HashMap<>();
          paramEmail.put("email","mikhailexample.com");

          Response response = requests
                  .makeCreatedUserRequest(generateDatafromCreatedUser(paramEmail),"https://playground.learnqa.ru/api/user/");

          assertEquals("Invalid email format",response.asString(),"response not Equals");
     }
     @ParameterizedTest
     @Description("Created user w/o param in value source")
     @DisplayName("Negative test for created user with error body")
     @ValueSource(strings = {"username", "firstName", "lastName", "email", "password"})
     public void notAllParameters(String parametrDeleted){
          Map<String,String> data = deletedParamInBody(parametrDeleted);

          Response response = requests
                  .makeCreatedUserRequest(data,"https://playground.learnqa.ru/api/user/");

          assertEquals("The following required params are missed: "+parametrDeleted,response.asString(),"Error not Equals");



     }
     @Test
     @Description("Created user with cut name")
     @DisplayName("Negative test with cut username")
     public void cutName(){
          Map<String,String> cutName = new HashMap<>();
          cutName.put("username","t");
          Response response = requests
                  .makeCreatedUserRequest(generateDatafromCreatedUser(cutName),"https://playground.learnqa.ru/api/user/");

          assertEquals("The value of 'username' field is too short",response.asString(),"Error not Equals");
     }

     @Test
     @Description("Created user with very long name")
     @DisplayName("Negative test with very long username")
     public void veryLongName(){
          Map<String,String> longName = new HashMap<>();
          longName.put("username","Равным образом консультация с широким активом представляет собой интересный эксперимент"+
                          "проверки позиций, занимаемых участниками в отношении поставленных задач. Не следует, однако забывать,"+
                          "что дальнейшее развитие различных форм деятельности влечет за вывы");
          Response response = requests
                  .makeCreatedUserRequest(generateDatafromCreatedUser(longName),"https://playground.learnqa.ru/api/user/");

          assertEquals("The value of 'username' field is too long",response.asString(),"Error not Equals");
     }
}
