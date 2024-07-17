import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Exeleven {
    @Test
    public void responseCookie(){
        Map<String,String> trueCookie = new HashMap<>();
        trueCookie.put("HomeWork","hw_value");
        Response responseResult = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie");
        Map<String,String> authCookie = responseResult.getCookies();

        assertEquals(trueCookie,authCookie,"Cookie is not true");
    }
}
