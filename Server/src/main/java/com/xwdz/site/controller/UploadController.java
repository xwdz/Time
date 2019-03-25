package com.xwdz.site.controller;


import com.xwdz.site.entity.Picture;
import com.xwdz.site.entity.Response;
import com.xwdz.site.mapper.PictureMapper;
import com.xwdz.site.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 接受客户端Http请求
 * 1. 接收文件
 * 2. 将数据插入数据库
 */
@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private PictureMapper mPictureMapper;

    @Value("${upload.path}")
    private String mSaveFilePath;


    @PostMapping("upload")
    public Response<Picture> upload(@RequestParam(value = "file") final MultipartFile file,
                                    @RequestParam(value = "key") final String key,
                                    @RequestParam(value = "address", required = false) String address,
                                    @RequestParam(value = "desc", required = false) String desc,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        if (ApiSignature.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }

        if (file == null) {
            return Response.fail(Constant.ERROR_MISS_UPLOAD, "please upload file!");
        }


        try {
            File saveFile = createSaveFile(file.getOriginalFilename());

            ThreadManager.execute(() -> {
                try {
                    FileUtils.save(file.getBytes(), saveFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Picture picture = new Picture();
            picture.setPath(saveFile.getAbsolutePath());
            picture.setAddress(CommUtils.isEmpty(address) ? "" : address);
            picture.setUploadTime(String.valueOf(System.currentTimeMillis()));
            picture.setDesc(CommUtils.isEmpty(desc) ? "" : desc);
            picture.setId(CommUtils.generateId());
            picture.setName(saveFile.getName());

            mPictureMapper.create(picture);
            return Response.ok(picture);


        } catch (Throwable e) {
            e.printStackTrace();
            return Response.fail(Constant.ERROR_UPLOAD, e.toString());
        }
    }


    @GetMapping("query")
    public Response<Picture> query(
            @RequestParam("key") String key,
            @RequestParam("id") String id) {

        if (ApiSignature.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }

        Picture picture = mPictureMapper.findById(id);
        if (picture != null) {
            return Response.ok(picture);
        }
        return Response.fail(Constant.ERROR_MISS_FAIL_PICTURE, "query fail");
    }

    @GetMapping("queryAll")
    public Response<List<Picture>> queryAll(@RequestParam("key") String key) {
        if (ApiSignature.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }

        return Response.ok(mPictureMapper.findAll());
    }


    private File createSaveFile(String name) throws IOException {
        File saveFile = new File(mSaveFilePath, name);

        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdir();
        }

        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        Logger.i("save File :" + saveFile.toString());
        return saveFile;
    }
}
