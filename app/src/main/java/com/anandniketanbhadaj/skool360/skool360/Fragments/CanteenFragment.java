package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Adapter.CanteenListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.CanteenAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.CanteenModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class CanteenFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnFilterCanteen, btnBackCanteen;
    private Spinner spinMonth, spinYear;
    private TextView txtNoRecordsClasswork;
    private ListView listCanteen;
    private Context mContext;
    private static boolean fromStartDate = false;
    private String fromDate, toDate;
    private CanteenAsyncTask canteenAsyncTask = null;
    private CanteenListAdapter canteenListAdapter = null;
    private ArrayList<CanteenModel> canteenModels = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private ArrayList<String> year1 = new ArrayList<>();

    public CanteenFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.canteen_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        Getnextandpreviousyear();
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnFilterCanteen = (Button) rootView.findViewById(R.id.btnFilterCanteen);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        btnBackCanteen = (Button) rootView.findViewById(R.id.btnBackCanteen);
        listCanteen = (ListView) rootView.findViewById(R.id.listCanteen);

        spinMonth = (Spinner) rootView.findViewById(R.id.spinMonth);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(mContext,R.layout.spinner_layout, getResources().getStringArray(R.array.month));
        spinMonth.setAdapter(adapterMonth);

        spinYear = (Spinner) rootView.findViewById(R.id.spinYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext,R.layout.spinner_layout, year1);
        spinYear.setAdapter(adapterYear);

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<String> months = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.month).length; i++) {
            months.add(getResources().getStringArray(R.array.month)[i]);
        }
        spinMonth.setSelection(months.indexOf(months.get(mm - 1)));

        ArrayList<String> year2 = new ArrayList<>();
        for (int i = 0; i < year1.size(); i++) {
            year2.add(year1.get(i));
        }
        spinYear.setSelection(year2.indexOf(String.valueOf(yy)));

        fromDate = "1" + "/" + mm + "/" + yy;
        toDate = getToDate(fromDate);

        getCanteenData(fromDate, toDate);//send blank first time
    }

    public void Getnextandpreviousyear() {
        final Calendar calendar = Calendar.getInstance();
        int currentyear = calendar.get(Calendar.YEAR);
        int nextyear = calendar.get(Calendar.YEAR) + 1;
        int previousyear = calendar.get(Calendar.YEAR) - 1;
        year1.add(String.valueOf(currentyear));
        year1.add(String.valueOf(previousyear));
        year1.add(String.valueOf(nextyear));
    }

    public void setListners() {

        btnFilterCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate = "1/" + (spinMonth.getSelectedItemPosition() + 1) + "/" + spinYear.getSelectedItem();
                toDate = getToDate(fromDate);

                getCanteenData(fromDate, toDate);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackCanteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    public void getCanteenData(final String startDate, final String endDate) {
        if(Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StartDate", startDate);
                        params.put("EndDate", endDate);
                        params.put("Standard", Utility.getPref(mContext, "standardID"));

                        canteenAsyncTask = new CanteenAsyncTask(params);
                        canteenModels = canteenAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();

                                if (canteenModels.size() > 0) {
                                    txtNoRecordsClasswork.setVisibility(View.GONE);
                                    listCanteen.setVisibility(View.VISIBLE);

                                    canteenListAdapter = new CanteenListAdapter(mContext, canteenModels);
                                    listCanteen.setAdapter(canteenListAdapter);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsClasswork.setVisibility(View.VISIBLE);
                                    listCanteen.setVisibility(View.GONE);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            Utility.ping(mContext,"Network not available");
        }
    }

    public String getToDate(String fromDate) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(fromDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);  // number of days to add
        c.add(Calendar.DATE, -1);
        Date todate = c.getTime();

        return format.format(todate);
    }
}
