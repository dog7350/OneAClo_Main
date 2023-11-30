package kr.kro.oneaclo.www.DTO.Log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDTO {
    private int no;
    private String id;
    private int age;
    private String gender;
    private String address;
    private int count;
    private String bcate;
    private String mcate;
    private String scate;
}
