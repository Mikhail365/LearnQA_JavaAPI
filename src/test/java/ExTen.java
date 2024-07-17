import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExTen {

String someText= "216271";

    @Test
    public void expectedString(){
        assertTrue(someText.length()>15,"Text less than 15 characters");

    }
}
