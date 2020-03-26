package cn.xyf.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("StarUser2")
public class StarUser2 {
    @Id
    private String _id;
    @Field("id")
    private int id;
    @Field("name")
    private String name;
    @Field("mobile")
    private String mobile;
    @Field("email")
    private String email;
    @Field("gender")
    private String gender;
    @Field("person_id")
    private int personId;
    @Field("utm_source")
    private String utmSource;
    @Field("created_time")
    private String createdTime;
    @Field("updated_time")
    private String updatedTime;
}
