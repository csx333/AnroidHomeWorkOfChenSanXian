package es.source.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.MessageOfApplication;
import es.source.code.activity.R;
import es.source.code.model.Food;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.myViewHolder> {

    private Context context;
    private ArrayList<Food> foodsList;

    //创建构造函数
    public FoodRecyclerAdapter(Context context,ArrayList<Food> foodsList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.foodsList = foodsList;//实体类数据ArrayList
    }
    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        //根据点击位置绑定数据
        Food data = foodsList.get(position);
        holder.mItemFoodsImg.setImageResource(data.getImgId());
        holder.mItemFoodsName.setText(data.getFoodName());//获取实体类中的name字段并设置
       holder.mItemFoodsPrice.setText(data.getPrice()+"");//获取实体类中的price字段并设置
        holder.mItemFoodsOrder.setText(data.Order());
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

    //自定义viewholder
     class myViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemFoodsImg;
        TextView mItemFoodsName;
        TextView mItemFoodsPrice;
        TextView mItemFoodsOrder;
        View foodView;

        public myViewHolder(View itemView) {
            super(itemView);
            mItemFoodsImg = (ImageView) itemView.findViewById(R.id.iv_food);
            mItemFoodsName = (TextView) itemView.findViewById(R.id.tv_food_name);
            mItemFoodsPrice = (TextView) itemView.findViewById(R.id.tv_food_price);
            mItemFoodsOrder = (TextView) itemView.findViewById(R.id.btn_order);
            foodView=itemView;
        }
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

        holder.mItemFoodsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = foodsList.get(position);
                Toast.makeText(v.getContext(),"view",Toast.LENGTH_SHORT).show();
            }
        });

        holder.mItemFoodsOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = foodsList.get(position);
                if(food.Order().equals("点餐")) {
                    MessageOfApplication.getInstance().operateFoodOrderList(food,true);
                    Toast.makeText(v.getContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    food.setOrder("退点");
                    notifyDataSetChanged();
                }else{
                    MessageOfApplication.getInstance().operateFoodOrderList(food,false);
                    food.setOrder("点餐");
                    notifyDataSetChanged();
                }
            }
        });
        return  holder;
    }
}
