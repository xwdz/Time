package com.xwdz.time.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;


public class PermissionUtils {


    private PermissionUtils() {
    }

    private static PermissionUtils mUtils;

    public static synchronized PermissionUtils getInstace() {
        if (mUtils == null) {
            mUtils = new PermissionUtils();
        }
        return mUtils;
    }


    private List<String> mUreadPermissions = new ArrayList<String>();


    public List<String> fullLauncherPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mUreadPermissions != null) {
                mUreadPermissions.clear();
            }
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (mUreadPermissions != null)
                    mUreadPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (mUreadPermissions != null)
                    mUreadPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (mUreadPermissions != null)
                    mUreadPermissions.add(Manifest.permission.CAMERA);
            }

            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (mUreadPermissions != null)
                    mUreadPermissions.add(Manifest.permission.READ_PHONE_STATE);
            }
        }
        return mUreadPermissions;
    }
}
