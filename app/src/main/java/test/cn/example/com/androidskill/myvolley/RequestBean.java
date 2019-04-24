package test.cn.example.com.androidskill.myvolley;

/**
 * Created by xugan on 2019/4/24.
 */

public class RequestBean {
    private String id;
    private String key;

    public RequestBean(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
