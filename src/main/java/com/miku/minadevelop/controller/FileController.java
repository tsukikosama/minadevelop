package com.miku.minadevelop.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.miku.minadevelop.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/upload")
@Slf4j
public class FileController {


    @Value("${minadevelop.fileurl}")
    public String fileUrl;

    @Value("${minadevelop.weburl}")
    private String webUrl;
    /**
     * 文件上床接口
     */
    @PostMapping("/file")
    public String upload(MultipartFile file) {
        System.out.println(file.getName());
        File f = new File(fileUrl);
        if(!f.exists()){
            System.out.println("1");
            f.mkdirs();
        }
        //获取图片名称
        String name = file.getOriginalFilename();
        //获取图片后缀
        String suffix = FileNameUtil.extName(name);
        //重命名图片
        String newFileName = IdUtil.randomUUID();
        newFileName = newFileName+"."+suffix;
        try {
            //把文件写入对应的位置
            file.transferTo(new File(fileUrl+newFileName));
        }catch (IOException e){
            log.error("图片上传异常");
            throw new RuntimeException("文件上传异常");
        }
        return webUrl+newFileName;
    }

    /**
     * 普通的图片上传接口
     */
    @PostMapping("/img")
    public String img() {
        return "普通的图片上传";
    }
}
