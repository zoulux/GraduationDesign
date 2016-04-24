package com.lyy.hitogether.global;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.lyy.hitogether.bean.Group;
import com.lyy.hitogether.bean.MyUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class App extends Application {

    private static App mInstance;
    private MyUser currentUser;

    public static App getInsatnce() {
        return mInstance;
    }

    List<Group> groupList = new ArrayList<Group>();


    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    List<UserInfo> userInfos = new ArrayList<UserInfo>();

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    @Override
    public void onCreate() {
        mInstance = this;

        super.onCreate();

        if (getApplicationInfo().packageName
                .equals(getCurrenPro(getApplicationContext()))
                || "io.rong.push".equals(getCurrenPro(getApplicationContext()))) {
            RongIM.init(this);

            if (getApplicationInfo().packageName
                    .equals(getCurrenPro(getApplicationContext()))) {

                // RongContext.init(this);

                RongCloudEvent.init(this);
                ImageLoader.getInstance().init(
                        ImageLoaderConfiguration.createDefault(this));
                BmobConfiguration config = new BmobConfiguration.Builder(this)
                        .customExternalCacheDir("Hitogether_cache").build();
                BmobPro.getInstance(this).initConfig(config);

                Log.i("TAg", ""
                        + BmobPro.getInstance(this).getCacheDownloadDir());
            }
        }

    }

    private Object getCurrenPro(Context context) {

        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (RunningAppProcessInfo appPro : activityManager
                .getRunningAppProcesses()) {
            if (appPro.pid == pid) {
                return appPro.processName;
            }
        }

        return null;
    }

    public void setCurrentUser(MyUser user) {
        currentUser = user;
    }

    public MyUser getCurrentUser() {
        return currentUser;
    }
}
