package kr.kro.oneaclo.www.Entity.QnA;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "oacchatlogs")
@Data
public class ChatLog {
    @Id
    private String id;
    private String site;
    private String img;
    private String writer;
    private String nick;
    private String ext;
    private String content;
    private Date createdAt;
}
