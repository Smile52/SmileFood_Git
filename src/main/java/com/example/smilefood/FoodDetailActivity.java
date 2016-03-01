package com.example.smilefood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.Bean.Food;
import com.Fragment.ShoppingFragment;

/**购物车Fragment
 * Created by qq272 on 2015/11/10.
 */
public class FoodDetailActivity extends AppCompatActivity {
    Food food;
    private  TextView title_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);}
        setContentView(R.layout.activity_food_detail);
        title_text= (TextView) findViewById(R.id.id_title_text);

        Intent intent=getIntent();
        food= (Food) intent.getSerializableExtra("food");
        System.out.println("菜名" + food.getFoodName());
        title_text.setText(food.getFoodName());
    }
}
