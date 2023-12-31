package kr.kro.oneaclo.www.Service.Shop;

import jakarta.transaction.Transactional;
import kr.kro.oneaclo.www.Common.FileUtils;
import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmt;
import kr.kro.oneaclo.www.Entity.Shop.ProductCmtId;
import kr.kro.oneaclo.www.Entity.Shop.ProductFile;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductCmtRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductFileRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileUtils fileUtils;
    private final ProductRepository productRepository;
    private final ProductFileRepository productFileRepository;
    private final MembersRepository membersRepository;
    private final ProductCmtRepository productCmtRepository;

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
        else if (searchOption.equals("scategory")) {
            if (searchValue.equals("남자") || searchValue.equals("남성") || searchValue.equals("남")) value = "male";
            else if (searchValue.equals("여자") || searchValue.equals("여성") || searchValue.equals("여")) value = "female";

            pages = productRepository.findByScategoryLikeOrderByPnoDesc(pageable, value);
        }

        return pages;
    }

    public Product ProductDetail(int pno) {
        return productRepository.findByPno(pno).get();
    }

    public int ReviewSave(ProductCmtDTO dto,String id,int pno) {
        Optional<Product> ProductResult = productRepository.findByPno(pno);
        Product product = ProductResult.orElseThrow();
        Optional<Members> MemberResult = membersRepository.findById(id);
        Members members = MemberResult.orElseThrow();
        ProductCmt productCmt = ProductCmt.builder()
                .pno(product)
                .writer(members)
                .ccontent(dto.getCcontent())
                .step(0)
                .indent(0)
                .ctime(null)
                .secret(dto.getSecret())
                .build();

        productCmtRepository.save(productCmt);
        return productCmt.getCno();

    }

    @Override
    public void CnoSave(int cno,int pno) {
        Optional<Product> ProductResult = productRepository.findByPno(pno);
        Product product = ProductResult.orElseThrow();
        Optional<ProductCmt> CmtResult = productCmtRepository.findById(new ProductCmtId(product,cno));
        ProductCmt productCmt = CmtResult.orElseThrow();
        if(CmtResult.get().getSecret()==null) {
            productCmt.CnoGroupSave(cno,"f");
        }else {
            productCmt.CnoGroupSave(cno,productCmt.getSecret());
        }
        productCmtRepository.save(productCmt);
    }
    @Transactional
    public void ProductDelete(int pno) { productRepository.deleteByPno(pno); }

    public void ProductUpdate(ProductDTO dto, MultipartFile thumbnail, List<MultipartFile> files) {
        Optional<Product> result = productRepository.findByPno(dto.getPno());
        List<ProductFile> fileResult = productFileRepository.findAllByPno(result.get());

        Product product = result.orElseThrow();

        fileUtils.ShopUpdateFileDelete(product, fileResult);

        String thumbnailName = fileUtils.ShopFileUpload(thumbnail);
        product.ThumbnailChange(thumbnailName);
        productRepository.save(product);

        fileResult.clear();
        try {
            for (MultipartFile f : files) {
                Thread.sleep(100);
                String fileName = fileUtils.ShopFileUpload(f);

                ProductFile productFile = ProductFile.builder()
                        .pno(product)
                        .filename(fileName)
                        .build();

                productFileRepository.save(productFile);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ProductInquiryAdd(int pno) {
        Product product = productRepository.findByPno(pno).get();
        product.InquiryAdd();
        productRepository.save(product);
    }

    public List<Product> MainPopularityList() { return productRepository.mainPopularityList(); }
    public List<Product> MainNewList() { return productRepository.mainNewList(); }

    public List<Product> RecomList(String category) { return productRepository.recomList(category); }
}
