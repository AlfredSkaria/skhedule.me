package com.alfred.karela.schedule;

/**
 * Created by Alfred on 11-02-2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter{


    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Calendartab tab1 = new Calendartab();
                return tab1;
            case 1:
                RecyclerView_Activity tab2 = new RecyclerView_Activity();
                return tab2;
            case 2:
                Notification_Activity tab3 = new Notification_Activity();
                return tab3;
            case 3:
                Profile tab4 = new Profile();
                return tab4;
            default:
                return null;
        }
    }

    public int getCount() {
        return tabCount;
    }
}

