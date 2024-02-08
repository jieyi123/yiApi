package com.pjieyi.yiapi.controller;

import com.pjieyi.yiapi.common.BaseResponse;
import com.pjieyi.yiapi.common.ResultUtils;
import com.pjieyi.yiapi.utils.AliyunOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Author pjieyi
 * @Description 文件上传下载
 */
@RestController
public class FileUploadController {

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file) throws IOException {
        //获取原始文件名
        String filename=file.getOriginalFilename();
        filename= UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
        String url=aliyunOssUtil.upload(filename,file.getInputStream());
        //file.transferTo(new File("C:\\Users\\pjy17\\Desktop\\img\\"+filename));
        return ResultUtils.success(url);
    }

    @GetMapping("/sdk")
    public void getSdk(HttpServletResponse response) throws IOException {
        // 获取要下载的文件
        org.springframework.core.io.Resource resource = new ClassPathResource("yiapi-client-sdk-0.0.1.jar");
        InputStream inputStream = resource.getInputStream();

        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=yiapi-client-sdk-0.0.1.jar");

        // 将文件内容写入响应
        try (OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }
}
