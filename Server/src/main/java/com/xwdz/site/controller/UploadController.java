package com.xwdz.site.controller;


import com.github.pagehelper.PageHelper;
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
import java.util.ArrayList;
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


    @PostMapping("uploads")
    public Response<List<Picture>> upload(@RequestParam(value = "files") final MultipartFile[] files,
                                          @RequestParam(value = "key") final String key,
                                          @RequestParam(value = "address", required = false) String address,
                                          @RequestParam(value = "desc", required = false) String desc,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {

        if (SignatureController.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }

        if (files == null) {
            return Response.fail(Constant.ERROR_MISS_UPLOAD, "please upload files!");
        }

        try {
            List<Picture> results = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
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
                picture.setUkey(key);

                mPictureMapper.create(picture);
                results.add(picture);
            }

            return Response.ok(results);


        } catch (Throwable e) {
            e.printStackTrace();
            return Response.fail(Constant.ERROR_UPLOAD, e.toString());
        }
    }


    @GetMapping("query")
    public Response<Picture> query(
            @RequestParam("key") String key,
            @RequestParam("id") String id) {

        if (SignatureController.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }

        Picture picture = mPictureMapper.findById(id, key);
        if (picture != null) {
            return Response.ok(picture);
        }
        return Response.fail(Constant.ERROR_MISS_FAIL_PICTURE, "query fail");
    }

    @GetMapping("queryAll")
    public Response<List<Picture>> queryAll(@RequestParam("key") String key,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int number,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        if (SignatureController.getImpl().checkSignatureFail(key)) {
            return Response.fail(Constant.ERROR_KEY_FAIL, "key is error!");
        }
        PageHelper.startPage(number, pageSize);
        List<Picture> list = mPictureMapper.findAll(key);
        return Response.ok(list);
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
