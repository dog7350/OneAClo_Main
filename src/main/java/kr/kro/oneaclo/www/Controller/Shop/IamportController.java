package kr.kro.oneaclo.www.Controller.Shop;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@Controller
public class IamportController {
    private IamportClient iamportClient;

    public IamportController() {
        this.iamportClient = new IamportClient("5570202745814253", "aQCmoJ2BCDe1W43KU8YLlyZMfMSCuvS7MRKX25akQtlZykNNZaf14BCzcT4ps4v6SBXmb4IbBZ5npwUa");
    }

    @ResponseBody
    @RequestMapping(value = "/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(Model model, Locale locale, HttpSession session,
                                                    @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return iamportClient.paymentByImpUid(imp_uid);
    }
}
