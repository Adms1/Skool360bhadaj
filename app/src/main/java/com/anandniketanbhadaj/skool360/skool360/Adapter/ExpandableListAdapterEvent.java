package com.anandniketanbhadaj.skool360.skool360.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;
import com.anandniketanbhadaj.skool360.skool360.Fragments.EventFragment;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterEvent extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<String>> _listDataChild;
    private ProgressBar spinner = null;
    private ImageLoader imageLoader;

    public ExpandableListAdapterEvent(Context context, List<String> listDataHeader,
                                      HashMap<String, ArrayList<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public String getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_event, null);
        }

       final ImageView imgEventImage1, imgEventImage2;//;

        imgEventImage1 = (ImageView) convertView.findViewById(R.id.imgEventImage1);
        imgEventImage2 = (ImageView) convertView.findViewById(R.id.imgEventImage2);
        spinner = new ProgressBar(_context, null, android.R.attr.progressBarStyleSmall);

        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .showImageOnLoading(R.drawable.placeholder_image)
                .showImageForEmptyUri(R.drawable.placeholder_image)
                .showImageOnFail(R.drawable.placeholder_image).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                _context)
                .threadPriority(Thread.MAX_PRIORITY)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();
        imageLoader.init(config.createDefault(_context));

        String[] childImagePath = null;
        if(childText.contains("|")){
            childImagePath = childText.split("\\|");
        }

        if(childText.contains("|")){
            imageLoader.displayImage(childImagePath[0], imgEventImage1, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    spinner.setVisibility(View.GONE);
                }
            });

            imageLoader.displayImage(childImagePath[1], imgEventImage2, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    spinner.setVisibility(View.GONE);
                }
            });
        }else {
            imageLoader.displayImage(childText, imgEventImage1, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    spinner.setVisibility(View.GONE);
                }
            });
        }

        imgEventImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventFragment.rlOverLay.setVisibility(View.VISIBLE);
                Bitmap bitmap = ((BitmapDrawable)imgEventImage1.getDrawable()).getBitmap();
                EventFragment.imgFullScreenImage.setImageBitmap(bitmap);
            }
        });

        imgEventImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventFragment.rlOverLay.setVisibility(View.VISIBLE);
                Bitmap bitmap = ((BitmapDrawable)imgEventImage2.getDrawable()).getBitmap();
                EventFragment.imgFullScreenImage.setImageBitmap(bitmap);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_event, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);

        if (isExpanded) {
            convertView.setBackgroundResource(R.drawable.event_row_orange_bg);
            lblListHeader.setTextColor(_context.getResources().getColor(R.color.white));
        } else {
            convertView.setBackgroundResource(R.drawable.event_row_bg);
            lblListHeader.setTextColor(_context.getResources().getColor(R.color.appointment_text));
        }


        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}