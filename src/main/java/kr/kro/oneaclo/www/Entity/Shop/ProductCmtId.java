package kr.kro.oneaclo.www.Entity.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCmtId implements Serializable {
    private Product pno;
    private int cno;
}
