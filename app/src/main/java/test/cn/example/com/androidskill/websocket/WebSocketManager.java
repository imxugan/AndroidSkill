package test.cn.example.com.androidskill.websocket;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xugan on 2018/8/8.
 */

public class WebSocketManager {
    private XywyWebsocketClient xywyWebsocketClient;
    private String vhost,userName,pwd;
    private static WebSocketManager instance = new WebSocketManager();
    private WebSocketManager(){}
    public void init(String vhost,String userName,String pwd){
        this.vhost = vhost;
        this.userName = userName;
        this.pwd = pwd;
    }
    public static WebSocketManager getInstance(){
        return instance;
    }

    public XywyWebsocketClient getWebsocketClient(){
        if(null == xywyWebsocketClient){
            synchronized (WebSocketManager.class){
                if(null == xywyWebsocketClient){
                    try {
                        xywyWebsocketClient = new XywyWebsocketClient(new URI("ws://121.40.165.18:8800"));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return xywyWebsocketClient;
    }


}
