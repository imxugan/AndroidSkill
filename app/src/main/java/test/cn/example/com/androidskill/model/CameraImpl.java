package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CameraInter;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/19.
 */

public class CameraImpl implements CameraInter {
    @Override
    public void open() {
        LogUtil.i("打开相机");
    }

    @Override
    public void takePhoto() {
        LogUtil.i("拍照");
    }

    @Override
    public void close() {
        LogUtil.i("关闭相机");
    }
}
