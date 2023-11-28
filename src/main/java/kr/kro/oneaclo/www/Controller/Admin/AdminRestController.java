package kr.kro.oneaclo.www.Controller.Admin;

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
    public String productDelete(@RequestParam String pno) {
        productService.ProductDelete(Integer.parseInt(pno));

        return "redirect:/admin/productList";
    }

    @PostMapping("/productModify")
    public String productModify(ProductDTO dto, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        productService.ProductUpdate(dto, thumbnail, files);

        return "redirect:/shop/detail?pno=" + dto.getPno();
    }
}
