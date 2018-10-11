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

import es.source.code.activity.MessageOfApplication;
import es.source.code.activity.R;
import es.source.code.adapter.FoodOrderRecyclerAdapter;
import es.source.code.adapter.FoodRecyclerAdapter;
import es.source.code.model.Food;


public class notOrderedFragment extends Fragment {

    private View view;//定义view用来设置fragment的layout
    public ArrayList<Food> foodsOrderList = new ArrayList<>();
    public RecyclerView mFoodRecyclerView;//定义RecyclerView
    private FoodOrderRecyclerAdapter mFoodRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ordered, container, false);
        initFoodsData();
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    private void initFoodsData(){
        foodsOrderList = MessageOfApplication.getInstance().getOrderFoodList();
    }

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
