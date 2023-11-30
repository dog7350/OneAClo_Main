package kr.kro.oneaclo.www.Controller.QnA;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import kr.kro.oneaclo.www.Common.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/qna")
@RequiredArgsConstructor
@Log4j2
public class QnaRestController {
    private final FileUtils fileUtils;

    @PostMapping("/fileUpload")
    public String FileUpload(@RequestParam("files") MultipartFile files, @RequestParam("id") String id) {
        return fileUtils.QnaFileUpload(files, id);
    }
}
