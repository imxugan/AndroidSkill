package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CameraInter;
import test.cn.example.com.androidskill.inter.PhoneInter;

/**
 * Created by xgxg on 2017/7/19.
 */

public class Mobile {
    private CameraInter camera = new CameraImpl();
    private PhoneInter phone = new PhoneImpl();
    public void call(){
        phone.open();
        phone.dail();
    }

    public void videoChat(){
        phone.open();
        camera.open();
        phone.dail();
    }

    public void takePicture(){
        camera.open();
        camera.takePhoto();
    }
}
