package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import es.source.code.activity.R;
import es.source.code.adapter.FoodRecyclerAdapter;
import es.source.code.model.Food;

public class coolFoodFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    private ArrayList<Food> foodsList = new ArrayList<>();
    public RecyclerView mFoodRecyclerView;//定义RecyclerView
    private FoodRecyclerAdapter mFoodRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cool_food, container, false);
        initFoodsData();
        initRecyclerView();
        return view;
    }

    private void initFoodsData(){
        foodsList.add(new Food("葱淋莴苣",25,"点餐",R.drawable.cool_chongyouwuju));
        foodsList.add(new Food("凉拌腐竹",25,"点餐",R.drawable.cool_liangbangfuzu));
        foodsList.add(new Food("南昌拌粉",12,"点餐",R.drawable.cool_nanchangbanou));
        foodsList.add(new Food("泡椒风爪",30,"点餐",R.drawable.cool_paojiaofengzhua));
        foodsList.add(new Food("皮蛋豆腐",18,"点餐",R.drawable.cool_pidandoufu));
        foodsList.add(new Food("手撕杏鲍菇",30,"点餐",R.drawable.cool_shousixingbaogua));
        foodsList.add(new Food("香油黄瓜",34,"点餐",R.drawable.cool_xiangyouhuanggua));
    }

    private void initRecyclerView(){
        //获取RecyclerView
        mFoodRecyclerView=(RecyclerView)view.findViewById(R.id.cool_recyclerView);
        //创建adapter
        mFoodRecyclerAdapter = new FoodRecyclerAdapter(getActivity(),foodsList);
        //给RecyclerView设置adapter
        mFoodRecyclerView.setAdapter(mFoodRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mFoodRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }
}
