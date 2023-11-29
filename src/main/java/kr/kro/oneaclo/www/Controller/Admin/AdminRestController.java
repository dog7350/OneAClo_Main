package kr.kro.oneaclo.www.Controller.Admin;

import jakarta.servlet.http.HttpServletResponse;
import kr.kro.oneaclo.www.DTO.Shop.ProductDTO;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import kr.kro.oneaclo.www.Service.Shop.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminRestController {
    private final MembersService membersService;
    private final ProductService productService;

    @PostMapping("/authChange")
    public void AuthChange(@RequestParam String UserId, @RequestParam String UserAuth) {
        membersService.AuthChange(UserId, UserAuth);
    }
    @PostMapping("/activeChange")
    public void ActiveChange(@RequestParam String UserId, @RequestParam String UserActive) {
        membersService.ActiveChange(UserId, UserActive);
    }
    @PostMapping("/deleteUser")
    public void DeleteUser(@RequestParam String UserId) {
        membersService.UserDel(UserId);
    }

    @GetMapping("/productDelete")
    public void productDelete(@RequestParam String pno, HttpServletResponse res) {
        productService.ProductDelete(Integer.parseInt(pno));

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/admin/productList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/productModify")
    public void productModify(ProductDTO dto, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam(value = "files", required = false) List<MultipartFile> files, HttpServletResponse res) {
        productService.ProductUpdate(dto, thumbnail, files);

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/shop/detail?pno=" + dto.getPno());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
