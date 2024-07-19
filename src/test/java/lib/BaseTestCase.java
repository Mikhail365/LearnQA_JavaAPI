package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {
    protected String getCookie(Response Response, String keyCookie){
        Map<String,String> cookies = Response.getCookies();
        assertTrue(cookies.containsKey(keyCookie),"Response is doesn't cookie " + keyCookie);

       return cookies.get(keyCookie);

    }
    protected String getHeader(Response Response, String keyHeader){
        Headers headers = Response.getHeaders();
        assertTrue(headers.hasHeaderWithName(keyHeader),"Response doesn't header" + keyHeader);

        return headers.getValue(keyHeader);

    }
    protected int getIntFromJson(Response Response, String keyUser){
        //знак доллара означает что ключ находится в корне тела
        Response.then().assertThat().body("$",hasKey(keyUser));

        return Response.jsonPath().getInt(keyUser);

    }
}
