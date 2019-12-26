import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.junit.Test;


public class FJsonTest {
    public static Logger logger = Logger.getLogger(FJsonTest.class);
    //json字符串-简单对象型
    private static final String JSON_OBJ_STR = "{\"studentName\":\"lily\",\"studentAge\":12}";

    //json字符串-数组类型
    private static final String JSON_ARRAY_STR = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";

    //复杂格式json字符串
    private static final String COMPLEX_JSON_STR = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";


    @Test
    public void testJSONStrToJSONObject() {

        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);

        logger.info("1 studentName:  " + jsonObject.getString("studentName") + ":" + "  studentAge:  "
                + jsonObject.getInteger("studentAge"));

    }

    @Test
    public void testJSONNObjectToJSONStr() {
        JSONObject jsonObject = JSONObject.parseObject(JSON_OBJ_STR);
        String jsonString = jsonObject.toJSONString();
        logger.info(jsonString);
    }

    @Test
    public void testJsonStrToJsonArray() {
        JSONArray jsonArray = JSONArray.parseArray(JSON_ARRAY_STR);
        int size = jsonArray.size();
        for(int i=0; i<size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            logger.info("2 studentName: " + jsonObject.getString("studentName") + ", studentAge: " + jsonObject.getString("studentAge"));
        }

        for(Object obj: jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            logger.info("3 studentName: " + jsonObject.getString("studentName") + ", studentAge: " + jsonObject.getString("studentAge"));
        }
    }

    @Test
    public void testComplexJSONStrToJSONObject() {
        JSONObject jsonObject = JSONObject.parseObject(COMPLEX_JSON_STR);
        String teacherName = jsonObject.getString("teacherName");
        Integer teacherAge = jsonObject.getInteger("teacherAge");
        logger.info("teacherName:  " + teacherName + "   teacherAge:  " + teacherAge);

        JSONObject jsonObjectCourse = jsonObject.getJSONObject("course");
        String courseName = jsonObjectCourse.getString("courseName");
        Integer code = jsonObjectCourse.getInteger("code");
        logger.info("courseName:  " + courseName + "   code:  " + code);

        JSONArray jsonArraystudents = jsonObject.getJSONArray("students");
        for(Object obj: jsonArraystudents) {
            JSONObject jsonObject1 = (JSONObject)obj;
            String studentName = jsonObject1.getString("studentName");
            Integer studentAge = jsonObject1.getInteger("studentAge");
            logger.info("4 studentName: " + studentName + ", studentAge: " + studentAge);

        }
    }

    @Test
    public void testJavaBeanObjToJSONStr() {
        Student student = new Student("lily", 12);
        String jsonString = JSONObject.toJSONString(student);
        logger.info(jsonString);
    }

    @Test
    public void testJSONStrToJavaBeanObj() {
        Student student = JSONObject.parseObject(JSON_OBJ_STR, Student.class);
        logger.info("5 studentName: " + student.studentName + ", studentAge: " + student.studentAge);

        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(student);
        logger.info("5 jsonObject: " + jsonObject.toJSONString());
    }
}


//@Data
class Student {
    public String studentName;
    public Integer studentAge;

    Student() {}

    Student(String name, Integer age) {
        studentName = name;
        studentAge = age;
    }
}

