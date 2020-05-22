package silkrode.ai.recommend;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Data
@Document(indexName = "rating")
public class Rating {
    @Field(type = FieldType.Long)
    Long userId;
    @Field(type = FieldType.Long)
    Long movieId;
    @Field(type = FieldType.Float)
    Float rating;
    @Field(type = FieldType.Text)
    String timestamp;
    @Id
    String id;
}
