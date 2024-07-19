package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExNine {

    @Test
    public void get_secret_password_homework() {
        Map<String,String> authCookie = new HashMap<>();
        authCookie.put("auth_cookie","tets");
        String truePass = null;
        String something = "test";


        try {
        BufferedReader reader = new BufferedReader(new FileReader("src/test.txt"));
        while (something != null) {


                something = reader.readLine();
                while (!check_auth_cookie(authCookie)) {
                    System.out.println("Пароль переменная:"+something);


                    Response responseResult = RestAssured
                            .given()
                            .body("{\"login\":\"super_admin\",\"password\":\""+something+"\"}")
                            .when()
                            .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework");
                    authCookie = responseResult.getCookies();
                    System.out.println("куки:"+authCookie);

                    truePass = something;
                    something = reader.readLine();



                }
                    System.out.println("Правильный пароль: " + truePass);
                    something = null;


            }





            }
                catch (IOException e) {
                    e.printStackTrace();
        }
    }


    public boolean check_auth_cookie(Map<String,String> authCookie) {
        Response responseAuthKey = RestAssured
                .given()
                .cookies(authCookie)
                .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                .andReturn();
        String body = responseAuthKey.getBody().htmlPath().get("body").toString();


        if (body.equals("You are authorized")) {
            return true;
        }

        return false;
    }
   /* @Test
    public void test1(){
        Map<String,String> authCookie = new HashMap<>();

        authCookie.put("auth_cookie","532482");
        check_auth_cookie(authCookie);
    }
*/
}
