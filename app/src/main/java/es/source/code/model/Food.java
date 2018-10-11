package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    // 菜名
    private String foodName;
    // 价格
    private int price;
    // 库存;
//    private int store;
    // 是否点单
    private String order;
    // 图片资源ID;
    private int imgId;

    public Food(){
    }

    public Food(String foodName, int price, String order, int imgId) {
        this.foodName = foodName;
        this.price = price;
//        this.store = store;
        this.order = order;
        this.imgId = imgId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String Order() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

//    public int getStore() {
//        return store;
//    }
//
//    public void setStore(int store) {
//        this.store = store;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.foodName);
        dest.writeInt(this.price);
//        dest.writeInt(this.store);
        dest.writeString(this.order);
        dest.writeInt(this.imgId);
    }

    protected Food(Parcel in) {
        this.foodName = in.readString();
        this.price = in.readInt();
//        this.store = in.readInt();
        this.order = in.readString();
        this.imgId = in.readInt();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }
        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
