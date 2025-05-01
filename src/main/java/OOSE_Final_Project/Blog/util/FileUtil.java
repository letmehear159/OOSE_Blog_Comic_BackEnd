package OOSE_Final_Project.Blog.util;

import OOSE_Final_Project.Blog.config.UploadPathHolder;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class FileUtil {


    public static boolean isImageFile(MultipartFile file) throws FileUploadException {

        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png");
        final String fileName = file.getOriginalFilename();

        // Check file empty
        if (file.isEmpty()) {
            throw new FileUploadException("You have not selected any files, please select a file");
        }

        boolean isValidExtension = allowedExtensions.stream()
                                                    .anyMatch(ext -> fileName.toLowerCase()
                                                                             .endsWith("." + ext));
        if (!isValidExtension) {
            throw new IllegalArgumentException("Invalid file format");
        }
        return true;
    }

    public static String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID() + "_" + filename;

        String absPath = new File(UploadPathHolder.uploadDir).getAbsolutePath();
        File folder = new File(absPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Tạo thư mục nếu chưa có
        }

        File dest = new File(folder, uniqueFilename);
        file.transferTo(dest);
        return uniqueFilename;
    }

    public static boolean deleteFile(String fileName) {
        File folder = new File(UploadPathHolder.uploadDir); // hoặc dùng biến đã cấu hình từ @Value
        File file = new File(folder, fileName);

        if (file.exists()) {
            return file.delete(); // Trả về true nếu xóa thành công
        } else {
            return false; // File không tồn tại
        }
    }

}
