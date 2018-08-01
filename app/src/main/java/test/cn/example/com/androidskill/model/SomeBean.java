package test.cn.example.com.androidskill.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by xugan on 2018/8/1.
 */

public class SomeBean {
    private String name;
    private int age;

    public void setName(String name){
        String oldName = this.name;
        this.name = name;
        support.firePropertyChange("name",oldName,this.name);
    }

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }
}
