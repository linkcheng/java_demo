package cn.xyf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {

    public static String jsonify(Object object) {
        return jsonify(object, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * json 日期对象
     */
    public static String jsonify(Object object, String dateFormat) {
        String res = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        try {
            res = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
