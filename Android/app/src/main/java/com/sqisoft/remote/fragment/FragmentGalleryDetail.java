package com.sqisoft.remote.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.activity.MainActivity;
import com.sqisoft.remote.domain.ServerGalleryImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.view.MyGalleryDetailRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentGalleryDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentGalleryDetail#} factory method to
 * create an instance of this fragment.
 */
public class FragmentGalleryDetail extends FragmentBase{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FragmentManager manager;
    FragmentTransaction transaction;

    ViewPager pager;
    int currentIndex;
    ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains = DataManager.getInstance().getServerGalleryImageDomains();

    private OnFragmentInteractionListener mListener;
    private  ImageView detailImageView;
    private View imageDetailView;
    private  RecyclerView image_detail_recyclerView;

    // TODO: Rename and change types and number of parameters
    public static FragmentCamera newInstance(String param1, String param2) {
        FragmentCamera fragment = new FragmentCamera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public FragmentGalleryDetail(int param2) {
        this.currentIndex = param2;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageDetailView = inflater.inflate(R.layout.fragment_gallery_image_detail, container, false);

        setTitle("셀카 갤러리 사진보기");
        MainActivity.setTopBackButton(false);

        setGalleryButton(true);

        attachViews();

        MyGalleryDetailRecyclerAdapter myAdapter = new MyGalleryDetailRecyclerAdapter(this,getActivity(),detailImageView);



        image_detail_recyclerView.setAdapter(myAdapter);
        image_detail_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1,OrientationHelper.HORIZONTAL,false));

        //디테일 이미지 로딩
           Picasso.with(getContext())
                .load(serverGalleryImageDomains.get(currentIndex).getImageUrl())
                .placeholder(R.drawable.dx)
                .into(detailImageView,DataManager.getInstance().getImageLoadedCallback(detailImageView));


        return imageDetailView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    String getTitle() {
        return "셀카 갤러리 사진보기";
    }

    @Override
    void attachViews() {
        detailImageView = (ImageView) imageDetailView.findViewById(R.id.detail_image_view);
        image_detail_recyclerView = (RecyclerView) imageDetailView.findViewById(R.id.image_detail_recycler_view);
    }

    @Override
    void attachListener() {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}