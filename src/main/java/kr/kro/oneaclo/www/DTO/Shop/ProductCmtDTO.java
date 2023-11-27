package kr.kro.oneaclo.www.DTO.Shop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class ProductCmtDTO {
    // PRODUCT
    private int pno;
    private String bcategory;
    private String mcategory;
    private String scategory;
    private String img;
    private String pname;
    private String price;
    private String content;
    private Timestamp time;
    private int inquiry;

    // PRODUCT CMT
    private int cno;
    private String writer;
    private String ccontent;
    private Timestamp ctime;
    private String secret;
    private int cnogroup;
    private int step;
    private int indent;
}
