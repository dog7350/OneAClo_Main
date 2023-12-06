package kr.kro.oneaclo.www.Configure;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandler implements ErrorController {
    /*
        100 번대 상태코드
        100 ----> 데이터의 일부를 서버가 받은 상태(처리중인 상태)

        200 번대 상태코드 : 정상적인 처리 후 응답
        200 ----> OK 에러없이 정상처리
        204 ----> 정상처리 되었으나, 서버에 보낼 데이터가 없음

        300 번대(다른 URL 처리)
        301: 요청한 URL이 새로 변경되었음.
        304: 기존의 데이터와 변경된것이 없음

        400번대
        400: 요청에 문제가 있기때문에 서버에서 인식할 수 없음
        403: 서버에서 허락되지않음
        404: 요청 URL을 찾을 수 없음
        406: 전송 방식이 허락되지 않음(REST방식에서 자주 나타나는 상태코드)

        500번대
        500: 서버에서 처리시 문제가 발생(프로그램 내부적인 오류)
        502: 게이트웨이, 프록시 상태의 문제(과부하)
        503: 일시적인 서비스 중단 상태
        504: 지정된 처리시간이 지나서 처리되지 못하는 경우
    */
    @GetMapping("/error")
    public String handleError(HttpServletRequest req) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int code = Integer.valueOf(status.toString());

            if (code == HttpStatus.NOT_FOUND.value()) return "views/common/e404";
            else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) return "views/common/e500";
            else System.out.println(code);
        }

        return "views/common/error";
    }
}
