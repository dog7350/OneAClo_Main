package kr.kro.oneaclo.www.Entity.Log;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "oacorderlogs")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrderLog {
    @Id
    private String id;
    private int pno;
    private String bcate;
    private String mcate;
    private String scate;
    private int age;
    private String gender;
    private int count;
    private Date createdAt;
}
