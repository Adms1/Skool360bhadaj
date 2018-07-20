package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Adapter.menuoptionItemAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.DeleteDeviceDetailAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetUserProfileAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.InsertStudentLeaveAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Fragments.AnnouncmentFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.AttendanceFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.CircularFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ClassworkFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ExamSyllabusFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.FeesFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.GalleryFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.HolidayFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.HomeFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.HomeworkFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ImprestFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.NotificationFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ProfileFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ReportCardFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ResultFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.ShowLeaveFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.SuggestionFragment;
import com.anandniketanbhadaj.skool360.skool360.Fragments.TimeTableFragment;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360.skool360.Models.StudProfileModel;
import com.anandniketanbhadaj.skool360.skool360.Models.menuoptionItem;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class DashBoardActivity extends FragmentActivity {
    public static String filename = "Valustoringfile";
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    static RelativeLayout leftRl;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    ImageView viewprofile_img;
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;
    CreateLeaveModel logoutResponse;
    private ArrayList<menuoptionItem> navDrawerItems_main;
    private menuoptionItemAdapter adapter_menu_item;
    private String putData = "0";
    private DeleteDeviceDetailAsyncTask deleteDeviceDetailAsyncTask = null;
    private ProgressDialog progressDialog = null;
    private GetUserProfileAsyncTask getUserProfileAsyncTask = null;
    private ArrayList<StudProfileModel> studDetailList = new ArrayList<>();
    private ImageLoader imageLoader;
    private CircleImageView profile_image;
    private TextView studName, grade, grno;


    public static void onLeft() {
        // TODO Auto-generated method stub
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
    }

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
        AppConfiguration.firsttimeback = true;

//        Log.d("Data123", Utility.getPref(mContext, "data"));
//        Log.d("messagekill", Utility.getPref(mContext, "message"));


//        displayView(dispPOS);
//        String fromWhere = getIntent().getStringExtra("fromNotification");

        /*if(fromWhere.equalsIgnoreCase("android.intent.action.MAIN")){
            displayView(0);

        }else {
            displayView(Integer.parseInt(fromWhere));
        }*/

//        Log.d("Dashboard : fromNotification", fromWhere);
//        onNewIntent(getIntent());

//        Log.d("Data",Utility.getPref(mContext,"data"));
//        Log.d("message",Utility.getPref(mContext,"message"));

        if (getIntent().getStringExtra("message") != null) {
            putData = getIntent().getStringExtra("message").toString();
            Log.d("Dashboard : notificationData", putData);
        }
        if (getIntent().getStringExtra("fromNotification") != null) {
            AppConfiguration.Notification = "1";
            String key = getIntent().getStringExtra("fromNotification").toString();
            Log.d("key", key);
            if (key.equalsIgnoreCase("HW")) {
                displayView(4);
            } else if (key.equalsIgnoreCase("CW")) {
                displayView(5);
            } else if (key.equalsIgnoreCase("Attendance")) {
                displayView(3);
            } else if (key.equalsIgnoreCase("Announcement")) {
                displayView(2);
            } else if (key.equalsIgnoreCase("Circular")) {
                displayView(13);
            }
        } else {
            AppConfiguration.Notification = "0";
            displayView(0);
        }
//        if (!AppConfiguration.dataNOtification.equalsIgnoreCase("")) {
//            Log.d("dataNotification", AppConfiguration.dataNOtification);
//            Log.d("dataMessage", AppConfiguration.messageNotification);
//        }

//        if (Utility.getPref(mContext, "data") != null) {
//            AppConfiguration.Notification = "1";
//            if (!Utility.getPref(mContext, "data").equalsIgnoreCase("")) {
//                //   String[] split = Utility.getPref(mContext, "data").split("\\=");
//                String key = Utility.getPref(mContext, "data");//.substring(0, split[1].length() - 1);
//                Log.d("key", key);
//                if (key.equalsIgnoreCase("Homework")) {
//                    key = "HW";
//                }
//                if (key.equalsIgnoreCase("Classwork")) {
//                    key = "CW";
//                }
//                if (key.equalsIgnoreCase("HW")) {
//                    displayView(4);
//                } else if (key.equalsIgnoreCase("CW")) {
//                    displayView(5);
//                } else if (key.equalsIgnoreCase("Attendance")) {
//                    displayView(3);
//                } else if (key.equalsIgnoreCase("Announcement")) {
//                    displayView(2);
//                } else if (key.equalsIgnoreCase("Circular")) {
//                    displayView(13);
//                }
//                Utility.setPref(mContext, "data", "");
//                Utility.setPref(mContext, "message", "");
//                Log.d("Data123", Utility.getPref(mContext, "data"));
//                Log.d("message123", Utility.getPref(mContext, "message"));
//            } else {
//                AppConfiguration.Notification = "0";
//                displayView(0);
//            }

//        } else {
//            AppConfiguration.Notification = "0";
//            displayView(0);
//        }


        System.out.println("Dashboard Message :" + getIntent().getStringExtra("message"));
        System.out.println("Dashboard Extra : " + getIntent().getStringExtra("fromNotification"));

    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);
        viewprofile_img = (ImageView) findViewById(R.id.viewprofile_img);
        studName = (TextView) findViewById(R.id.studName);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        grade = (TextView) findViewById(R.id.grade);
        //grno = (TextView) findViewById(R.id.grno);

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

        viewprofile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(1);
            }
        });

        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .threadPriority(Thread.MAX_PRIORITY)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();
        imageLoader.init(config.createDefault(mContext));

        getUserProfile();
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

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message").toString();
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                }
                AppConfiguration.position = 0;
                break;
            case 1:
                fragment = new ProfileFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 1;
                break;
            case 2:
                fragment = new AnnouncmentFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 2;
                break;
            case 3:
                fragment = new AttendanceFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 3;
                break;
            case 4:
                fragment = new HomeworkFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message").toString();
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                }
                AppConfiguration.position = 4;
                break;
            case 5:
                fragment = new ClassworkFragment();
                if (getIntent().getStringExtra("message") != null) {
                    putData = getIntent().getStringExtra("message").toString();
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                } else {
                    putData = "test";
                    Bundle args = new Bundle();
                    args.putString("message", putData);
                    fragment.setArguments(args);
                    myid = fragment.getId();
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                }
                break;
            case 6:
                fragment = new TimeTableFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 6;
                break;
            case 7:
                fragment = new ExamSyllabusFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 7;
                break;
            case 8:
                fragment = new ResultFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 8;
                break;
            case 9:
                fragment = new ReportCardFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 9;
                break;
            case 10:
                fragment = new FeesFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 10;
                break;
//            case 11:
//                fragment = new ImprestFragment();
//                myid = fragment.getId();
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                AppConfiguration.firsttimeback = true;
//                break;
            case 11:
                fragment = new HolidayFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 11;
                break;
            case 12:
                fragment = new ShowLeaveFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 12;
                break;
            case 13:
                fragment = new CircularFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 13;
                break;
            case 14:
                fragment = new GalleryFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 14;
                break;
            case 15:
                AppConfiguration.firsttimeback = true;
                fragment = new SuggestionFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.position = 15;
                break;
            case 16:
                AppConfiguration.position = 16;
                new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AppTheme))
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getDeleteDeviceData();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing

                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
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
        if (AppConfiguration.firsttimeback) {
            if (AppConfiguration.position != 0) {
                displayView(0);
            }
            AppConfiguration.firsttimeback = false;
            Utility.ping(mContext, "Press again to exit");
        } else {
            finish();
            System.exit(0);
        }
    }

    public void getDeleteDeviceData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("DeviceID", Utility.getPref(mContext, "deviceId"));
                        deleteDeviceDetailAsyncTask = new DeleteDeviceDetailAsyncTask(params);
                        logoutResponse = deleteDeviceDetailAsyncTask.execute().get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (logoutResponse.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.setPref(mContext, "unm", "");
                                    Utility.setPref(mContext, "pwd", "");
                                    Utility.setPref(mContext, "studid", "");
                                    Utility.setPref(mContext, "FamilyID", "");
                                    Utility.setPref(mContext, "standardID", "");
                                    Utility.setPref(mContext, "ClassID", "");
                                    Utility.setPref(mContext, "TermID", "");
                                    Utility.setPref(mContext, "deviceId", "");
                                    Intent intentLogin = new Intent(DashBoardActivity.this, LoginActivity.class);
                                    startActivity(intentLogin);
                                    finish();
                                } else {
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void getUserProfile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("StudentID", Utility.getPref(mContext, "studid"));
                    getUserProfileAsyncTask = new GetUserProfileAsyncTask(params);
                    studDetailList = getUserProfileAsyncTask.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (studDetailList.size() > 0) {
                                imageLoader.displayImage(studDetailList.get(0).getStudentImage(), profile_image);
                                studName.setText(studDetailList.get(0).getStudentName() + " (" + studDetailList.get(0).getGRNO() + ")");
                                grade.setText(studDetailList.get(0).getStandard() + "-" + studDetailList.get(0).getStudClass());
                                // grno.setText("GRNo :" + " " + studDetailList.get(0).getGRNO());

                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

}
