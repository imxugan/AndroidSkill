package test.cn.example.com.androidskill.changeSkin;

/**
 * Created by xugan on 2019/4/16.
 */

public class SkinItem {
    /**
     * 属性名
     */
    private String name;

    /**
     * 属性的资源ID
     */
    private int resId;

    /**
     * 属性资源文件的名字，@drawable/bg，colorPriamry
     */
    private String entryName;

    /**
     * 属性的资源文件的类型
     */
    private String typeName;

    public  SkinItem(String name,int resId,String entryName,String typeName){
        this.name = name;
        this.resId = resId;
        this.entryName = entryName;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
