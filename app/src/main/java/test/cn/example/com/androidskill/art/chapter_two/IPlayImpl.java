package test.cn.example.com.androidskill.art.chapter_two;

import android.os.Process;
import android.os.RemoteException;

import test.cn.example.com.androidskill.IPlayInterface;
import test.cn.example.com.util.LogUtil;

public class IPlayImpl extends IPlayInterface.Stub {
    @Override
    public void palyMusic(String musicName) throws RemoteException {
        LogUtil.e("播放的歌曲是   "+musicName+"       Process.myPid()=   "+ Process.myPid());
    }
}
