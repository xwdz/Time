package com.xwdz.time.repository;

import android.util.Log;

import com.xwdz.http.QuietHttp;
import com.xwdz.http.callback.JsonCallBack;
import com.xwdz.time.entity.Picture;
import com.xwdz.time.entity.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import okhttp3.Call;

/**
 * @author xingwei.huang (xwdz9989@gamil.com)
 * @since 2019/4/11
 */
public class PictureListModel extends ViewModel {

    private MutableLiveData<List<Picture>> mModel = new MutableLiveData<>();

    private static final String TAG = PictureListModel.class.getSimpleName();


    private static final String URL_QUERY = "http://47.106.223.246:80/file/queryAll";
    private static final String URL_UPLOAD = "http://47.106.223.246:80/file/uploads";


    public MutableLiveData<List<Picture>> getModel() {
        return mModel;
    }

    private volatile boolean isRefresh;


    /**
     * 请求服务器所有图片
     *
     * @param pageNumber
     */
    public void load(int pageNumber,boolean isRefresh) {
        this.isRefresh = isRefresh;

        QuietHttp.getImpl()
                .get(URL_QUERY)
                .addParams("pageNum", String.valueOf(pageNumber))
                .addParams("pageSize", String.valueOf("5"))
                .addParams("key", "10926a9165054566b6df6a8410e45f08")
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

    public boolean isRefresh() {
        return isRefresh;
    }
}
