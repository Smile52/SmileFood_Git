package com.Fragment;

import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.OrderAdapter;
import com.Bean.Food;
import com.View.ZhifuDialog;
import com.example.smilefood.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**购物车Fragment
 * Created by qq272 on 2015/11/10.
 */
public class ShoppingFragment extends android.support.v4.app.Fragment {
    public final static String BCAST="com.smile.bcast";//广播
    private List<Food> orderList=new ArrayList<Food>();
    private RecyclerView recyclerView;
    private TextView price_tv;
    private Button pay_btn;
    private ProgressDialog dialog;
    Double price=0.0;
    private Map<Integer,Double> foodMap=new HashMap<Integer, Double>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View shopping_view=inflater.inflate(R.layout.shopping_fragment,container,false);
        initviews(shopping_view);
        initRecyclerView();
        return  shopping_view;

    }

    /**
     * 初始化布局
     * @param shopping_view
     */
    private void initviews(View shopping_view) {
        recyclerView= (RecyclerView) shopping_view.findViewById(R.id.id_recyclerview_shopping);
        price_tv= (TextView) shopping_view.findViewById(R.id.price_tv);
        pay_btn= (Button) shopping_view.findViewById(R.id.pay_btn);
        TextView title_text= (TextView) shopping_view.findViewById(R.id.id_title_text);
        title_text.setText("购物车");

        dialog = null;
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderList.size()==0){
                    Toast.makeText(getContext(),"当前购物车没有菜，请先添加菜",Toast.LENGTH_SHORT).show();
                }else {
                    ZhifuDialog dialog=new ZhifuDialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉提示框标题
                    dialog.show();

                }
            }
        });

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //广播来接收回传过来的数据
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BCAST);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("o");
                Food food = (Food) bundle.getSerializable("order");
                orderList.add(food);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                OrderAdapter adapter = new OrderAdapter(orderList);
                price=0.0;
                for(Food food1:orderList){
                    price+=food1.getFoodPrice();
                }

                price_tv.setText(price+"¥");


                recyclerView.setAdapter(adapter);
                //System.out.println("点的菜" + food.getFoodName());
                //foodMap.put(food.getFoodId(), food.getFoodPrice());
                //getPrice(foodMap);
              /*  Message msg = new Message();
                msg.what = 0x1234;
                mHandler.sendMessage(msg);
*/
            }
        }, intentFilter);

    }
    private void initRecyclerView(){
        System.out.println("点菜的" + orderList.size());

    }
    private Double getPrice(Map<Integer,Double> map){
        Double pricecount = null;
        for (Double price:map.values()){
            pricecount+=price;
        }
        return pricecount;
    }
}

