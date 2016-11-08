package com.sqisoft.remote.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.activity.MainActivity;
import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.domain.ServerGalleryImageDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.view.MyAlbumDetailRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMyAlbumImageDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMyAlbumImageDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyAlbumImageDetail extends FragmentBase {

    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<LocalImageObject> localImageObjects = new ArrayList<LocalImageObject>();
    private OnFragmentInteractionListener mListener;
    private int currentIndex;
    private ViewPager pager;
    private View myAlbumImageDetailView;
    private Bitmap bitmap;
    private TextView mCategoryTextView;

  //  private Button share,download,delete;

   ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains = DataManager.getInstance().getServerGalleryImageDomains();



    public FragmentMyAlbumImageDetail() {
        // Required empty public constructor
    }

    public FragmentMyAlbumImageDetail(int pos) {
        // Required empty public constructor
        this.localImageObjects = DataManager.getInstance().getLocalImageObjects();
        this.currentIndex = pos;
    }


    @Override
    String getTitle() {
        return null;
    }

    @Override
    void attachViews() {

    }

    @Override
    void attachListener() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMyAlbumImageDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyAlbumImageDetail newInstance(String param1, String param2) {
        FragmentMyAlbumImageDetail fragment = new FragmentMyAlbumImageDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myAlbumImageDetailView = inflater.inflate(R.layout.fragment_fragment_my_album_image_detail, container, false);
        setGalleryButton(true);
        setTitle("사진 보기");
        MainActivity.setTopBackButton(false);
        MainActivity.getImageButtonLayout().setVisibility(View.VISIBLE);

        final RecyclerView image_detail_recyclerView = (RecyclerView) myAlbumImageDetailView.findViewById(R.id.image_detail_recycler_view);
        mCategoryTextView = (TextView) myAlbumImageDetailView.findViewById(R.id.category);
        ImageView my_album_detail_image = (ImageView) myAlbumImageDetailView.findViewById(R.id.my_album_detail_image);
        MyAlbumDetailRecyclerAdapter myAdapter = new MyAlbumDetailRecyclerAdapter(this,getActivity(),my_album_detail_image,mCategoryTextView);

        image_detail_recyclerView.setAdapter(myAdapter);
        image_detail_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1, OrientationHelper.HORIZONTAL,false));


        Picasso.with(getActivity())
                .load(serverGalleryImageDomains.get(currentIndex).getImageUrl())
                .placeholder(R.drawable.dx)
                .into(my_album_detail_image,DataManager.getInstance().getImageLoadedCallback(my_album_detail_image));

        DataManager.getInstance().setCurrentCurrentImageURL(serverGalleryImageDomains.get(currentIndex).getImageUrl());



        return myAlbumImageDetailView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
