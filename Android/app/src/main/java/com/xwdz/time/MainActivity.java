package com.xwdz.time;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;
import com.github.nukc.LoadMoreWrapper.LoadMoreWrapper;
import com.xwdz.http.callback.JsonCallBack;
import com.xwdz.time.adpater.MainAdapter;
import com.xwdz.time.entity.Picture;
import com.xwdz.time.entity.Response;
import com.xwdz.time.model.PictureModelProvide;
import com.xwdz.time.ui.MoreFooterView;
import com.xwdz.time.util.LoadingDialog;
import com.xwdz.time.util.PermissionUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE = 200;
    private static final int MAX_COUNT    = 9;

    private PictureModelProvide mPictureModelProvide = new PictureModelProvide();
    private RecyclerView       mMainRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainAdapter        mMainAdapter;
    private LoadingDialog      mLoadingDialog;

    private int mPageNumber = 1;
    private LoadMoreWrapper mLoadMoreWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingDialog = new LoadingDialog(MainActivity.this);
        initView();

        PermissionUtils.getInstace().fullLauncherPermission(this);

        requestListHttp(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNumber = 1;
                requestListHttp(true);
            }
        });

        //
        mLoadMoreWrapper = LoadMoreWrapper.with(mMainAdapter)
                .setFooterView(new MoreFooterView(MainActivity.this))
                .setShowNoMoreEnabled(true)
                .setListener(new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
                        Log.i("TAG", "enable:" + enabled.getLoadMoreEnabled());
                        if (enabled.getLoadMoreEnabled()) {
                            mPageNumber++;
                            requestListHttp(false);
                        }
                    }
                });
        mLoadMoreWrapper.into(mMainRecyclerView);
    }

    private void requestListHttp(final boolean isRefresh) {
        mPictureModelProvide.request(mPageNumber, new JsonCallBack<Response<List<Picture>>>() {
            @Override
            public void onSuccess(Call call, Response<List<Picture>> response) {
                List<Picture> data = response.data;
                if (data != null && !data.isEmpty()) {
                    dispatchHttpSuccess(isRefresh, data);
                }

                if (mLoadingDialog.isShowing()){
                    mLoadingDialog.dismiss();
                }

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });
    }


    @SuppressLint("NewApi")
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mMainRecyclerView = findViewById(R.id.main_list);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        Drawable drawable = getDrawable(R.drawable.list_divider);
        mMainRecyclerView.addItemDecoration(new com.xwdz.time.util.DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL, drawable));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCap();
            }
        });

        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mMainAdapter = new MainAdapter();
        mMainRecyclerView.setItemAnimator(null);
        mMainRecyclerView.setAdapter(mMainAdapter);
    }


    private void openCap() {
        Matisse.from(MainActivity.this)
                .choose(MimeType.ofAll())//照片视频全部显示
                .countable(true)//有序选择图片
                .maxSelectable(MAX_COUNT)//最大选择数量为9
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.size))//图片显示表格的大小getResources()
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Dracula)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new GlideEngine())//加载方式
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.xwdz.time.fileprovider"))
                .forResult(REQUEST_CODE);//请求码
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.github) {
            openGithub();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openGithub() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/xwdz/Time"));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                List<String> list = Matisse.obtainPathResult(data);
                dispatchActivityResultUploadHttp(list, true);
            }
        }
    }

    private void dispatchActivityResultUploadHttp(List<String> list, final boolean isRefresh) {
        try {
            mLoadingDialog.show();
            mPageNumber = 1;
            mPictureModelProvide.upload(list, new JsonCallBack<Response<List<Picture>>>() {
                @Override
                public void onSuccess(Call call, Response<List<Picture>> response) {
                    if ("200".equals(response.code)) {
                        requestListHttp(true);
                    }

                }

                @Override
                public void onFailure(Call call, Exception e) {
                    mLoadingDialog.stopLoading();

                    Log.e(MainActivity.class.getSimpleName(), "fail:" + e.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatchHttpSuccess(boolean isRefresh, List<Picture> data) {
        if (data == null || data.isEmpty()) {
            mLoadMoreWrapper.setLoadMoreEnabled(false);
            return;
        }

        if (isRefresh) {
            mMainAdapter.update(data);
        } else {
            mMainAdapter.add(data);
        }

    }
}
