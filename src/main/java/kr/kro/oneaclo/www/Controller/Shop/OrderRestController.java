package kr.kro.oneaclo.www.Controller.Shop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Log.LogDTO;
import kr.kro.oneaclo.www.DTO.Shop.OrdersDTO;
import kr.kro.oneaclo.www.Service.Logs.OrderLogService;
import kr.kro.oneaclo.www.Service.Shop.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final TokenProcess tokenProcess;
    private final OrdersService ordersService;
    private final OrderLogService orderLogService;

    private String[] arr = {"id", "age", "gender", "address"};
    private LogDTO log;

    private void TokenList(String token) {
        Map<String, String> map = new HashMap<>();

        if (token != null) {
            for (String str : arr) map.put(str, tokenProcess.getMembersToken(token, str));

            log = new LogDTO();

            log.setId(map.get("id"));
            log.setAge(Integer.parseInt(map.get("age")));
            log.setGender(map.get("gender"));
            log.setAddress(map.get("address"));
        }
    }

    @PostMapping("/payment")
    public void Payment(OrdersDTO dto, HttpServletResponse res, HttpSession session) {
        String token = (String) session.getAttribute("UserInfo");
        TokenList(token);
        ordersService.OrderSave(dto);
        orderLogService.OrderLogSave(dto, log);

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
