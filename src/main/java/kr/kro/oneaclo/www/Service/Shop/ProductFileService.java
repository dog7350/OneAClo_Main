package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.Entity.Shop.ProductFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductFileService {
    void ProductFileUpload(String pName, List<MultipartFile> files);

    List<ProductFile> ProductDetail(int pno);
}
