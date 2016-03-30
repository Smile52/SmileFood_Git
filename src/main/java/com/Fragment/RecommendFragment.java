package com.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.RecyclerViewAdapter;
import com.Adapter.SpaceItemDecoration;
import com.Bean.Food;
import com.Config.Config;
import com.DB.DbOpenHelper;
import com.DB.DbOpenHelperDao;
import com.Smile.Intent.GetJson;
import com.Smile.Intent.webService;
import com.View.ArcMenu;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.smilefood.MyApplication;
import com.example.smilefood.R;
import com.example.smilefood.ScrollingActivity;

import java.util.ArrayList;
import java.util.List;

/**今日推荐Fragment
 * Created by qq272 on 2015/11/10.
 */
public class RecommendFragment extends android.support.v4.app.Fragment {
    private ArcMenu mArcMenu;
    private TextView title_text;
    private String jsonResult;
    private RequestQueue mRequestQueue;
    private List<Food> mfoodList=new ArrayList<Food>();
    private RecyclerView recyclerView;
    private DbOpenHelper openHelper;
    private List<Food> listfrom;

    private static final String url="http://"+ webService.IP1 + "/SmileFoodServer/servlet/FoodQueryServlet";
    private   RecyclerViewAdapter adapter;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //handler回传过来的List数据
                savaData(GetJson.mList);
                System.out.println("78945+"+GetJson.mList.size());
                for(int i =0;i<GetJson.mList.size();i++){
                    Food food=new Food();
                    food.setFoodId(GetJson.mList.get(i).getFoodId());
                    food.setFoodName(GetJson.mList.get(i).getFoodName());
                    food.setFoodPrice(GetJson.mList.get(i).getFoodPrice());
                    food.setFoodDetail(GetJson.mList.get(i).getFoodDetail());
                    food.setFoodUrl(GetJson.mList.get(i).getFoodUrl());
                    food.setFoodCount(GetJson.mList.get(i).getFoodCount());
                    food.setFoodType(GetJson.mList.get(i).getFoodType());
                    //System.out.println("菜"+food.getFoodName());
                    mfoodList.add(food);

                    if(mfoodList.size()>0){

                        addDatatoview(mfoodList);
                        //清空数据库
                    }
            }
            //savaData(mfoodList);
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recommend=inflater.inflate(R.layout.recommend_fragment,container,false);
        initViews(recommend);
        return recommend;
    }

    /**
     * 初始化控件
     * @param recommend
     */
    private void initViews(View recommend) {
        title_text= (TextView) recommend.findViewById(R.id.id_title_text);
        recyclerView= (RecyclerView) recommend.findViewById(R.id.id_recyclerview);
        title_text.setText("今日推荐");
        GetJson.getJsonArray
                ("http://" + Config.IP + "/SmileFoodServer/servlet/FoodQueryServlet", mHandler, getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.y3);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(layoutManager);



        mRequestQueue= Volley.newRequestQueue(MyApplication.getContextObject());
        //System.out.println("www"+mfoodList.size());
        //mArcMenu= (ArcMenu) view.findViewById(R.id.arcmenu);
        //卫星菜单点击事件
        /*mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //从上往下算的
                switch (position) {
                    case 0:

                    case 1:
                        Log.i("dandy", "点击了");
                }

            }
        });*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public List<Food> getMfoodList(List<Food> list){
        this.mfoodList=list;
        System.out.println("www" + mfoodList.size());
        return mfoodList;
    }
    public void addDatatoview(final List<Food> list){

        
        //deleteData();

        //queryData();
        RecyclerViewAdapter viewAdapter=new RecyclerViewAdapter(list);
        recyclerView.setAdapter(viewAdapter);
        viewAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                Food food = list.get(position);
                intent.putExtra("food", food);
                startActivity(intent);
                //Toast.makeText(getContext(),"这个"+list.get(position).getFoodName(),Toast.LENGTH_SHORT).show();
            }

            //长按点击事件
            @Override
            public void onItemClick(View view, int position) {
                Food orderFood = new Food();
                orderFood.setFoodId(list.get(position).getFoodId());
                orderFood.setFoodName(list.get(position).getFoodName());
                orderFood.setFoodPrice(list.get(position).getFoodPrice());
                orderFood.setFoodUrl(list.get(position).getFoodUrl());
                Intent intent = new Intent(ShoppingFragment.BCAST);//用广播传数据到购物车
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderFood);
                intent.putExtra("o", bundle);
                getActivity().sendBroadcast(intent);
                Toast.makeText(getContext(), "已将" + list.get(position).getFoodName() + "加入购物车", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 将数据存储到sqlite数据库中
     * @param list
     */
    private void savaData(List<Food> list){

        openHelper=DbOpenHelper.getInstance(getContext());
        DbOpenHelperDao dao=new DbOpenHelperDao(openHelper);


        listfrom=dao.query();
        System.out.println("取出来的大小"+listfrom.size());
        if (listfrom.size()>0){
            deleteData();
        }
        dao.insert(list);
    }

    private void  deleteData(){
        openHelper=DbOpenHelper.getInstance(getContext());
        DbOpenHelperDao dao=new DbOpenHelperDao(openHelper);
        dao.delete();
    }
    private List<Food> queryData(){
        openHelper=DbOpenHelper.getInstance(getContext());
        DbOpenHelperDao dao=new DbOpenHelperDao(openHelper);
        listfrom=dao.query();
        System.out.println("取出来的大小"+listfrom.size());
        return listfrom;
    }

}
