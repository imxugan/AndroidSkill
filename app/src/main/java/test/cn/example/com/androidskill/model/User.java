package test.cn.example.com.androidskill.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xgxg on 2017/7/3.
 */

public class User implements Parcelable{
    private String name;
    private int age;
    private boolean isMale;
    protected User(Parcel in) {
        name = in.readString();
        age = in.readInt();
        isMale = (in.readInt() == 1)?true:false;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeInt(isMale?1:0);
    }
}
