package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExThirty {

   static Stream<String[]> myparseJson() throws Exception {

      BufferedReader reader = new BufferedReader(new FileReader("src/UserData.json"));
      JsonPath jsondata = new JsonPath(reader);
      jsondata.prettyPrint();
      List<Object> expectedValuesList = jsondata.getList("JsonData");
      return expectedValuesList.stream().map(object -> {
         LinkedHashMap objectone = (LinkedHashMap) object;
         String userAgent = (String) objectone.get("User Agent");
         Object expectedValues = objectone.get("Expected values");
         Map parseExpectedValues = ((Map) expectedValues);
         String platform = (String) parseExpectedValues.get("platform");
         String browser = (String) parseExpectedValues.get("browser");
         String device = (String) parseExpectedValues.get("device");
         return new String[] {userAgent, platform, browser, device};
      });



   }



   @ParameterizedTest
   @MethodSource("myparseJson")
   public void userAgentCheck(String userAgent, String platform, String browser, String device) {
      JsonPath response = RestAssured
              .given()
              .header("User-Agent",userAgent)
              .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
              .jsonPath();
      Map<String,String> responseObjects = response.get();
      System.out.println(responseObjects.toString());
      assertEquals(platform,(responseObjects.get("platform").toString()), "platform is not Equals");
      assertEquals(browser,(responseObjects.get("browser").toString()), "browser is not Equals");
      assertEquals(device,(responseObjects.get("device").toString()), "platform is not Equals");



   }




}
