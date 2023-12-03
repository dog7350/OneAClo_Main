package kr.kro.oneaclo.www.Common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class FileUtils {
    @Value("${spring.servlet.multipart.location}")
    public String path;

    public String QnaFileUpload(MultipartFile files, String id) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String fileName = "QNA_" + id + "_" + today + "_" + files.getOriginalFilename();

        Path savePath = Paths.get(path + "/" + fileName);

        try {
            files.transferTo(savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public String ShopFileUpload(MultipartFile files) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String fileName = "SHOP_" + today + "_" + files.getOriginalFilename();

        Path savePath = Paths.get(path + "/" + fileName);

        try {
            files.transferTo(savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
