package com.alfred.karela.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionsMenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alfred.karela.schedule.R.id.tabs;


public class MainActivity extends AppCompatActivity {

    public static View.OnClickListener myOnClickListener;
    private TabHost tabHost;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button button;



   private int[] tabicons={R.drawable.calendar,R.drawable.events,R.drawable.notifications,R.drawable.profile,R.drawable.calendar1,R.drawable.events1,R.drawable.notifications1,R.drawable.profile1};
    private View bckgroundDimmer;
    private FloatingActionsMenu floatingActionsMenu;
    private com.getbase.floatingactionbutton.FloatingActionButton fab1;
    private com.getbase.floatingactionbutton.FloatingActionButton fab2;
    private com.getbase.floatingactionbutton.FloatingActionButton fab3;
    private com.getbase.floatingactionbutton.FloatingActionButton fab4;
    private Button btn;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFloatingButtonControls();
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        /**
                  * Call this function whenever you want to check user login
                  * This will redirect user to LoginActivity is he is not
                  * logged in
                  * */
        if(session.checkLogin()) {
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);



     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(tabs);
          tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

     fab1 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_private);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(MainActivity.this,PrivateEvent.class);
                startActivity(i);
               }
            });
        // Locate the button in activity_main.xml


        fab2 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_public);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PublicEvent.class);
                startActivity(i);
            }
        });


        fab3 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_polled);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PolledEvent.class);
                startActivity(i);
            }
        });


        fab4 = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_reminders);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Remainders.class);
                startActivity(i);
            }
        });

/*
        btn = (Button) findViewById(R.id.b1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Location1.class);
                startActivity(i);
            }
        });
*/
        }

    }




    private void setFloatingButtonControls(){
        this.bckgroundDimmer = findViewById(R.id.background_dimmer);
        this.floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        this.floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                bckgroundDimmer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                bckgroundDimmer.setVisibility(View.GONE);
            }
        });
    }

    /* public static void setTabColor(TabHost tabhost) {

         for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
             tabhost.getTabWidget().getChildAt(i)
                     .setBackgroundResource(R.color.green); // unselected
         }
         tabhost.getTabWidget().setCurrentTab(0);
         tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                 .setBackgroundResource(R.color.blue); // selected
         // //have
         // to
         // change
     }*/
    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(tabicons[0]);
        tabLayout.getTabAt(1).setIcon(tabicons[1]);
        tabLayout.getTabAt(2).setIcon(tabicons[2]);
        tabLayout.getTabAt(3).setIcon(tabicons[3]);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tabLayout.getSelectedTabPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(tabicons[0]);
                        tabLayout.getTabAt(1).setIcon(tabicons[1]);
                        tabLayout.getTabAt(2).setIcon(tabicons[2]);
                        tabLayout.getTabAt(3).setIcon(tabicons[3]);
                    case 1:
                        tabLayout.getTabAt(0).setIcon(tabicons[0]);
                        tabLayout.getTabAt(1).setIcon(tabicons[1]);
                        tabLayout.getTabAt(2).setIcon(tabicons[2]);
                        tabLayout.getTabAt(3).setIcon(tabicons[3]);
                    case 2:
                        tabLayout.getTabAt(0).setIcon(tabicons[0]);
                        tabLayout.getTabAt(1).setIcon(tabicons[1]);
                        tabLayout.getTabAt(2).setIcon(tabicons[2]);
                        tabLayout.getTabAt(3).setIcon(tabicons[3]);
                    case 3:
                        tabLayout.getTabAt(0).setIcon(tabicons[0]);
                        tabLayout.getTabAt(1).setIcon(tabicons[1]);
                        tabLayout.getTabAt(2).setIcon(tabicons[2]);
                        tabLayout.getTabAt(3).setIcon(tabicons[3]);
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Calendartab(), "");
        adapter.addFragment(new RecyclerView_Activity(), "");
        adapter.addFragment(new Notification_Activity(), "");
        adapter.addFragment(new Profile(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.logout)
        {
            session.logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }





}
