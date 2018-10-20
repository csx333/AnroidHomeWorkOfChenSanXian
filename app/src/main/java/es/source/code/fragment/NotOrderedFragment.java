package es.source.code.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import es.source.code.activity.FoodOrderView;
import es.source.code.activity.FoodView;
import es.source.code.application.MessageOfApplication;
import es.source.code.activity.R;
import es.source.code.adapter.FoodOrderRecyclerAdapter;
import es.source.code.model.Food;


public class NotOrderedFragment extends Fragment {

    private View view;//定义view用来设置fragment的layout
    public RecyclerView mFoodRecyclerView;//定义RecyclerView
    private FoodOrderRecyclerAdapter mFoodRecyclerAdapter;
    private TextView number;
    private TextView price;
    private TextView textButton;
    public ArrayList<Food> foodsOrderList = MessageOfApplication.getInstance().getOrderFoodList();
    public ArrayList<Food> foodsPayList = MessageOfApplication.getInstance().getFoodPayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ordered, container, false);
//        initFoodsData();
        initRecyclerView();

        int i=0;
        textButton=(TextView) view.findViewById(R.id.btn_submit);
        price=(TextView) view.findViewById(R.id.order_price);
        number=(TextView)view.findViewById(R.id.order_number);

        number.setText("已点数量："+ foodsOrderList.size());
        for(Food food : foodsOrderList){
            i=i+food.getPrice();
        }
        price.setText("总价："+ i );
        textButton.setText("提交订单");
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textButton.getText().equals("提交订单") ){
                    for(Food food : foodsOrderList){
                       foodsPayList.add(food);
                    }
                    foodsOrderList.clear();
                    textButton.setText("撤销订单");
                }else {
                    for(Food food : MessageOfApplication.getInstance().getFoodPayList()){
                        MessageOfApplication.getInstance().operateFoodOrderList(food,true);
                    }
                    foodsPayList.clear();
                    textButton.setText("提交订单");
                }
                Intent intent = new Intent();
                intent.setClass(getActivity(),FoodOrderView.class);
                intent.putExtra("idOfFoodView",1);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
//    private void initFoodsData(){
//        foodsOrderList = MessageOfApplication.getInstance().getOrderFoodList();
//    }
    private void initRecyclerView(){
        //获取RecyclerView
        mFoodRecyclerView=(RecyclerView)view.findViewById(R.id.order_recyclerView);
        //创建adapter
        mFoodRecyclerAdapter = new FoodOrderRecyclerAdapter(getActivity(),foodsOrderList);
        //给RecyclerView设置adapter
        mFoodRecyclerView.setAdapter(mFoodRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mFoodRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }



}
