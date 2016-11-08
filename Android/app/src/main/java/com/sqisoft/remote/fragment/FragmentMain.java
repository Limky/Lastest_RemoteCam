package com.sqisoft.remote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sqisoft.remote.R;
import com.sqisoft.remote.activity.MainActivity;
import com.sqisoft.remote.data.ResponseListener;
import com.sqisoft.remote.domain.SelfieZoneDomain;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.sqisoft.remote.util.Log;
import com.sqisoft.remote.util.SelfieZoneUtil;
import com.sqisoft.remote.view.MyMainListViewAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends FragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mCamera_btn;
    private OnFragmentInteractionListener mListener;

    View fragment_main_view;
    MyMainListViewAdapter myMainListViewAdapter;
    private ListView m_ListView;

    public FragmentMain() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Main.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
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
    public void onResume(){
    //    Toast.makeText(getActivity(), "Resume "+ "Main 스타트", Toast.LENGTH_SHORT).show();

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setTitle("셀카 촬영");
        MainActivity.setTopBackButton(true);
        setGalleryButton(false);
        // Inflate the layout for this fragment
        fragment_main_view = inflater.inflate(R.layout.fragment_main, container, false);


        attachViews();

        attachListener();

        LoadServerSelfieZoneDate();


        myMainListViewAdapter = new MyMainListViewAdapter(getActivity(), DataManager.getInstance().getSelfieZoneDomains());

        //메인 리스트 헤더 푸터 설정
        View header = inflater.inflate(R.layout.listview_main_header,null,false);
        View footer = inflater.inflate(R.layout.listview_main_footer,null,false);
        m_ListView.addHeaderView(header);
        m_ListView.addFooterView(footer);
        m_ListView.setAdapter(myMainListViewAdapter);



        return fragment_main_view;

    }


    private void LoadServerSelfieZoneDate(){

        SelfieZoneUtil.getZone(new ResponseListener<SelfieZoneDomain[]>() {

            @Override
            public void response(boolean success, SelfieZoneDomain[] data) {
                Log.d("test","메인 안에 response (1)");
                if(success && data != null) {
                    Log.d("test","response  피니시(11)");
                    ArrayList<SelfieZoneDomain> selfieZoneDomains = new ArrayList<SelfieZoneDomain>();
                    for (int i = 0; i < data.length; i++) {
                        selfieZoneDomains.add(data[i]);
                        DataManager.getInstance().setSelfieZoneDomains(selfieZoneDomains);

                    }
                }
            }
        });

    }

    private Button.OnClickListener CameraFragmentMoveListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentUtil.addFragment(new FragmentCamera());
            //    transaction.replace(R.id.replacedLayout, new FragmentCamera(),"FCamera").addToBackStack(null).commit();
        }
    };



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

    //해당 프래그먼트 title 던져주기
    @Override
    String getTitle() {
        return "셀카촬영";
    }


    @Override
    void attachViews() {
        m_ListView = (ListView) fragment_main_view.findViewById(R.id.list);
        mCamera_btn = (Button)fragment_main_view.findViewById(R.id.camera_btn);
    }

    @Override
    void attachListener() {
        mCamera_btn.setOnClickListener(CameraFragmentMoveListener);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
