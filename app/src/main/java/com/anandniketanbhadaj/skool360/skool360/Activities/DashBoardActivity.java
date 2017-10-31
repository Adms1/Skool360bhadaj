package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Adapter.menuoptionItemAdapter;
import com.anandniketanbhadaj.skool360.skool360.Fragments.AppointmentFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.AttendanceFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.CanteenFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ClassworkFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.CourseFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.FeesFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.HomeFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.HomeworkFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ImprestFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.NotificationFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.PTMMainFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.PrincipalMessageFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ProfileFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ReportCardFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ResultFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.TimeTableFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.UnitTestFragment;
import com.anandniketanbhadaj.skool360.skool360.Models.menuoptionItem;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class DashBoardActivity extends FragmentActivity {
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    static RelativeLayout leftRl;
    private ArrayList<menuoptionItem> navDrawerItems_main;
    private menuoptionItemAdapter adapter_menu_item;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    public static String filename = "Valustoringfile";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String putData = "0";

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mContext = this;
        Initialize();
        dispPOS = getIntent().getIntExtra("POS", 0);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        displayView(dispPOS);
//        String fromWhere = getIntent().getStringExtra("fromNotification");

        /*if(fromWhere.equalsIgnoreCase("android.intent.action.MAIN")){
            displayView(0);

        }else {
            displayView(Integer.parseInt(fromWhere));
        }*/

//        Log.d("Dashboard : fromNotification", fromWhere);
//        onNewIntent(getIntent());
        if (getIntent().getStringExtra("message") != null) {
            putData = getIntent().getStringExtra("message").toString();
            Log.d("Dashboard : notificationData", putData);
        }
        if (getIntent().getStringExtra("fromNotification") != null) {
            String key = getIntent().getStringExtra("fromNotification").toString();
            Log.d("key", key);
            if (key.equalsIgnoreCase("HW")) {
                displayView(3);
            } else if (key.equalsIgnoreCase("CW")) {
                displayView(4);
            } else if (key.equalsIgnoreCase("Attendance")) {
                displayView(2);
            } else if (key.equalsIgnoreCase("Announcement")) {
                Intent is = new Intent(DashBoardActivity.this, NotificationFragment.class);
                is.putExtra("message", putData);
                startActivity(is);
            }
        } else {
            displayView(0);
        }
        System.out.println("Dashboard Message :" + getIntent().getStringExtra("message"));
        System.out.println("Dashboard Extra : " + getIntent().getStringExtra("fromNotification"));

    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftRl = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems_main = new ArrayList<menuoptionItem>();
        adapter_menu_item = new menuoptionItemAdapter(DashBoardActivity.this, navDrawerItems_main);
        for (int i = 0; i < MenuName.length; i++) {
            navDrawerItems_main.add(new menuoptionItem(MenuName[i]));
        }
        mDrawerList.setAdapter(adapter_menu_item);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;

    public void displayView(int position) {

                /*  case 4:
                fragment = new ClassworkFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
                 case 6:
                fragment = new EventFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
                  case 9:
                fragment = new AnnouncementFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 10:
                fragment = new CircularFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
*/
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 1:
                fragment = new ProfileFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 2:
                fragment = new AttendanceFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
//            case 3:
//                fragment=new CourseFragment();
//                myid=fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                break;
            case 3:
                fragment = new HomeworkFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 4:
                fragment = new ClassworkFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 5:
                fragment = new TimeTableFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 6:
                fragment = new UnitTestFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 7:
                fragment = new ResultFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 8:
                fragment = new ReportCardFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 9:
                fragment = new FeesFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 10:
                fragment = new ImprestFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 11:
                fragment = new CanteenFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 12:
                fragment = new PTMMainFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 13:
                fragment = new PrincipalMessageFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 14:
                Utility.setPref(mContext, "unm", "");
                Utility.setPref(mContext, "pwd", "");
                Utility.setPref(mContext, "studid", "");
                Utility.setPref(mContext, "FamilyID", "");
                Utility.setPref(mContext, "standardID", "");
                Utility.setPref(mContext, "ClassID", "");
                Intent intentLogin = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    public static void onLeft() {
        // TODO Auto-generated method stub
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        displayView(0);
    }
}
