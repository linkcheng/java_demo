package cn.xyf;

import cn.xyf.service.StarUserServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Log4j2
class JucDemoApplicationTests {

    //json字符串-简单对象型
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";

    //json字符串-数组类型
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";

    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";

    @Autowired
    StarUserServiceImpl starUserServiceImpl;

    @Test
    void contextLoads() {
    }

    @Test
    public void testJSONStrToJSONObject() {

        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);

        log.info("1 studentName:  " + jsonObject.getString("studentName") + ":" + "  studentAge:  "
                + jsonObject.getInteger("studentAge"));

        Integer age = parseJsonObject(jsonObject, "studentAge", Integer.class);
        String  name = parseJsonObject(jsonObject, "studentName", String.class);
        Map<String, Object> eles = new HashMap<>();
        eles.put("age", age);
        eles.put("name", name);
        List<String> eleIds = Arrays.asList("age", "name");

        updateContext(jsonObject, eleIds, null);
        log.info(jsonObject.toJSONString());

        updateContext(jsonObject, eleIds, eles);
        log.info(jsonObject.toJSONString());

        Integer age1 = ReflectionTestUtils.invokeMethod(starUserServiceImpl, "parseJsonObject", jsonObject, "studentAge", Integer.class);
        log.info(age1);
    }

    private <T> T parseJsonObject(JSONObject params, String key, Class<T> cls) {
        if (!params.containsKey(key)) {
            log.error("key " + key + "not exists");
        }
        return params.getObject(key, cls);
    }

    private JSONObject updateContext(JSONObject context, List<String> eleIds, Map<String, Object> elements) {
        if(null != elements){
            context.putAll(elements);
        } else {
            //如果接口返回空就把每个因子填到context中值为null
            eleIds.forEach(eleId -> {if(!context.containsKey(eleId)) context.put(eleId, null);});
        }
        return context;
    }

    @Test
    public void testJSONNObjectToJSONStr() {
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);
        String jsonString = jsonObject.toJSONString();
        log.info(jsonString);
    }

    @Test
    public void testJsonStrToJsonArray() {
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        int size = jsonArray.size();
        for(int i=0; i<size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            log.info("2 studentName: " + jsonObject.getString("studentName") + ", studentAge: " + jsonObject.getString("studentAge"));
        }

        for(Object obj: jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            log.info("3 studentName: " + jsonObject.getString("studentName") + ", studentAge: " + jsonObject.getString("studentAge"));
        }
    }

    @Test
    public void testComplexJSONStrToJSONObject() {
        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);
        String teacherName = jsonObject.getString("teacherName");
        Integer teacherAge = jsonObject.getInteger("teacherAge");
        log.info("teacherName:  " + teacherName + "   teacherAge:  " + teacherAge);

        JSONObject jsonObjectCourse = jsonObject.getJSONObject("course");
        String courseName = jsonObjectCourse.getString("courseName");
        Integer code = jsonObjectCourse.getInteger("code");
        log.info("courseName:  " + courseName + "   code:  " + code);

        JSONArray jsonArraystudents = jsonObject.getJSONArray("students");
        for(Object obj: jsonArraystudents) {
            JSONObject jsonObject1 = (JSONObject)obj;
            String studentName = jsonObject1.getString("studentName");
            Integer studentAge = jsonObject1.getInteger("studentAge");
            log.info("4 studentName: " + studentName + ", studentAge: " + studentAge);

        }
    }

}
