package es.source.code.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.R;
import es.source.code.adapter.FoodOrderRecyclerAdapter;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.Food;
import es.source.code.model.User;


public class OrderedFragment extends Fragment {

    private View view;//定义view用来设置fragment的layout
    public ArrayList<Food> foodsPayList;
    public RecyclerView mFoodRecyclerView;//定义RecyclerView
    private FoodOrderRecyclerAdapter mFoodRecyclerAdapter;
    private TextView number;
    private TextView price;
    private TextView textButton;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ordered, container, false);
        initFoodsData();
        initRecyclerView();

        user = MessageOfApplication.getInstance().getUser();
        int i=0;
        textButton=(TextView) view.findViewById(R.id.btn_submit);
        price=(TextView) view.findViewById(R.id.order_price);
        number=(TextView)view.findViewById(R.id.order_number);

        number.setText("已点数量："+ foodsPayList.size());
        for(Food food: foodsPayList){
            i=i+food.getPrice();
        }
        price.setText("总价："+i);

        textButton.setText("结账");
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textButton.getText().toString().equals("结账")){
                    if(user!=null){
                        if(user.isOldUser()){
                            Toast.makeText(getActivity(),"您好，老顾客，本次您可享受7折优惠",Toast.LENGTH_SHORT).show();
                            textButton.setText("已付款");
                        }
                    }
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initFoodsData(){
        foodsPayList = MessageOfApplication.getInstance().getFoodPayList();
    }
    private void initRecyclerView(){
        //获取RecyclerView
        mFoodRecyclerView=(RecyclerView)view.findViewById(R.id.order_recyclerView);
        //创建adapter
        mFoodRecyclerAdapter = new FoodOrderRecyclerAdapter(getActivity(),foodsPayList);
        //给RecyclerView设置adapter
        mFoodRecyclerView.setAdapter(mFoodRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mFoodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mFoodRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }
}


