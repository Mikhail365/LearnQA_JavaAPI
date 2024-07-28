package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void assertJsonByName(Response Response, String name, int expectedValue){
        Response.then().assertThat().body("$",hasKey(name));
        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue,value,"Json value is not equal");
    }
    public static void assertResponseNotKeys(Response Response, String [] keys){
        Response.then().assertThat().body("$",not(hasKey(keys)));
    }
    public static void assertResponseNotKeys(Response Response, String  key){
        Response.then().assertThat().body("$",not(hasKey(key)));
    }
    public static void assertResponseHasKeys(Response Response, String  key){
        Response.then().assertThat().body("$",hasKey(key));
    }
}
