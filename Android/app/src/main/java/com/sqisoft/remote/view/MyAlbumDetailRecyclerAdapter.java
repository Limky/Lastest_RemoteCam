package com.sqisoft.remote.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.sqisoft.remote.R;
import com.sqisoft.remote.domain.ServerGalleryImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SQISOFT on 2016-10-07.
 */

public class MyAlbumDetailRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains;
    private Fragment adapterContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Context context;
    private ImageLoader mImageLoader;
    private ImageView mDetail_image;
    private Bitmap bitmap,bit;
    private TextView mCategoryTextView;

    // Adapter constructor
    public MyAlbumDetailRecyclerAdapter(Fragment adapterContext, Context context, ImageView detail_image ,TextView category_text) {

        this.adapterContext = adapterContext;
        this.serverGalleryImageDomains = DataManager.getInstance().getServerGalleryImageDomains();
        this.context = context;
        this.mDetail_image = detail_image;
        this.mCategoryTextView = category_text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_listitem, null);

        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {



        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.mServer_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Picasso.with(context)
                        .load(serverGalleryImageDomains.get(position).getImageUrl())
                        .into(mDetail_image,DataManager.getInstance().getImageLoadedCallback(mDetail_image));

                DataManager.getInstance().setCurrentCurrentImageURL(serverGalleryImageDomains.get(position).getImageUrl());

                //해당 상세보기 이미지 날짜
                mCategoryTextView.setText(serverGalleryImageDomains.get(position).getImageDate());
                mCategoryTextView.invalidate();
            }
        });

        mCategoryTextView.setText(serverGalleryImageDomains.get(position).getImageDate());
        mCategoryTextView.invalidate();

        Picasso.with(context)
                .load(serverGalleryImageDomains.get(position).getImageUrl())
                .placeholder(R.drawable.dx)
                .resize(232,202)
                .into(myViewHolder.mServer_imageview);
    }

    @Override
    public int getItemCount() {

        return serverGalleryImageDomains.size();
    }

    /** This is our ViewHolder class */
    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView detailView,mServer_imageview;
        public RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView); // Must call super() first
            mServer_imageview = (ImageView) itemView.findViewById(R.id.server_imageview);

        }


    }



}