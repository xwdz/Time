package com.xwdz.time.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.JsonCallBack;
import com.xwdz.time.entity.Picture;
import com.xwdz.time.entity.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/4/11
 */
public class UploadFileModel extends ViewModel {

    private MutableLiveData<List<Picture>> mModel = new MutableLiveData<>();

    private static final String TAG = UploadFileModel.class.getSimpleName();

    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";

    public MutableLiveData<List<Picture>> getModel() {
        return mModel;
    }


    private boolean isRefresh;

    public boolean isRefresh() {
        return isRefresh;
    }

    /**
     * 上传图片
     *
     * @param list
     */
    public void upload(List<String> list,boolean isRefresh) throws IOException {
        this.isRefresh = isRefresh;

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
                .execute(new JsonCallBack<Response<List<Picture>>>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        mModel.postValue(null);
                    }

                    @Override
                    public void onSuccess(Call call, Response<List<Picture>> response) {
                        mModel.postValue(response.data);
                    }
                });

    }

    private String getFileName(String path) {
        int index = path.lastIndexOf(".");
        if (index != -1) {
            return path.substring(index);
        }
        return System.currentTimeMillis() + "";
    }
}
