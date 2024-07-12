import com.sun.javafx.collections.MappingChange;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Exfive {
    @Test
    public void testHelloWorld(){
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        List<Object> test = response.getList("messages");
        Map <String,String> hashMap = (Map<String, String>) test.get(1);
        System.out.println(hashMap.get("message"));

    }
}
