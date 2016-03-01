package com.Fragment;

import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.OrderAdapter;
import com.Bean.Food;
import com.Bean.FoodId;
import com.Config.Config;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smilefood.MyApplication;
import com.example.smilefood.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Double price=0.0;
    private Map<String,Integer> foodMap=new HashMap<String, Integer>();
    private List<FoodId> idList=new ArrayList<FoodId>();
    private View shopping_view;
    private OrderAdapter adapter;
    private String  url="http://"+ Config.IP + "/SmileFoodServer/servlet/OrderFoodServlet";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shopping_view=inflater.inflate(R.layout.shopping_fragment,container,false);
        initviews(shopping_view);
        initRecyclerView();
        return  shopping_view;
    }

    /**
     * 初始化布局
     * @param shopping_view
     */
    private void initviews(final View shopping_view) {
        recyclerView= (RecyclerView) shopping_view.findViewById(R.id.id_recyclerview_shopping);
        price_tv= (TextView) shopping_view.findViewById(R.id.price_tv);
        pay_btn= (Button) shopping_view.findViewById(R.id.pay_btn);
        TextView title_text= (TextView) shopping_view.findViewById(R.id.id_title_text);
        title_text.setText("购物车");

        final Gson gson=new Gson();
        //下单
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderList.size() == 0) {
                    Toast.makeText(getContext(), "当前购物车没有菜，请先添加菜", Toast.LENGTH_SHORT).show();
                } else {

                    for (Food food : orderList) {
                        FoodId id = new FoodId();
                        id.setFoodId(food.getFoodId());
                        idList.add(id);
                    }
                    String jsons = gson.toJson(idList);
                    Log.i("dandy", "json" + jsons);
                    OrderFoodPost(jsons);
                    //清空购物车，刷新页面
                    orderList.clear();
                    adapter.notifyDataSetChanged();
                    price_tv.setVisibility(View.INVISIBLE);
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
                adapter = new OrderAdapter(orderList);
                price = 0.0;
                for (Food food1 : orderList) {
                    price += food1.getFoodPrice();
                }
                price_tv.setVisibility(View.VISIBLE);
                price_tv.setText(price + "¥");


                recyclerView.setAdapter(adapter);


            }
        }, intentFilter);

    }
    private void initRecyclerView(){
        System.out.println("点菜的" + orderList.size());

    }

    /**
     * post提交下单请求
     * @param jsons 参数
     */
    private void OrderFoodPost(final String jsons){
        Log.i("dandy","wocao");


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               Log.i("dandy","你妹啊"+s);
               Toast.makeText(getActivity(),"下单成功",2000).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("dandy","请求错误"+volleyError.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("key","123456");
                map.put("values",jsons);
                return map;
            }

        };
        request.setTag("volleypost");
        MyApplication.getQueue().add(request);
    }

    /**
     * 注销
     */
    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getQueue().cancelAll("volleypost");
    }

}

