package kr.kro.oneaclo.www.Controller.Shop;

import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
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

    @GetMapping("/cancelPay")
    public String CancelPayment(@RequestParam("uid") String uid, @RequestParam("price") int price, @RequestParam("msg") String msg) throws IOException, ParseException {
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("reason", msg);
        json.addProperty("imp_uid", uid);
        json.addProperty("amount", price);
        json.addProperty("checksum", price);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        return "redirect:/admin/orderList";
    }
}
