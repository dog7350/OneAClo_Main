package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {
    void ProductUpload (ProductDTO dto, MultipartFile img);

    Page<Product> AllProductPage(int pageNumber, int elementCount);
    Page<Product> SearchProductPage(int pageNumber, int elementCount, String searchOption, String searchValue);

    Product ProductDetail(int pno);
}
