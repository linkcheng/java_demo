import com.google.gson.Gson;
import org.apache.avro.util.Utf8;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.regex.Pattern;

public class GsonTest {
    public static Logger logger = Logger.getLogger(GsonTest.class);

    //json字符串-简单对象型
//    private static final String JSON_OBJ_STR = "[2019-12-21 12:00:29,744][INFO] \"GET /v1/open/product_list?user_key=****&user_phone=****&screen_height=1125&screen_width=2436&from_page=1&user_type=2&os_type=android HTTP/1.0\" 200 1321";
    private static final String JSON_OBJ_STR = "10.10.13.41 - - [13/Aug/2019:03:46:54 +0800] \"GET /v1/open/product_list?user_key=****&user_phone=****&screen_height=1125&screen_width=2436&from_page=1&user_type=2&os_type=ios HTTP/1.0\" 200 1321";

    @Test
    public void testJSONStrToJSONObject() {
        Gson gson = new Gson();
//        LogLine line = gson.fromJson(JSON_OBJ_STR, LogLine.class);
        String line = gson.toJson(JSON_OBJ_STR);
        logger.info(line);
        Utf8 x = new Utf8("c");
        logger.info("c".equals(x.toString()));
    }

    @Test
    public void testPattern() {
        String value = "hello world 123";
        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        String[] res = pattern.split(value);

        for(String str: res) {
            logger.info(str);
        }
    }
}

class LogLine {
    public String payload;
    public Object schema;

    public String getPayload() {
        return payload;
    }
}