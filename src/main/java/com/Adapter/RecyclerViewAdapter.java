package com.Adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.Bean.Food;
import com.Cache.BitmapCache;
import com.ImageUtils.MyBitmapUtils;
import com.android.volley.toolbox.ImageLoader;
import com.example.smilefood.MyApplication;
import com.example.smilefood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 九龙 on 2015/11/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Food> mFoodList=new ArrayList<Food>();
    public  RecyclerViewAdapter(List<Food> mfoodList){
        this.mFoodList=mfoodList;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickLitener mOnItemClickLitener;
    //private AdapterView.OnItemLongClickListener
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(),R.layout.food_item,null);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.foodName_tv.setText(mFoodList.get(position).getFoodName());
        holder.foodCount_tv.setText("已预定数量"+String.valueOf(mFoodList.get(position).getFoodCount()));
        holder.foodPrice_tv.setText("¥："+String.valueOf(mFoodList.get(position).getFoodPrice()));

       /* ImageLoader loader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
        ImageLoader.ImageListener listener=ImageLoader.getImageListener
                (holder.foodImg_img,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        loader.get(mFoodList.get(position).getFoodUrl(), listener);*/
        new MyBitmapUtils().display(holder.foodImg_img,mFoodList.get(position).getFoodUrl());
        System.out.println(mFoodList.get(position).getFoodUrl());
        //自己写接口，写点击事件和长按事件
        if(mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView foodName_tv;
        TextView foodCount_tv;
        TextView foodPrice_tv;
        ImageView foodImg_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            foodCount_tv= (TextView) itemView.findViewById(R.id.foodcount_tv);
            foodName_tv= (TextView) itemView.findViewById(R.id.foodname_tv);
            foodPrice_tv= (TextView) itemView.findViewById(R.id.foodprice_tv);
            foodImg_img= (ImageView) itemView.findViewById(R.id.foodimg_img);
        }
    }


}
