package es.source.code.application;

import android.app.Application;
import java.util.ArrayList;
import es.source.code.activity.R;
import es.source.code.model.Food;
import es.source.code.model.User;

public class MessageOfApplication  extends Application {
    private ArrayList<Food> foodsList;
    private ArrayList<Food> foodsOrderList;
    private ArrayList<Food> foodsPayList;
    private static MessageOfApplication instance;
    private User user;

    public final static int COOLFOOD=0;
    public final static int HOTFOOD=1;
    public final static int SEAFOOD=2;
    public final static int WINEFOOD=3;

    public static MessageOfApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        foodsList=new ArrayList<Food>();
        foodsOrderList=new ArrayList<>();
        foodsPayList = new ArrayList<>();
        user=new User();
        createFood();
    }
    public ArrayList<Food> getFoodList(){
        return foodsList;
    }
    public ArrayList<Food> getFoodPayList(){
        return foodsPayList;
    }
    public ArrayList<Food> getOrderFoodList() {
        return foodsOrderList;
    }
    public void operateFoodOrderList(Food food,boolean isAdd) {
        boolean add = true;
        for (Food orderFood : foodsOrderList) {
            if (orderFood.getFoodName().equals(food.getFoodName())) {
                if (isAdd) {
                    // 避免重复添加
                    add = false;
                } else {
                    food.setOrder("点餐");
                    foodsOrderList.remove(orderFood);
                }
                break;
            }
        }
        if (isAdd && add) {
            food.setOrder("退点");
            foodsOrderList.add(food);
        }
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    private void createFood(){
        foodsList.add(new Food("葱淋莴苣",25,"点餐",R.drawable.cool_chongyouwuju,COOLFOOD));
        foodsList.add(new Food("凉拌腐竹",15,"点餐",R.drawable.cool_liangbangfuzu,COOLFOOD));
        foodsList.add(new Food("南昌拌粉",12,"点餐",R.drawable.cool_nanchangbanou,COOLFOOD));
        foodsList.add(new Food("泡椒风爪",30,"点餐",R.drawable.cool_paojiaofengzhua,COOLFOOD));
        foodsList.add(new Food("皮蛋豆腐",18,"点餐",R.drawable.cool_pidandoufu,COOLFOOD));
        foodsList.add(new Food("手撕杏鲍菇",30,"点餐",R.drawable.cool_shousixingbaogua,COOLFOOD));
        foodsList.add(new Food("香油黄瓜",34,"点餐",R.drawable.cool_xiangyouhuanggua,COOLFOOD));

        foodsList.add(new Food("炖蛋",23,"点餐",R.drawable.hot_dundan,HOTFOOD));
        foodsList.add(new Food("黄焖鸡",43,"点餐",R.drawable.hot_huangmenji,HOTFOOD));
        foodsList.add(new Food("可乐鸡翅",33,"点餐",R.drawable.hot_kelejichi,HOTFOOD));
        foodsList.add(new Food("口水鸡",53,"点餐",R.drawable.hot_koushuiji,HOTFOOD));
        foodsList.add(new Food("肉末豆角",29,"点餐",R.drawable.hot_roumudoujiaou,HOTFOOD));
        foodsList.add(new Food("糖醋排骨",45,"点餐",R.drawable.hot_tangcupaigu,HOTFOOD));
        foodsList.add(new Food("玉烧子",56,"点餐",R.drawable.hot_yusao,HOTFOOD));

        foodsList.add(new Food("八爪鱼",78,"点餐",R.drawable.sea_bazhuyu,SEAFOOD));
        foodsList.add(new Food("鲳鱼",67,"点餐",R.drawable.sea_changyu,SEAFOOD));
        foodsList.add(new Food("带鱼",75,"点餐",R.drawable.sea_daiyu,SEAFOOD));
        foodsList.add(new Food("凤尾虾球",56,"点餐",R.drawable.sea_fengweixiaqiu,SEAFOOD));
        foodsList.add(new Food("红烧鲫鱼",30,"点餐",R.drawable.sea_hongsaojiyu,SEAFOOD));
        foodsList.add(new Food("花蛤",36,"点餐",R.drawable.sea_huaha,SEAFOOD));
        foodsList.add(new Food("清蒸大闸蟹",106,"点餐",R.drawable.sea_qingzhengdahaixie,SEAFOOD));

        foodsList.add(new Food("薄荷酒",23,"点餐",R.drawable.wine_bohejiu,WINEFOOD));
        foodsList.add(new Food("鸡尾酒",43,"点餐",R.drawable.wine_jiweijiu,WINEFOOD));
        foodsList.add(new Food("青梅酒",33,"点餐",R.drawable.wine_qingmeijiu,WINEFOOD));
        foodsList.add(new Food("桑格利亚",53,"点餐",R.drawable.wine_sanggeliya,WINEFOOD));
        foodsList.add(new Food("山楂酒",29,"点餐",R.drawable.wine_shanzhajiu,WINEFOOD));
        foodsList.add(new Food("石榴酒",45,"点餐",R.drawable.wine_siliujiu,WINEFOOD));
        foodsList.add(new Food("杨梅酒",56,"点餐",R.drawable.wine_yangmeijiu,WINEFOOD));
    }

}
