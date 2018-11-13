package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.FoodOrderView;
import es.source.code.activity.FoodView;
import es.source.code.application.MessageOfApplication;
import es.source.code.activity.R;
import es.source.code.adapter.FoodViewRecyclerAdapter;
import es.source.code.model.Food;

public class FoodFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    private ArrayList<Food> foodsList =new ArrayList<>();
    public RecyclerView mFoodRecyclerView;//定义RecyclerView
    public static FoodViewRecyclerAdapter mFoodViewRecyclerAdapter;
    private int styleOfFood;

    public static FoodFragment newInstance(int styleOfFood){
        FoodFragment foodFragment= new FoodFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("name", styleOfFood);
        foodFragment.setArguments(bundle);
        return foodFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);

        //接受构造函数传入的值
        Bundle args= getArguments();
        if (args != null){
            styleOfFood=args.getInt("name");
        }
        initFoodsData();
        initRecyclerView();
        return view;
    }
    private void initFoodsData(){
        for(Food item: MessageOfApplication.getInstance().getFoodList()){
            if(item.getStyle() == styleOfFood){
                foodsList.add(item);
            }
        }
    }
    public void initRecyclerView(){
        //获取RecyclerView
        mFoodRecyclerView=(RecyclerView)view.findViewById(R.id.cool_recyclerView);
        //创建adapter
        mFoodViewRecyclerAdapter = new FoodViewRecyclerAdapter(getActivity(),MessageOfApplication.getInstance().getFoodList(),styleOfFood);
        //给RecyclerView设置adapter
        mFoodRecyclerView.setAdapter(mFoodViewRecyclerAdapter);
        mFoodViewRecyclerAdapter.setOnItemClickListener(new FoodViewRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ArrayList<Food> foodsList1,int position) {
                Intent intent =new Intent(getActivity(),FoodDetailed.class);
                intent.putExtra("positionOfItem",position);
                intent.putParcelableArrayListExtra("foodList",foodsList1);
                startActivity(intent);
            }
            @Override
            public void OnItemButtonClick(View v,int position) {
                Food food = foodsList.get(position);
                if(food.getOrder().equals("点餐")) {
                    MessageOfApplication.getInstance().operateFoodOrderList(food,true);
                    Toast.makeText(v.getContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    food.setOrder("退点");
                }else{
                    MessageOfApplication.getInstance().operateFoodOrderList(food,false);
                    food.setOrder("点餐");
                }
            }
        });
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mFoodRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }
}
