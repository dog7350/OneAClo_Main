package kr.kro.oneaclo.www.DTO.Shop;

import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class ProductCmtDTO {

    private Product pno;
    private int cno;
    private Members writer;
    private String ccontent;
    private Timestamp ctime;
    private String secret;
    private int cnogroup;
    private int step;
    private int indent;
}
