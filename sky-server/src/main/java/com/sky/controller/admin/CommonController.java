package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @param
 * @return
 */

@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) throws IOException {
        log.info("文件上传");
        // 1、获取文件信息
        try {
            String originalFileName = file.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 2、生成文件名
            String objectName = UUID.randomUUID().toString()+suffix;
            // 3、调用utils上传
            String url = aliOssUtil.upload(file.getBytes(),objectName);
            log.info("文件上传成功");
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e.getMessage());
            return Result.error("文件上传失败");
        }
    }
}
