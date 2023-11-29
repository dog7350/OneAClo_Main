package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.Service.Shop.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrdersService ordersService;

    @PostMapping("/payment")
    public void Payment(OrdersDTO dto, HttpServletResponse res) {
        ordersService.OrderSave(dto);

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/order/orderList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/statusChange")
    public String StatusChange(@RequestBody HashMap<String, String> map) {
        ordersService.orderStatusUpdate(Integer.parseInt(map.get("ono")), map.get("status"));

        return "success";
    }

    @GetMapping("/userStatus")
    public void UserStatusChange(int ono, String status, HttpServletResponse res) {
        ordersService.orderStatusUpdate(ono, status);

        try {
            res.sendRedirect("http://www.oneaclo.kro.kr/order/orderList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
