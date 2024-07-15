import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ExEight {
    String tokenNew;
    String RequestPath = "https://playground.learnqa.ru/ajax/api/longtime_job";
    @Test
    public  void test12() throws InterruptedException {


        JsonPath response = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get(RequestPath)
                            .jsonPath();
        tokenNew = response.get("token");
        Long oneSeconds = 1000L;
        int secondsInt =  (response.get("seconds")) ;
        Long secondswait = (long) secondsInt * oneSeconds;

        System.out.println(tokenNew);
        System.out.println(secondswait);

        Response responseStatus = RestAssured
                .given()
                .queryParam("token", "\""+tokenNew+"\"")
                .when()
                .get(RequestPath);
        String status =   responseStatus.jsonPath().get("status");
        if (status.equals("Job is NOT ready")){
            System.out.println("Job is NOT ready");
        };
        Thread.sleep(secondswait);
        ResponseresultforURL(tokenNew);


        }
    public void ResponseresultforURL(String tokenNew){
        Response responseResult = RestAssured
                .given()
                .queryParam("token", "\""+tokenNew+"\"")
                .when()
                .get(RequestPath);
        String status =   responseResult.jsonPath().get("status");
        if (status.equals("Job is ready")){
            System.out.println("Job is ready");
        };
        String result = responseResult.jsonPath().get("result");
        responseResult.prettyPrint();
        if (!result.isEmpty()){
            System.out.println("Строка result есть");
        };




    }
    }

