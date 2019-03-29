package com.xwdz.time.model;

import android.util.Log;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.JsonCallBack;
import com.xwdz.time.entity.Picture;
import com.xwdz.time.entity.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑操作层
 * 1. 获取所有服务器上面图片
 *
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/3/26
 */
public class PictureModelProvide {

    private static final String TAG = PictureModelProvide.class.getSimpleName();

    private static final String URL_QUERY  = "http://47.106.223.246:80/file/queryAll";
    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";

    /**
     * 请求服务器所有图片
     *
     * @param pageNumber
     * @param callBack
     */
    public void request(int pageNumber, JsonCallBack<Response<List<Picture>>> callBack) {
        QuietHttp.getImpl()
                .get(URL_QUERY)
                .addParams("pageNum", String.valueOf(pageNumber))
                .addParams("pageSize", String.valueOf("5"))
                .addParams("key", "10926a9165054566b6df6a8410e45f08")
                .execute(callBack);
    }

    /**
     * 上传图片
     *
     * @param list
     */
    public void upload(List<String> list, JsonCallBack<Response<List<Picture>>> callBack) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }

        final List<File> uploadFiles = new ArrayList<>();

        for (String path : list) {
            String name = getFileName(path);
            Log.i(TAG, " path :" + path);
            Log.i(TAG, " name :" + name);
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            uploadFiles.add(file);
        }


        Log.i(TAG, "size:" + uploadFiles.size());
        QuietHttp.getImpl()
                .postFile(URL_UPLOAD)
                .addParams("address", "深圳")
                .addParams("desc", "phone")
                .addParams("key", "10926a9165054566b6df6a8410e45f08")
                .uploadFiles("files", uploadFiles)
                .execute(callBack);

    }

    private String getFileName(String path) {
        int index = path.lastIndexOf(".");
        if (index != -1) {
            return path.substring(index, path.length());
        }
        return System.currentTimeMillis() + "";
    }
}
