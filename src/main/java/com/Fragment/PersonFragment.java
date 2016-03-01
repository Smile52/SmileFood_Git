package com.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smilefood.R;

/**个人信息Fragment
 * Created by qq272 on 2015/11/10.
 */
public class PersonFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View person_view=inflater.inflate(R.layout.person_fragment,container,false);
        TextView title_text= (TextView) person_view.findViewById(R.id.id_title_text);
        title_text.setText("小贴心");


        return  person_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
