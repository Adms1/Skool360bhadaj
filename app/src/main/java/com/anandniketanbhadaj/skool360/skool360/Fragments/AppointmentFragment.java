package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.AppointmentAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AppointmentFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackAppointment, btnSave, btnCancel;
    private static TextView txtDate;
    private EditText edtPurpose, edtDescription;
    private Spinner spinRequestFor;
    private Context mContext;
    private AppointmentAsyncTask appointmentAsyncTask = null;
    private ProgressDialog progressDialog = null;

    public AppointmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.appointment_fragment, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackAppointment = (Button) rootView.findViewById(R.id.btnBackAppointment);
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        edtPurpose = (EditText) rootView.findViewById(R.id.edtPurpose);
        spinRequestFor = (Spinner) rootView.findViewById(R.id.spinRequestFor);
        ArrayAdapter<String> adapterReqFor = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.appoi_personnel));
        spinRequestFor.setAdapter(adapterReqFor);
        spinRequestFor.setSelection(0);

        edtDescription = (EditText) rootView.findViewById(R.id.edtDescription);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);

    }

    public void setListners() {

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtDate.length() > 0) {
                    if (edtPurpose.length() > 0) {
                        if (edtDescription.length() > 0) {
                            sendAppointmentRequest();
                        } else {
                            Utility.ping(mContext, "Please describe the purpose of appointment");
                        }
                    } else {
                        Utility.ping(mContext, "Please specify the purpose of appointment");
                    }
                } else {
                    Utility.ping(mContext, "Please enter a date of appointment");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate.setText("");
                edtPurpose.setText("");
                spinRequestFor.setSelection(0);
                edtDescription.setText("");
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });

        btnBackAppointment.setOnClickListener(new View.OnClickListener() {
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

    public void sendAppointmentRequest() {
        if(Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("StudentID", Utility.getPref(mContext, "studid"));
                        hashMap.put("AppointmentDate", txtDate.getText().toString());
                        hashMap.put("Purpose", edtPurpose.getText().toString());
                        hashMap.put("RequestFor", spinRequestFor.getSelectedItem().toString());
                        hashMap.put("Description", edtDescription.getText().toString());
                        appointmentAsyncTask = new AppointmentAsyncTask(hashMap);
                        final boolean success = appointmentAsyncTask.execute().get();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (success) {
                                    Utility.pong(mContext, "Appointment booked successfully");
                                    txtDate.setText("");
                                    edtPurpose.setText("");
                                    spinRequestFor.setSelection(0);
                                    edtDescription.setText("");
                                } else {
                                    progressDialog.dismiss();
                                    Utility.pong(mContext, "Appointment not booked.");
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

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String date = day + "/" + month + "/" + year;
            if (checkDate(date)) {
                txtDate.setText(day + "/" + month + "/" + year);
            } else {
                Utility.pong(getActivity(), "Please select a future date");
            }
        }

        public boolean checkDate(String selectedDate) {
            boolean isValidDate = false;
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date selectedDt = null;
            Date now = new Date();
            try {
                selectedDt = df.parse(selectedDate);
                if (selectedDt.before(now) || selectedDate.equals(now)) {
                    isValidDate = false;
                } else {
                    isValidDate = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return isValidDate;
        }
    }
}
