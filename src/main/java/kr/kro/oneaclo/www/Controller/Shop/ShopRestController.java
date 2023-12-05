package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Shop.ProductCmtDTO;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Service.Shop.ProductCmtService;
import kr.kro.oneaclo.www.Service.Shop.ProductFileService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/shop")
@RequiredArgsConstructor
@Log4j2
public class ShopRestController {
    private final ProductService productService;
    private final ProductFileService productFileService;
    private final TokenProcess tokenProcess;
    private final ProductCmtService productCmtService;

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

    @PostMapping("/productModify")
    public void ProductModify(ProductDTO dto, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam(value = "files", required = false) List<MultipartFile> files, HttpServletResponse res) {
        productService.ProductUpdate(dto, thumbnail, files);

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/shop/detail?pno=" + dto.getPno());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/ReviewSave")
    public void ReviewSave(ProductCmtDTO dto,@RequestParam int pno, HttpSession session) {
        int Cno = productService.ReviewSave(dto,tokenProcess.getMembersToken(String.valueOf(session.getAttribute("UserInfo")),"id"),pno);
        productService.CnoSave(Cno,pno);
    }

    @GetMapping("/CmtDel")
    public void CmtDel(ProductCmtDTO dto) {
        productCmtService.CmtDel(dto);
    }
}
