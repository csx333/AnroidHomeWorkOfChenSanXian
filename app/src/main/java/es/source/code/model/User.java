package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String userName;
    private String password;
    private boolean oldUser;

    public User()
    {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOldUser() {
        return oldUser;
    }

    public void setOldUser(boolean oldUser) {
        this.oldUser = oldUser;
    }
    //重写describeContents方法，内容接口描述，默认返回0就可以
    @Override
    public int describeContents()
    {
        return 0;
    }
    //重写writeToParcel方法，将你的对象序列化为一个Parcel对象，即：将类的数据写入外部提供的Parcel中，打包需要传递的数据到Parcel容器保存，以便从 Parcel容器获取数据
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(userName);
        out.writeString(password);
        out.writeByte(this.oldUser ? (byte) 1 : (byte) 0);
    }
    protected User(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.oldUser = in.readByte() != 0;
    }
    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


}
