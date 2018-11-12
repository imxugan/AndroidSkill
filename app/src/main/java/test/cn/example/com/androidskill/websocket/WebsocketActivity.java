package test.cn.example.com.androidskill.websocket;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/8.
 */

public class WebsocketActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_websocket;
    }

    @Override
    public int getMyDefinedEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getMyDefinedErrorLayoutId() {
        return 0;
    }

    @Override
    public void initView(){
        WebSocketManager.getInstance().init("test1","password1234");
        XywyWebsocketClient c = WebSocketManager.getInstance().getWebsocketClient();
        LogUtil.i("         "+c);
        WebSocketManager.getInstance().init("test1","password1234");
        c = WebSocketManager.getInstance().getWebsocketClient();
        LogUtil.i("         "+c);
        //Connect to a server normally
        try {
            c.connectBlocking();
            c.send( "111" );
            Thread.sleep( 10 );
            c.closeBlocking();
            //Disconnect manually and reconnect blocking
            c.reconnectBlocking();
            c.send( "112" );
//            Thread.sleep( 10000 );
            Thread.sleep( 200 );
            c.closeBlocking();
            //Disconnect manually and reconnect
            c.reconnect();
            Thread.sleep( 100 );
            c.send( "113" );
            Thread.sleep( 100 );
            c.closeBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
