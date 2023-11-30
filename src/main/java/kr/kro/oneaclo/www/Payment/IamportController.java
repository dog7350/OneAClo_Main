package kr.kro.oneaclo.www.Payment;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Locale;

@Controller
public class IamportController {
    private IamportClient iamportClient;

    @Autowired
    private RefundService refundService;

    String apiKey = "5570202745814253";
    String secretKey = "aQCmoJ2BCDe1W43KU8YLlyZMfMSCuvS7MRKX25akQtlZykNNZaf14BCzcT4ps4v6SBXmb4IbBZ5npwUa";

    public IamportController() { this.iamportClient = new IamportClient(apiKey, secretKey); }

    @ResponseBody
    @RequestMapping(value = "/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(Model model, Locale locale, HttpSession session,
                                                    @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }

    @GetMapping("/cancelPay")
    public String CancelPayment(@RequestParam("uid") String uid, @RequestParam("price") int price, @RequestParam("msg") String msg) throws IOException {
        String token = refundService.getToken(apiKey, secretKey);
        refundService.refundRequest(token, uid, price, msg);

        return "redirect:/admin/orderList";
    }
}
