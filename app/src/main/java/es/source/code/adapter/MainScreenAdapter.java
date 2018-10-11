package es.source.code.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;

import es.source.code.activity.R;
import es.source.code.model.iconOfMainScreen;

public class MainScreenAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private ArrayList<iconOfMainScreen> mData;
    private Context mContext;           //布局id
    private  int resourceId;

    public MainScreenAdapter(Context mcontext,int id, ArrayList<iconOfMainScreen> mData) {
        super(mcontext,id,mData);
        this.mData = mData;
        this.mContext = mContext;
        this.resourceId= id;
        inflater = LayoutInflater.from(getContext());//直接传会报错
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mData ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(resourceId,null);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_icon.setImageResource(mData.get(position).getImageId());
        holder.txt_content.setText(mData.get(position).getIName());
        return convertView;
    }

    //添加一个元素
    public void add(iconOfMainScreen data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,iconOfMainScreen data){
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(iconOfMainScreen data) {
        if(mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if(mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if(mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView img_icon;
        TextView txt_content;
    }

}
