package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "mikhail" + timestamp + "@example.com";
    }

    public static Map<String, String> generateDatafromCreatedUser() {
        Map<String, String> data = new HashMap<>();
        data.put("username", "test");
        data.put("firstName", "Mikhail");
        data.put("lastName", "Test");
        data.put("email", "mikhail@example.com");
        data.put("password", "12345");
        return data;
    }

    public static Map<String, String> deletedParamInBody(String key) {
        Map<String, String> defaultsDate = generateDatafromCreatedUser();
        if (defaultsDate.containsKey(key)) {
            defaultsDate.remove(key);
        }
        return defaultsDate;
    }
}




