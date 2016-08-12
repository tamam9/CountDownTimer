package com.arjinmc.countdowntimer.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.arjinmc.countdowntimer.activity.AddCountAct;
import com.arjinmc.countdowntimer.activity.CountAct;
import com.arjinmc.countdowntimer.activity.MainActivity;

/**
 * Created by Yusuf on 2016/8/11.
 */
public class ActivitySwitcher {


    public static void countAct(FragmentActivity activity) {
        Intent intent = new Intent(activity, CountAct.class);
        activity.startActivity(intent);

    }

    public static void addCount(MainActivity mainActivity) {
        Intent intent = new Intent(mainActivity, AddCountAct.class);
        mainActivity.startActivity(intent);
    }

    public static void editCount(CountAct editCountAct) {
        Intent intent = new Intent(editCountAct, EditCountAct.class);
        editCountAct.startActivity(intent);
    }
}
