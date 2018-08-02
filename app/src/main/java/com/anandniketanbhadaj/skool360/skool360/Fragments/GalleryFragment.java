package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;


public class GalleryFragment extends Fragment {
    Fragment fragment;
    FragmentManager fragmentManager;
    ArrayList<String> arrayList;
    ArrayList<PhotoModel> photoarrayList;
    ArrayList<String> name;
    RecyclerView gallery_list, gallery_list1;
    Button btnMenu, btnBack;
    GalleryListAdapter galleryListAdapter;
    GalleryAdapter galleryAdapter;
    ExamModel galleryResponse;
    String position = "";
    TextView photo_name, event_name_txt;
    String displayMode;
    LinearLayout linearBack;
    private View rootView;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GalleryAsyncTask galleryAsyncTask = null;

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
        linearBack=(LinearLayout)rootView.findViewById(R.id.linearBack);
        gallery_list = (RecyclerView) rootView.findViewById(R.id.gallery_list);
        gallery_list1 = (RecyclerView) rootView.findViewById(R.id.gallery_list1);
        photo_name = (TextView) rootView.findViewById(R.id.photo_name);
        event_name_txt = (TextView) rootView.findViewById(R.id.event_name_txt);
        getGalleryData();

    }

    public void setListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                if (position.equalsIgnoreCase("")) {
                    fragment = new HomeFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else {
                    fragment = new GalleryFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        });
        linearBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                if (position.equalsIgnoreCase("")) {
                    fragment = new HomeFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                            .replace(R.id.frame_container, fragment).commit();
                } else {
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
        event_name_txt.setVisibility(View.GONE);
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
        event_name_txt.setVisibility(View.VISIBLE);
        photoarrayList = new ArrayList<>();
        name = new ArrayList<>();
        displayMode = "normal";
        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            if (position.equalsIgnoreCase(String.valueOf(i))) {
                name.add(galleryResponse.getFinalArray().get(i).getEventName());
                event_name_txt.setText(galleryResponse.getFinalArray().get(i).getEventName());
                for (int j = 0; j < galleryResponse.getFinalArray().get(i).getPhotos().size(); j++) {
                    photoarrayList.add(galleryResponse.getFinalArray().get(i).getPhotos().get(j));
                }
            }
        }
        galleryAdapter = new GalleryAdapter(mContext, name, photoarrayList, displayMode, new onViewClick() {
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

    public void setSelectedDetailImage() {
        gallery_list1.setVisibility(View.VISIBLE);
        gallery_list.setVisibility(View.GONE);
        event_name_txt.setVisibility(View.VISIBLE);
        photoarrayList = new ArrayList<>();
        name = new ArrayList<>();
        displayMode = "diffrenet";
        for (int i = 0; i < galleryResponse.getFinalArray().size(); i++) {
            if (position.equalsIgnoreCase(String.valueOf(i))) {
                name.add(galleryResponse.getFinalArray().get(i).getEventName());
                event_name_txt.setText(galleryResponse.getFinalArray().get(i).getEventName());
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
//        mLayoutManager.setStackFromEnd(true);
        gallery_list1.setLayoutManager(mLayoutManager);
        gallery_list1.getLayoutManager().scrollToPosition(photoarrayList.size() - 1);
        gallery_list1.addItemDecoration(new EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.HORIZONTAL));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(gallery_list1);
        gallery_list1.setAdapter(galleryAdapter);
    }
}
