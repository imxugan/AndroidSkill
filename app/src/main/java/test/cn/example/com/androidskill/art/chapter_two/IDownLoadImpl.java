package test.cn.example.com.androidskill.art.chapter_two;

import android.os.Process;
import android.os.RemoteException;

import test.cn.example.com.androidskill.IDownLoadInterface;
import test.cn.example.com.util.LogUtil;

public class IDownLoadImpl extends IDownLoadInterface.Stub {
    @Override
    public void downLoad(String url) throws RemoteException {
        LogUtil.e("下载的url地址是   "+url+"       Process.myPid()=   "+ Process.myPid());
    }
}
