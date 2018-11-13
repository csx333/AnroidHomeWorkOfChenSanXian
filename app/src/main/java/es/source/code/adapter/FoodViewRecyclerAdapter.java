package es.source.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import es.source.code.activity.R;
import es.source.code.application.MessageOfApplication;
import es.source.code.model.Food;

public class FoodViewRecyclerAdapter extends RecyclerView.Adapter<FoodViewRecyclerAdapter.myViewHolder> {
    //自定义viewholder
    public class myViewHolder extends RecyclerView.ViewHolder {
        public ImageView mItemFoodsImg;
        public TextView mItemFoodsName;
        public TextView mItemFoodsPrice;
        public TextView mItemFoodsOrder;
        public TextView mItemFoodsNumber;
        public View foodView;

        public myViewHolder(View itemView) {
            super(itemView);
            mItemFoodsImg = (ImageView) itemView.findViewById(R.id.iv_food);
            mItemFoodsName = (TextView) itemView.findViewById(R.id.tv_food_name);
            mItemFoodsPrice = (TextView) itemView.findViewById(R.id.tv_food_price);
            mItemFoodsOrder = (TextView) itemView.findViewById(R.id.btn_order);
            mItemFoodsNumber = (TextView) itemView.findViewById(R.id.tv_food_number);
            foodView=itemView;
        }
    }
    //回调接口
    public interface OnItemClickListener{
        public void OnItemClick(ArrayList<Food> foodsList,int position);
        public void OnItemButtonClick(View view,int position);
    }

    private Context context;
    private ArrayList<Food> foodsList;
    private OnItemClickListener onItemClickListener;
    private int styleOfFood;

    //创建构造函数
    public FoodViewRecyclerAdapter(Context context, ArrayList<Food> appFoodsList,int styleFood) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.styleOfFood= styleFood;
        this.foodsList = initFoodsData(appFoodsList);//实体类数据ArrayList
    }
    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_food_item,parent,false);
        final myViewHolder holder = new myViewHolder(view);
        return  holder;
    }
    /**
     * 绑定数据，数据与view绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        //根据点击位置绑定数据
        Food data = foodsList.get(position);
        holder.mItemFoodsImg.setImageResource(data.getImgId());
        holder.mItemFoodsName.setText(data.getFoodName());//获取实体类中的name字段并设置
        holder.mItemFoodsPrice.setText(String.valueOf(data.getPrice()));//获取实体类中的price字段并设置
        holder.mItemFoodsOrder.setText(data.getOrder());
        holder.mItemFoodsNumber.setText(String.valueOf(data.getStore()));

        LinearLayout ll = holder.foodView.findViewById(R.id.ll_food_item);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(foodsList, position);
                    notifyDataSetChanged();
                }
            }
        });
        holder.mItemFoodsOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemButtonClick(v, position);
                    notifyDataSetChanged();
            }
            }
        });
    }
    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    private ArrayList<Food> initFoodsData(ArrayList<Food> appFoodList){
        ArrayList<Food> foodsList2 = new ArrayList<>();
        for(Food item: appFoodList){
            if(item.getStyle() == styleOfFood){
                foodsList2.add(item);
            }
        }
        return foodsList2;
    }
}
