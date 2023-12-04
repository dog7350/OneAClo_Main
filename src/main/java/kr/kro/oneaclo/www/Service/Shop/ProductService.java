package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void ProductUpload(ProductDTO dto, MultipartFile img);

    void ProductDelete(int pno);

    void ProductUpdate(ProductDTO dto, MultipartFile thumbnail, List<MultipartFile> files);

    void ProductInquiryAdd(int pno);

    Page<Product> AllProductPage(int pageNumber, int elementCount);
    Page<Product> SearchProductPage(int pageNumber, int elementCount, String searchOption, String searchValue);

    Product ProductDetail(int pno);

    int ReviewSave(ProductCmtDTO dto,String id,int pno);

    void CnoSave(int cno,int pno);

    List<Product> MainPopularityList();
    List<Product> MainNewList();

    List<Product> RecomList();
}
