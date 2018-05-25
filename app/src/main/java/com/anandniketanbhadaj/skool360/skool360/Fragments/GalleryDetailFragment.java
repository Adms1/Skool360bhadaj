package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Activities.EqualSpacingItemDecoration;
import com.anandniketanbhadaj.skool360.skool360.Adapter.GalleryAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GalleryAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.PhotoModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class GalleryDetailFragment extends Fragment {
    Fragment fragment;
    FragmentManager fragmentManager;
    ArrayList<PhotoModel> arrayList;
    ArrayList<String> name;
    RecyclerView gallery_list;
    Button btnMenu, btnBack;
    GalleryAdapter galleryAdapter;
    ExamModel galleryResponse;
    String position;
    private View rootView;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GalleryAsyncTask galleryAsyncTask = null;
TextView photo_name;

    public GalleryDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {

        position = getArguments().getString("position");

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        gallery_list = (RecyclerView) rootView.findViewById(R.id.gallery_list);
        photo_name=(TextView)rootView.findViewById(R.id.photo_name);
        getGalleryData();

    }

    public void setListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new GalleryFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashBoardActivity.onLeft();
            }
        });

    }

    public void getGalleryData() {
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
                        galleryAsyncTask = new GalleryAsyncTask(params);
                        galleryResponse = galleryAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (galleryResponse.getFinalArray().size() > 0) {
                                    progressDialog.dismiss();
//                                    prepaareList();

                                    setGalleryData();
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


    public void setGalleryData() {
        arrayList = new ArrayList<PhotoModel>();
        name = new ArrayList<>();

        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            if (position.equalsIgnoreCase(String.valueOf(i))) {
                name.add(galleryResponse.getFinalArray().get(i).getEventName());
                photo_name.setText(galleryResponse.getFinalArray().get(i).getEventName());
                for (int j = 0; j < galleryResponse.getFinalArray().get(i).getPhotos().size(); j++) {
                    arrayList.add(galleryResponse.getFinalArray().get(i).getPhotos().get(j));
                }
            }
        }


//        galleryAdapter = new GalleryAdapter(mContext, name, arrayList, displayMode, new onViewClick() {
//            @Override
//            public void getViewClick() {
//            }
//        });
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
//        gallery_list.setLayoutManager(mLayoutManager);
//        gallery_list.addItemDecoration(new EqualSpacingItemDecoration(12,EqualSpacingItemDecoration.GRID));
//        gallery_list.setAdapter(galleryAdapter);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
//        gallery_list.setLayoutManager(mLayoutManager);
//        gallery_list.setItemAnimator(new DefaultItemAnimator());
//        gallery_list.setAdapter(galleryAdapter);
    }
}
