package kr.kro.oneaclo.www.Service.Shop;

import kr.kro.oneaclo.www.Common.FileUtils;
import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Entity.Shop.ProductFile;
import kr.kro.oneaclo.www.Repository.Shop.ProductFileRepository;
import kr.kro.oneaclo.www.Repository.Shop.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService {
    private final FileUtils fileUtils;
    private final ProductRepository productRepository;
    private final ProductFileRepository productFileRepository;

    public void ProductFileUpload(String pName, List<MultipartFile> files) {
        if (!files.isEmpty()) {
            try {
                Thread.sleep(100);
                Optional<Product> product = productRepository.findByPname(pName);

                for (MultipartFile f : files) {
                    Thread.sleep(100);
                    String fileName = fileUtils.ShopFileUpload(f);

                    ProductFile productFile = ProductFile.builder()
                                                         .pno(product.get())
                                                         .filename(fileName)
                                                         .build();

                    productFileRepository.save(productFile);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ProductFile> ProductDetail(int pno) {
        Product product = productRepository.findByPno(pno).get();

        return productFileRepository.findAllByPno(product);
    }
}
