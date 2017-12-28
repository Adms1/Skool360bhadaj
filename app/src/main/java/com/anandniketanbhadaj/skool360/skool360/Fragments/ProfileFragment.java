package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.ChangePasswordAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GetUserProfileAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Models.StudProfileModel;
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

/**
 * Created by Harsh on 04-Aug-16.
 */
public class ProfileFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnPersonalDetail, btnEducationalDetail, btnEditProfile, btnUpdate, btnBackProfile;
    private TextView txtName, txtDOB, txtAge, txtGender, txtBloodGroup, txtBirthPlace, txtFatherName, txtFatherPhNo, txtFatherEmail,
            txtMotherName, txtMotherPhNo, txtMotherEmail, txtAddress, txtCity, txtTransKMs, txtTrasPickTime, txtTrasDropTime,
            txtGRNO, txtStandard, txtClass, txtUserName, txtPassword, txtEdit, studName, txtsmsno;
    private View viewShadow1, viewShadow2, viewShadow3;
    private CircleImageView profile_image;
    private View includePersonalDetail, includeEducationalDetail, includeEditProfileDetail;
    private EditText edtPassword;
    private Context mContext;
    private GetUserProfileAsyncTask getUserProfileAsyncTask = null;
    private ChangePasswordAsyncTask changePasswordAsyncTask = null;
    private ArrayList<StudProfileModel> studDetailList = new ArrayList<>();
    private ProgressDialog progressDialog = null;
    private ImageLoader imageLoader;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        mContext = getActivity();

        initViews();
        getUserProfile();
        setListners();

        return rootView;
    }

    public void initViews() {
        studName = (TextView) rootView.findViewById(R.id.studName);
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackProfile = (Button) rootView.findViewById(R.id.btnBackProfile);
        btnPersonalDetail = (Button) rootView.findViewById(R.id.btnPersonalDetail);
//        btnPersonalDetail.setBackgroundResource(R.drawable.white_dropshadow);
        btnPersonalDetail.setBackgroundColor(getResources().getColor(R.color.profile_lite));
        btnEducationalDetail = (Button) rootView.findViewById(R.id.btnEducationalDetail);
        btnEditProfile = (Button) rootView.findViewById(R.id.btnEditProfile);
        viewShadow1 = (View) rootView.findViewById(R.id.viewShadow1);
        viewShadow2 = (View) rootView.findViewById(R.id.viewShadow2);
        viewShadow3 = (View) rootView.findViewById(R.id.viewShadow3);
        viewShadow1.setVisibility(View.INVISIBLE);
        includePersonalDetail = (View) rootView.findViewById(R.id.includePersonalDetail);
        includeEducationalDetail = (View) rootView.findViewById(R.id.includeEducationalDetail);
        includeEditProfileDetail = (View) rootView.findViewById(R.id.includeEditProfileDetail);
        txtName = (TextView) includePersonalDetail.findViewById(R.id.txtName);
        txtDOB = (TextView) includePersonalDetail.findViewById(R.id.txtDOB);
        txtAge = (TextView) includePersonalDetail.findViewById(R.id.txtAge);
        txtGender = (TextView) includePersonalDetail.findViewById(R.id.txtGender);
        txtBloodGroup = (TextView) includePersonalDetail.findViewById(R.id.txtBloodGroup);
        txtBirthPlace = (TextView) includePersonalDetail.findViewById(R.id.txtBirthPlace);
        txtFatherName = (TextView) includePersonalDetail.findViewById(R.id.txtFatherName);
        txtFatherPhNo = (TextView) includePersonalDetail.findViewById(R.id.txtFatherPhNo);
        txtFatherEmail = (TextView) includePersonalDetail.findViewById(R.id.txtFatherEmail);
        txtMotherName = (TextView) includePersonalDetail.findViewById(R.id.txtMotherName);
        txtMotherPhNo = (TextView) includePersonalDetail.findViewById(R.id.txtMotherPhNo);
        txtMotherEmail = (TextView) includePersonalDetail.findViewById(R.id.txtMotherEmail);
        txtsmsno = (TextView) includePersonalDetail.findViewById(R.id.txtsmsno);
        txtAddress = (TextView) includePersonalDetail.findViewById(R.id.txtAddress);
        txtCity = (TextView) includePersonalDetail.findViewById(R.id.txtCity);
        txtTransKMs = (TextView) includePersonalDetail.findViewById(R.id.txtTransKMs);
        txtTrasPickTime = (TextView) includePersonalDetail.findViewById(R.id.txtTrasPickTime);
        txtTrasDropTime = (TextView) includePersonalDetail.findViewById(R.id.txtTrasDropTime);

        txtGRNO = (TextView) includeEducationalDetail.findViewById(R.id.txtGRNO);
        txtStandard = (TextView) includeEducationalDetail.findViewById(R.id.txtStandard);
        txtClass = (TextView) includeEducationalDetail.findViewById(R.id.txtClass);
        txtUserName = (TextView) includeEditProfileDetail.findViewById(R.id.txtUserName);
        txtPassword = (TextView) includeEditProfileDetail.findViewById(R.id.txtPassword);
        edtPassword = (EditText) includeEditProfileDetail.findViewById(R.id.edtPassword);
        txtEdit = (TextView) includeEditProfileDetail.findViewById(R.id.txtEdit);

        btnUpdate = (Button) includeEditProfileDetail.findViewById(R.id.btnUpdate);

        profile_image = (CircleImageView) rootView.findViewById(R.id.profile_image);
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
    }

    public void setListners() {
        btnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });
        btnPersonalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPersonalDetail.setBackgroundColor(getResources().getColor(R.color.profile_lite));
                btnEducationalDetail.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                btnEditProfile.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                includePersonalDetail.setVisibility(View.VISIBLE);
                includeEducationalDetail.setVisibility(View.GONE);
                includeEditProfileDetail.setVisibility(View.GONE);
                viewShadow1.setVisibility(View.INVISIBLE);
                viewShadow2.setVisibility(View.VISIBLE);
                viewShadow3.setVisibility(View.VISIBLE);
            }
        });
        btnEducationalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPersonalDetail.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                btnEducationalDetail.setBackgroundColor(getResources().getColor(R.color.profile_lite));
                btnEditProfile.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                includePersonalDetail.setVisibility(View.GONE);
                includeEducationalDetail.setVisibility(View.VISIBLE);
                includeEditProfileDetail.setVisibility(View.GONE);
                viewShadow1.setVisibility(View.VISIBLE);
                viewShadow2.setVisibility(View.INVISIBLE);
                viewShadow3.setVisibility(View.VISIBLE);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPersonalDetail.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                btnEducationalDetail.setBackgroundColor(getResources().getColor(R.color.profile_dark));
                btnEditProfile.setBackgroundColor(getResources().getColor(R.color.profile_lite));
                includePersonalDetail.setVisibility(View.GONE);
                includeEducationalDetail.setVisibility(View.GONE);
                includeEditProfileDetail.setVisibility(View.VISIBLE);
                viewShadow1.setVisibility(View.VISIBLE);
                viewShadow2.setVisibility(View.VISIBLE);
                viewShadow3.setVisibility(View.INVISIBLE);
            }
        });

        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEdit.getText().toString().equalsIgnoreCase("CHANGE")) {
                    txtEdit.setText("CANCEL");
                    edtPassword.setVisibility(View.VISIBLE);
                    txtPassword.setVisibility(View.GONE);
                    btnUpdate.setVisibility(View.VISIBLE);
                } else if (txtEdit.getText().toString().equalsIgnoreCase("CANCEL")) {
                    txtEdit.setText("CHANGE");
                    edtPassword.setVisibility(View.GONE);
                    txtPassword.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.GONE);
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(mContext)) {

                    if (edtPassword.getVisibility() == View.VISIBLE) {
                        if (!edtPassword.getText().toString().equalsIgnoreCase("")) {
                            if (!edtPassword.getText().toString().equalsIgnoreCase(studDetailList.get(0).getPassword())) {
                                progressDialog = new ProgressDialog(mContext);
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Please wait...");
                                progressDialog.show();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            HashMap<String, String> params = new HashMap<String, String>();
                                            params.put("StudentID", Utility.getPref(mContext, "studid"));
                                            params.put("OldPassword", studDetailList.get(0).getPassword());
                                            params.put("NewPassword", edtPassword.getText().toString());
                                            changePasswordAsyncTask = new ChangePasswordAsyncTask(params);
                                            final Boolean result = changePasswordAsyncTask.execute().get();

                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.dismiss();
                                                    if (result == true) {
                                                        Utility.pong(mContext, "Password Updated Successfully");
                                                        if (!Utility.getPref(mContext, "pwd").equalsIgnoreCase("")) {
                                                            Utility.setPref(mContext, "pwd", edtPassword.getText().toString());
                                                            getUserProfile();
                                                            txtEdit.performClick();
                                                        }
                                                    }
                                                }
                                            });

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            } else {
                                Utility.pong(mContext, "New password cant be same as old password");
                            }
                        } else {
                            Utility.pong(mContext, "Password cant be empty");
                        }
                    }
                } else {
                    Utility.ping(mContext, "NEtwork not available");
                }
            }
        });
    }

    public void getUserProfile() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StudentID", Utility.getPref(mContext, "studid"));
                        getUserProfileAsyncTask = new GetUserProfileAsyncTask(params);
                        studDetailList = getUserProfileAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                studName.setText(studDetailList.get(0).getStudentName());
                                imageLoader.displayImage(studDetailList.get(0).getStudentImage(), profile_image);
                                txtName.setText(studDetailList.get(0).getStudentName());
                                txtDOB.setText(studDetailList.get(0).getStudentDOB());
                                txtAge.setText(studDetailList.get(0).getStudentAge());
                                txtGender.setText(studDetailList.get(0).getStudentGender());
                                txtBloodGroup.setText(studDetailList.get(0).getBloodGroup());
                                txtBirthPlace.setText(studDetailList.get(0).getBirthPlace());
                                txtFatherName.setText(studDetailList.get(0).getFatherName());
                                txtFatherPhNo.setText(studDetailList.get(0).getFatherPhone());
                                txtFatherEmail.setText(studDetailList.get(0).getFatherEmail());
                                txtMotherName.setText(studDetailList.get(0).getMotherName());
                                txtMotherPhNo.setText(studDetailList.get(0).getMotherMobile());
                                txtMotherEmail.setText(studDetailList.get(0).getMotherEmail());
                                txtsmsno.setText(studDetailList.get(0).getSMSNumber());
                                txtAddress.setText(studDetailList.get(0).getAddress());
                                txtCity.setText(studDetailList.get(0).getCity());
                                txtTransKMs.setText(studDetailList.get(0).getTransport_KM());
                                txtTrasPickTime.setText(studDetailList.get(0).getTransport_PicupTime());
                                txtTrasDropTime.setText(studDetailList.get(0).getTransport_DropTime());
                                txtGRNO.setText(studDetailList.get(0).getGRNO());
                                txtStandard.setText(studDetailList.get(0).getStandard());
                                txtClass.setText(studDetailList.get(0).getStudClass());
                                txtUserName.setText(studDetailList.get(0).getUserName());
                                txtPassword.setText(studDetailList.get(0).getPassword());
                                edtPassword.setText(studDetailList.get(0).getPassword());

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
}
