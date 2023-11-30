package kr.kro.oneaclo.www.Entity.Log;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "oacinquirylogs")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InquiryLog {
    @Id
    private String id;
    private int pno;
    private String bcate;
    private String mcate;
    private String scate;
    private String userid;
    private int age;
    private String gender;
    private String address;
    private Date createdAt;
}
