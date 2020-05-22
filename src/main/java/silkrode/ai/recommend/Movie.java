package silkrode.ai.recommend;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Date;
import java.util.List;

@Data
@Document(indexName = "spark")
public class Movie {
    @Id
    Long id;
    @Field(type = FieldType.Text)
    String title;
    @Field(type = FieldType.Text)
    List<String> actors;
    @Field(type = FieldType.Text)
    List<String> categories;
    @Field(type = FieldType.Text)
    List<String> tags;
    @Field(type = FieldType.Long)
    Long watch;
    @Field(type = FieldType.Long)
    Long like;
    @Field(type = FieldType.Text)
    String image;
    @Field(type = FieldType.Text)
    String video_url;
    @Field(type = FieldType.Date)
    Date published_time;
    @Field(type = FieldType.Long)
    List<Long> sim;
}
