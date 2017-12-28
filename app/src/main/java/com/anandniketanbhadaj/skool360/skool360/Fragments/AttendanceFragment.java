package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetAttendanceAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.AttendanceModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AttendanceFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnFilterAttendance, btnBackAttendance;
    private TextView txtTotalPresent, txtTotalAbsent, txtNoRecordsHomework;
    private Spinner spinMonth, spinYear;
    private RelativeLayout rlCalender;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private CaldroidFragment mCaldroidFragment = null;
    private GetAttendanceAsyncTask getAttendanceAsyncTask = null;
    private ArrayList<AttendanceModel> attendanceModels = new ArrayList<>();
    private ArrayList<String> absentDates = new ArrayList<>();
    private ArrayList<String> presentDates = new ArrayList<>();
    private ArrayList<String> year1 = new ArrayList<>();

    public AttendanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.attendance_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        Getnextandpreviousyear();
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackAttendance = (Button) rootView.findViewById(R.id.btnBackAttendance);
        btnFilterAttendance = (Button) rootView.findViewById(R.id.btnFilterAttendance);
        txtTotalPresent = (TextView) rootView.findViewById(R.id.txtTotalPresent);
        txtTotalAbsent = (TextView) rootView.findViewById(R.id.txtTotalAbsent);
        txtNoRecordsHomework = (TextView) rootView.findViewById(R.id.txtNoRecordsHomework);
        rlCalender = (RelativeLayout) rootView.findViewById(R.id.rlCalender);
        spinMonth = (Spinner) rootView.findViewById(R.id.spinMonth);

        Collections.sort(year1);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + year1);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinMonth);

            popupWindow.setHeight(getResources().getStringArray(R.array.month).length > 5 ? 500 : getResources().getStringArray(R.array.month).length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, getResources().getStringArray(R.array.month));
        spinMonth.setAdapter(adapterMonth);

        spinYear = (Spinner) rootView.findViewById(R.id.spinYear);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinYear);

            popupWindow.setHeight(year1.size() > 5 ? 500 : year1.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
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

        try {
            mCaldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            args.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, false);


            mCaldroidFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.calFrameContainer, mCaldroidFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public void getAttendance() {
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
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        params.put("Month", String.valueOf(spinMonth.getSelectedItemPosition() + 1));
                        params.put("Year", spinYear.getSelectedItem().toString());
                        getAttendanceAsyncTask = new GetAttendanceAsyncTask(params);
                        attendanceModels = getAttendanceAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                absentDates.clear();
                                if (attendanceModels.size() > 0) {
                                    txtNoRecordsHomework.setVisibility(View.GONE);
                                    rlCalender.setVisibility(View.VISIBLE);
                                    txtTotalPresent.setText(attendanceModels.get(0).getTotalPresent());
                                    txtTotalAbsent.setText(attendanceModels.get(0).getTotalAbsent());
                                    mCaldroidFragment.moveToDate(stringToDate(attendanceModels.get(0).getEventsList().get(0).getAttendanceDate()));
                                    HashMap hm = new HashMap();
                                    for (int i = 0; i < attendanceModels.get(0).getEventsList().size(); i++) {

                                        if (attendanceModels.get(0).getEventsList().get(i).getAttendenceStatus().equalsIgnoreCase("Absent")) {
                                            absentDates.add(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate());
                                            hm.put(stringToDate(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate()), new ColorDrawable(getResources().getColor(R.color.attendance_absent_new)));
                                        } else if (attendanceModels.get(0).getEventsList().get(i).getAttendenceStatus().equalsIgnoreCase("Present")) {
                                            presentDates.add(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate());
                                            hm.put(stringToDate(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate()), new ColorDrawable(getResources().getColor(R.color.attendance_present_new)));
                                        }
                                    }

                                    if (hm.size() > 0) {
                                        mCaldroidFragment.setBackgroundDrawableForDates(hm);
                                    }
                                    mCaldroidFragment.refreshView();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsHomework.setVisibility(View.VISIBLE);
                                    rlCalender.setVisibility(View.GONE);
                                    txtTotalPresent.setText("0");
                                    txtTotalAbsent.setText("0");
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            Utility.ping(mContext,"Network not available");
        }
    }

    public void setListners() {
        mCaldroidFragment.setCaldroidListener(listener);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        btnFilterAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getAttendance();
            }
        });
    }

    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            for (int i = 0; i < attendanceModels.get(0).getEventsList().size(); i++) {
                if (dateToString(date).equalsIgnoreCase(attendanceModels.get(0).getEventsList().get(i).getAttendanceDate())) {
                    String comments = attendanceModels.get(0).getEventsList().get(i).getComment().toString();
                    if (!comments.equalsIgnoreCase("")) {
                        AlertDialog ad = new AlertDialog.Builder(view.getContext()).create();
                        ad.setCancelable(false);
                        ad.setTitle("Comment");
                        ad.setMessage(comments);
                        ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.show();

//                        showAlert();
                        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                        SomeDialog newFragment = new SomeDialog ("Comment", comments);
                        newFragment.show(ft, "dialog");*/
                    }
                }
            }
        }

        @Override
        public void onChangeMonth(int month, int year) {
        }

        @Override
        public void onLongClickDate(Date date, View view) {
        }

        @Override
        public void onCaldroidViewCreated() {
            getAttendance();
        }
    };

    public void showAlert() {
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());

        alertbox.setTitle("Do you want To exit ?");
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // finish used for destroyed activity
                arg0.dismiss();
            }
        });

        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // Nothing will be happened when clicked on no button
                // of Dialog
            }
        });

        alertbox.show();

    }
    public Date stringToDate(String stirngDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(stirngDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String stirngDate = "";
        stirngDate = df.format(date);

        return stirngDate;
    }


}
