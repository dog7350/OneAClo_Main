package kr.kro.oneaclo.www.DTO.Shop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class ProductDTO {
    // PRODUCT
    private int pno;
    private String bcategory;
    private String mcategory;
    private String scategory;
    private String img;
    private String pname;
    private int price;
    private String content;
    private Timestamp time;
    private int inquiry;
}
