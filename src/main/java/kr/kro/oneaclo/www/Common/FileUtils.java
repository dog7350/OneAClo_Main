package kr.kro.oneaclo.www.Common;

import kr.kro.oneaclo.www.Entity.Shop.Product;
import kr.kro.oneaclo.www.Entity.Shop.ProductFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileUtils {
    @Value("${real.upload.path}")
    public String path;

    public String QnaFileUpload(MultipartFile files, String id) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String fileName = "QNA_" + id + "_" + today + "_" + files.getOriginalFilename();

        Path savePath = Paths.get(path + "/file/" + fileName);

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

        Path savePath = Paths.get(path + "/file/" + fileName);

        try {
            files.transferTo(savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public void ShopUpdateFileDelete(Product product, List<ProductFile> fileResult) {
        File Origin = new File(path + "/file/" + product.getImg());
        Origin.delete();

        for (ProductFile pf : fileResult) {
            File originFile = new File(path + "/file/" + pf.getFilename());
            originFile.delete();
        }
    }

    public void MemberProfileUpload(MultipartFile file, String fileName) {
        File NameFilter = new File(path + "/profile/" + fileName);
        try{
            file.transferTo(NameFilter);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BoardFileUpload(MultipartFile file, String fileName) {
        File boardFile = new File(path + "/file/" + fileName);

        try {
            file.transferTo(boardFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BoardFileDelete(String fileName) {
        File boardFile = new File(path + "/file/" + fileName);

        try {
            boardFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
