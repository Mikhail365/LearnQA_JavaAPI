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
        data.put("email", getRandomEmail());
        data.put("password", "12345");
        return data;
    }
    public static Map<String, String> generateDatafromCreatedUser(Map<String,String> nonDefaultParams) {
        Map<String, String> defaultsDate = generateDatafromCreatedUser();
        String [] keys = {"username", "firstName", "lastName", "email", "password"};
        Map<String, String> newData = new HashMap<>();
        for (String key : keys){
            if (nonDefaultParams.containsKey(key)){
                newData.put(key,nonDefaultParams.get(key));
            }
            else {
                newData.put(key, defaultsDate.get(key));
            }
        }

        return newData;
    }

    public static Map<String, String> deletedParamInBody(String key) {
        Map<String, String> defaultsDate = generateDatafromCreatedUser();
        if (defaultsDate.containsKey(key)) {
            defaultsDate.remove(key);
        }
        return defaultsDate;
    }
}




