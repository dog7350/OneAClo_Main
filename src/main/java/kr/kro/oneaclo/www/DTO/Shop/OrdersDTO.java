package kr.kro.oneaclo.www.DTO.Shop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
public class OrdersDTO {
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

    // ORDERS
    private int ono;
    private String oid;
    private String email;
    private int ocount;
    private int totalprice;
    private Timestamp otime;
    private String ostatus;
    private String ouid;
    private String receiver;
    private String phone;
    private String zipcode;
    private String address;
    private String detailaddr;
}
