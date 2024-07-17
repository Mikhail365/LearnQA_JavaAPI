import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExTwelve {
    @Test
    public void responseHeaders1(){
        List <String> trueHeaders = new ArrayList<>();
        trueHeaders.add("x-secret-homework-header=Some secret value");
        System.out.println(trueHeaders);
        Response responseResult = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        List<Header> homeHeaders = responseResult.getHeaders().getList("x-secret-homework-header");
        assertEquals(trueHeaders.get(0).toString(),homeHeaders.get(0).toString(),"object is not same");


       /* assertEquals(trueCookie,authCookie,"Cookie is not true");*/
    }
}
