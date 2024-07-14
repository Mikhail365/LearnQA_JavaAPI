import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ExSeven {
        int count = 0;
        int statusResponse = 0;
        String RequestPath = "https://playground.learnqa.ru/api/long_redirect";
        @Test
        public  void test11() {


            while (statusResponse!=200) {
                Response response = RestAssured
                        .given()
                        .redirects()
                        .follow(false)
                        .when()
                        .get(RequestPath)
                        .andReturn();
                statusResponse = response.getStatusCode();
                count++;
                RequestPath = response.getHeader("Location");
                System.out.println(statusResponse);
            }
            System.out.println(statusResponse);
            System.out.println(count);

        }
    }


