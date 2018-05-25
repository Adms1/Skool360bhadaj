package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Activities.DashBoardActivity;
import com.anandniketanbhadaj.skool360.skool360.Activities.EqualSpacingItemDecoration;
import com.anandniketanbhadaj.skool360.skool360.Adapter.GalleryAdapter;
import com.anandniketanbhadaj.skool360.skool360.Adapter.GalleryListAdapter;
import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.GalleryAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Interfacess.onViewClick;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.PhotoModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class GalleryFragment extends Fragment {
    Fragment fragment;
    FragmentManager fragmentManager;
    ArrayList<String> arrayList;
    ArrayList<PhotoModel> photoarrayList;
    ArrayList<String> name;
    RecyclerView gallery_list,gallery_list1;
    Button btnMenu, btnBack;
    GalleryListAdapter galleryListAdapter;
    GalleryAdapter galleryAdapter;
    ExamModel galleryResponse;
    String position = "";
    TextView photo_name;
    private View rootView;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GalleryAsyncTask galleryAsyncTask = null;
    String displayMode;

    public GalleryFragment() {
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
        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        gallery_list = (RecyclerView) rootView.findViewById(R.id.gallery_list);
        gallery_list1=(RecyclerView)rootView.findViewById(R.id.gallery_list1);
        photo_name = (TextView) rootView.findViewById(R.id.photo_name);
        getGalleryData();

    }

    public void setListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position.equalsIgnoreCase("")) {
                    fragment = new HomeFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }else{
                    fragment = new GalleryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
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
        gallery_list.setVisibility(View.VISIBLE);
        gallery_list1.setVisibility(View.GONE);
        arrayList = new ArrayList<String>();
        name = new ArrayList<>();

        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            name.add(galleryResponse.getFinalArray().get(i).getEventName());
            arrayList.add(galleryResponse.getFinalArray().get(i).getPhotos().get(0).getImagePath() + "|" +
                    galleryResponse.getFinalArray().get(i).getPhotos().get(0).getTitle());
        }


        galleryListAdapter = new GalleryListAdapter(mContext, name, arrayList, new onViewClick() {
            @Override
            public void getViewClick() {

                ArrayList<String> selectedposition = new ArrayList<String>();

                selectedposition = galleryListAdapter.getPhotoDetail();
                Log.d("selectedposition", "" + selectedposition);
                for (int i = 0; i < selectedposition.size(); i++) {
                    position = selectedposition.get(i);
                }
                Log.d("position", "" + position);
                setSelectedImage();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        gallery_list.setLayoutManager(mLayoutManager);
        gallery_list.addItemDecoration(new EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.GRID));
        gallery_list.setAdapter(galleryListAdapter);

    }

    public void setSelectedImage() {
        gallery_list.setVisibility(View.VISIBLE);
        gallery_list1.setVisibility(View.GONE);
        photoarrayList = new ArrayList<>();
        name = new ArrayList<>();
        displayMode="normal";
        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            if (position.equalsIgnoreCase(String.valueOf(i))) {
                name.add(galleryResponse.getFinalArray().get(i).getEventName());
                photo_name.setText(galleryResponse.getFinalArray().get(i).getEventName());
                for (int j = 0; j < galleryResponse.getFinalArray().get(i).getPhotos().size(); j++) {
                    photoarrayList.add(galleryResponse.getFinalArray().get(i).getPhotos().get(j));
                }
            }
        }
        galleryAdapter = new GalleryAdapter(mContext, name, photoarrayList,displayMode, new onViewClick() {
            @Override
            public void getViewClick() {
                setSelectedDetailImage();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        gallery_list.setLayoutManager(mLayoutManager);
        gallery_list.addItemDecoration(new EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.GRID));
        gallery_list.setAdapter(galleryAdapter);
    }

    public void setSelectedDetailImage(){
        gallery_list1.setVisibility(View.VISIBLE);
        gallery_list.setVisibility(View.GONE);
        photoarrayList = new ArrayList<>();
        name = new ArrayList<>();
        displayMode="diffrenet";
        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            if (position.equalsIgnoreCase(String.valueOf(i))) {
                name.add(galleryResponse.getFinalArray().get(i).getEventName());
                photo_name.setText(galleryResponse.getFinalArray().get(i).getEventName());
                for (int j = 0; j < galleryResponse.getFinalArray().get(i).getPhotos().size(); j++) {
                    photoarrayList.add(galleryResponse.getFinalArray().get(i).getPhotos().get(j));
                }
            }
        }
        galleryAdapter = new GalleryAdapter(mContext, name, photoarrayList, displayMode, new onViewClick() {
            @Override
            public void getViewClick() {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,true);
        gallery_list1.setLayoutManager(mLayoutManager);
        gallery_list1.addItemDecoration(new EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.HORIZONTAL));
        gallery_list1.setAdapter(galleryAdapter);
    }
}