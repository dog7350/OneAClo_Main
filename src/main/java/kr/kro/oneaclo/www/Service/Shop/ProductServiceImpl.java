package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.Common.FileUtils;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Repository.Shop.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileUtils fileUtils;
    private final ProductRepository productRepository;

    public void ProductUpload (ProductDTO dto, MultipartFile thumbnail) {
        String imgName = fileUtils.ShopFileUpload(thumbnail);

        Product product = Product.builder()
                                 .pname(dto.getPname())
                                 .content(dto.getContent())
                                 .bcategory(dto.getBcategory())
                                 .mcategory(dto.getMcategory())
                                 .scategory(dto.getScategory())
                                 .price(dto.getPrice())
                                 .img(imgName)
                                 .build();

        productRepository.save(product);
    }

    public Page<Product> AllProductPage(int pageNumber, int elementCount) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);

        return productRepository.findAllByOrderByPnoDesc(pageable);
    }

    public Page<Product> SearchProductPage(int pageNumber, int elementCount, String searchOption, String searchValue) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);
        Page<Product> pages = null;

        String value = "%" + searchValue + "%";

        if (searchOption.equals("pname")) pages = productRepository.findByPnameLikeOrderByPnoDesc(pageable, value);
        else if (searchOption.equals("bcategory")) pages = productRepository.findByBcategoryLikeOrderByPnoDesc(pageable, value);
        else if (searchOption.equals("mcategory")) pages = productRepository.findByMcategoryLikeOrderByPnoDesc(pageable, value);
        else if (searchOption.equals("scategory")) pages = productRepository.findByScategoryLikeOrderByPnoDesc(pageable, value);

        return pages;
    }

    public Product ProductDetail(int pno) {
        return productRepository.findByPno(pno).get();
    }
}
