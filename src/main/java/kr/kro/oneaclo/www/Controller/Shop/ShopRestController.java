package kr.kro.oneaclo.www.Controller.Shop;

import com.siot.IamportRestClient.IamportClient;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Entity.Shop.Orders;
import kr.kro.oneaclo.www.Service.Shop.ProductFileService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/shop")
@RequiredArgsConstructor
@Log4j2
public class ShopRestController {
    private final ProductService productService;
    private final ProductFileService productFileService;

    @PostMapping("/productUpload")
    public void ProductUpload(ProductDTO dto, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam(value = "files", required = false) List<MultipartFile> files, HttpServletResponse res) {
        productService.ProductUpload(dto, thumbnail);
        productFileService.ProductFileUpload(dto.getPname(), files);

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/admin/productList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
