package com.sqisoft.remote.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sqisoft.remote.R;
import com.sqisoft.remote.data.ResponseListener;
import com.sqisoft.remote.domain.ServerGalleryImageDomain;
import com.sqisoft.remote.fragment.FragmentCamera;
import com.sqisoft.remote.fragment.FragmentGallery;
import com.sqisoft.remote.fragment.FragmentGalleryDetail;
import com.sqisoft.remote.fragment.FragmentMain;
import com.sqisoft.remote.fragment.FragmentMyAlbum;
import com.sqisoft.remote.fragment.FragmentMyAlbumImageDetail;
import com.sqisoft.remote.fragment.FragmentUserConfirm;
import com.sqisoft.remote.manager.DataManager;
import com.sqisoft.remote.util.FragmentUtil;
import com.sqisoft.remote.util.Log;
import com.sqisoft.remote.util.ServerImageUtil;
import com.sqisoft.remote.view.TitleView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements FragmentMain.OnFragmentInteractionListener,FragmentCamera.OnFragmentInteractionListener,
        FragmentGallery.OnFragmentInteractionListener,FragmentGalleryDetail.OnFragmentInteractionListener,FragmentUserConfirm.OnFragmentInteractionListener,FragmentMyAlbum.OnFragmentInteractionListener
,FragmentMyAlbumImageDetail.OnFragmentInteractionListener{


    private Button ip_cameraBtn;
    long backKeyPressedTime = 0;
    private Toast toast;
    private static TitleView mTitleView;
    private static Button mPhotoGalleryBtn,mTopBackBtn,mDownload_btn,mDelete_btn,mShare_btn,mMain_btn,mMyAblum_Btn,mPhoto_gallery_Btn;
    private static final int ONE_SECOND = 1000;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FrameLayout naviLayout;
    private static LinearLayout mSelect_delete_share;
    private String fileName;
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AttachViews();

        AttachListener();

        mTitleView.setText("메인");
        mTitleView.invalidate();

         manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        naviLayout.setVisibility(View.VISIBLE);

        //프래그먼트 유틸(프래그먼트간 이동 공통 모듈) 초기화
        FragmentUtil.init(R.id.replacedLayout, getSupportFragmentManager(), mTitleView);

        LoadServerGalleryImage();

    }



    private void LoadServerGalleryImage() {
        /**
         * Json 파일 파싱해서 자바 객체로..
         * **/
        ServerImageUtil.getZone(new ResponseListener<ServerGalleryImageDomain[]>() {

            @Override
            public void response(boolean success, ServerGalleryImageDomain[] data) {
                Log.d("test","메인 안에 response (1)");
                if(success && data != null) {
                    Log.d("test","response  피니시(11)");
                    ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains = new ArrayList<ServerGalleryImageDomain>();
                    for (int i = 0; i < data.length; i++) {
                        serverGalleryImageDomains.add(data[i]);
                        DataManager.getInstance().setServerGalleryImageDomains(serverGalleryImageDomains);
                        Log.d("test", data[i].getImageTitle());
                    }
                }
            }
        });

    }

    private void AttachViews(){
        mTitleView = (TitleView) findViewById(R.id.remotecamera_main_title);
        naviLayout = (FrameLayout) findViewById(R.id.navi_layout);
        mPhotoGalleryBtn = (Button) findViewById(R.id.photo_gallery_Btn);
        mTopBackBtn = (Button) findViewById(R.id.top_back_btn);
        mSelect_delete_share = (LinearLayout) findViewById(R.id.image_delete_share_layout);

        mMain_btn = (Button) findViewById(R.id.main_link_btn);
        mMyAblum_Btn= (Button) findViewById(R.id.my_album_link_btn);
        mPhoto_gallery_Btn = (Button)findViewById(R.id.photo_gallery_Btn);

        mDownload_btn = (Button) findViewById(R.id.download_btn);
        mDelete_btn = (Button) findViewById(R.id.delete_btn);
        mShare_btn = (Button) findViewById(R.id.share_btn);

    }


    private void AttachListener() {

        mMain_btn.setOnClickListener(MainFragmentMoveListener);
        mMyAblum_Btn.setOnClickListener(MyAlbumFragmentMoveListener);
        mPhoto_gallery_Btn.setOnClickListener(GalleryFragmentMoveListener);

        mDownload_btn.setOnClickListener(DownloadListener);
        mDelete_btn.setOnClickListener(DeleteListener);
        mShare_btn.setOnClickListener(ShareListener);

        mTopBackBtn.setOnClickListener(TopBackButtonListener);

    }


    private Button.OnClickListener MainFragmentMoveListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentUtil.addFragment(new FragmentMain());
            //     transaction.replace(R.id.replacedLayout, main,"FMain").commit();
            naviLayout.setVisibility(View.GONE);
        }
    };

    private Button.OnClickListener MyAlbumFragmentMoveListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentUtil.addFragment(new FragmentMyAlbum());
            naviLayout.setVisibility(View.GONE);
        }
    };

    private Button.OnClickListener GalleryFragmentMoveListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentUtil.addFragment(new FragmentGallery());

        }
    };



    /* * * * * * * * * * * * * * * * * * * * * *
     * MY앨범 레이아웃 top navi 버튼 리스너
     * * * * * * * * * * * * * * * * * * * * *
     * * */

    private Button.OnClickListener DownloadListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            /**
             * MY앨범 이미지 다운로드 이벤트
             * **/
            Toast.makeText(getApplicationContext(),"image saved", Toast.LENGTH_SHORT).show();
            DataManager dataManager = DataManager.getInstance();

            Log.i("dataManager.getCurrentCurrentImageURL()",""+dataManager.getCurrentCurrentImageURL());
            new WebGetImage(dataManager.getCurrentCurrentImageURL()).execute();


        }
    };


    private Button.OnClickListener DeleteListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            ///storage/emulated/0
            Log.i("삭제 버튼 이벤트","경로 = "+ android.os.Environment.getExternalStorageDirectory()+"/DCIM/HIKVISION/"+DataManager.getInstance().getCurrentImageTitle()+".jpg");
/*            //이미지 삭제
          File file= new File(android.os.Environment.getExternalStorageDirectory()+"/DCIM/HIKVISION/"+DataManager.getInstance().getCurrentImageTitle()+".jpg");
            if(file.exists())
            {
                file.delete();
            }
     */
        }
    };


    private Button.OnClickListener ShareListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {

        }
    };




    private Button.OnClickListener TopBackButtonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            backButtonFunction();
        }
    };


    @Override
    public void onBackPressed(){

        backButtonFunction();

    }

    @Override
    public void onResume(){
        super.onResume();
        naviLayout.setVisibility(View.VISIBLE);

    }

    public void backButtonFunction(){
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.i("backStackCount","backStackCount ========== "+backStackCount);

        if (backStackCount > 0) {
            super.onBackPressed();
            if (backStackCount == 1) {
                //Main 처리
                toast = Toast.makeText(getApplicationContext(), "메인 처리", Toast.LENGTH_SHORT);
                naviLayout.setVisibility(View.VISIBLE);
                mTitleView.setText("메인");
                mTitleView.invalidate();
           //     finish();
            }
        } else {
            finish();
            //currentTimeMillis 현재시간이 버튼을 눌린 시간 + 2초 보다 흘럿다면 2초내 클릭 안한것임.
          /*  if(System.currentTimeMillis() > backKeyPressedTime + 2000){
                backKeyPressedTime = System.currentTimeMillis(); //backKeyPressedTime 버튼을 누른 시간을 입력
                toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                toast.show();

                super.onBackPressed();
                return;
            }

            if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
                finish();
                toast.cancel();sl
            }*/
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static TitleView getTitleView(){
        return mTitleView;
    }
    public static Button getGalleryButton(){ return mPhotoGalleryBtn; }

    public static LinearLayout getImageButtonLayout(){ return mSelect_delete_share; }

    public static void setTopBackButton(Boolean b){
        if(b){
            mTopBackBtn.setBackgroundResource(R.drawable.backarrowbtn);
        }else{
            mTopBackBtn.setBackgroundResource(R.drawable.xicon);
        }
         mTopBackBtn.invalidate();
    }


    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    } // In MyActivity




    //네트워크 카메라 웹 뷰에서 이미지를 가져온다.
    public class WebGetImage extends AsyncTask<Void, Void, Void> {

        String imageURL = null;

        public WebGetImage(String imageURL) {
            this.imageURL = imageURL;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // 네트워크에 접속해서 데이터를 가져옴

            try {

                Thread.sleep(1000);
                URL url = new URL(imageURL);
                URLConnection conn = url.openConnection();
                conn.connect();

                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                bit = BitmapFactory.decodeStream(bis);
                bis.close();

                SavedImage();

                galleryAddPic();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected  void onPostExecute(Void nothing){

        }


    }


    private void SavedImage(){

        //파일 이름 :날짜_시간
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
        fileName = String.valueOf(sdf.format(day));
        fileName +=".jpg";


        File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/HIKVISION");


        android.util.Log.d("filepath ==", filepath.toString()+" "+filepath.exists());

        if (!filepath.exists()) {
            boolean ret = filepath.mkdirs();
            android.util.Log.d("mkdirs action success!!", "ret:"+ret);

        }

        File filespace = new File(new File("/storage/emulated/0/DCIM/HIKVISION"), fileName);
        if (filespace.exists()) {
            filespace.delete();
        }

        try {

            FileOutputStream file = new FileOutputStream(filespace);
            bit.compress(Bitmap.CompressFormat.JPEG,100,file);

            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //기본갤러리에서 저장 및 refresh
    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("/storage/emulated/0/DCIM/HIKVISION/"+fileName);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getApplicationContext().sendBroadcast(mediaScanIntent);

    }






}
