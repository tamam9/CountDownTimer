package com.arjinmc.countdowntimer.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.arjinmc.countdowntimer.R;
import com.arjinmc.countdowntimer.database.DataBaseHelper;
import com.arjinmc.countdowntimer.databinding.EditCountActBinding;
import com.arjinmc.countdowntimer.pojo.MussEvent;
import com.arjinmc.countdowntimer.util.Constant;
import com.arjinmc.countdowntimer.util.EventBusEv;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yusuf on 2016/8/11.
 */
public class AddCountAct extends FragmentActivity {

    EditCountActBinding binding;
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.edit_count_act);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        binding.title.setText("ADD");

    }

    @OnClick(R.id.add_event)
    public void setAddEvent(View view) {
        String name = binding.name.getText().toString();
        String limit = binding.limit.getText().toString();
        if (name.isEmpty() || limit.isEmpty()) {
            Toast.makeText(this, "please fill blanket", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseHelper.addEvent(new MussEvent(name, Integer.valueOf(limit)));
            Toast.makeText(this, "add data success", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onBackPressed() {
        addCountActBack();
        super.onBackPressed();
    }

    public void addCountActBack() {
        EventBus.getDefault().post(new EventBusEv(Constant.REFRESH_MAIN_RECYCLERVIEW, null));
        finish();
    }

}
