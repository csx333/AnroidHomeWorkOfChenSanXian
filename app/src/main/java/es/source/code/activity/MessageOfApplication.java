package es.source.code.activity;


import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import es.source.code.model.Food;

public class MessageOfApplication  extends Application {
    private static final String VALUE = "SCOS";
    private String messOfLogin;
    private ArrayList<Food> foodsOrderList;
    private static MessageOfApplication instance;

    public static MessageOfApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        setValueOfMessOfLogin(VALUE); // 初始化全局变量
        instance=this;
    }

    public ArrayList<Food> getOrderFoodList() {
        return foodsOrderList;
    }

    public void operateFoodOrderList(Food food,boolean isAdd) {
        boolean add = true;
        if (foodsOrderList == null) {
            foodsOrderList = new ArrayList<>();
        }
        for (Food orderFood : foodsOrderList) {
            if (orderFood.getFoodName().equals(food.getFoodName())) {
                if (isAdd) {
                    // 避免重复添加
                    add = false;
                } else {
                    foodsOrderList.remove(orderFood);
                }
                break;
            }
        }
        if (isAdd && add) {
            foodsOrderList.add(food);
        }
    }
    public void setValueOfMessOfLogin(String value)
    {
        this.messOfLogin = value;
    }

    public String getValueOfMessOfLogin()
    {
        return messOfLogin;
    }



}
