package com.sqisoft.remote.manager;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.sqisoft.remote.domain.LocalImageObject;
import com.sqisoft.remote.domain.SelfieZoneDomain;
import com.sqisoft.remote.domain.ServerGalleryImageDomain;
import com.squareup.picasso.Callback;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by SQISOFT on 2016-10-11.
 */
public class DataManager {
    private static DataManager instance = null;

    public Bitmap bitmap = null;
    public ArrayList<SelfieZoneDomain> selfieZoneDomains = new ArrayList<SelfieZoneDomain>();
    public ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains = new ArrayList<ServerGalleryImageDomain>();
    public ArrayList<LocalImageObject> localImageObjects = new ArrayList<LocalImageObject>();
    public String currentImageURL;
    public String currentImageTitle;


    private DataManager(){

    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap (){
        return  this.bitmap;
    }



    public ArrayList<LocalImageObject> getLocalImageObjects() {
        return localImageObjects;
    }

    public void setLocalImageObjects(ArrayList<LocalImageObject> localImageObjects) {
        this.localImageObjects = localImageObjects;
    }

    public ArrayList<ServerGalleryImageDomain> getServerGalleryImageDomains() {
        return serverGalleryImageDomains;
    }

    public void setServerGalleryImageDomains(ArrayList<ServerGalleryImageDomain> serverGalleryImageDomains) {
        this.serverGalleryImageDomains = serverGalleryImageDomains;
    }


    public ArrayList<SelfieZoneDomain> getSelfieZoneDomains() {
        return selfieZoneDomains;
    }

    public void setSelfieZoneDomains(ArrayList<SelfieZoneDomain> selfieZoneDomains) {
        this.selfieZoneDomains = selfieZoneDomains;
    }


    public String getCurrentImageTitle() {
        return currentImageTitle;
    }

    public void setCurrentImageTitle(String currentImageTitle) {
        this.currentImageTitle = currentImageTitle;
    }



    public String getCurrentCurrentImageURL() {
        return currentImageURL;
    }

    public void setCurrentCurrentImageURL(String currentImageURL) {
        this.currentImageURL = currentImageURL;
    }

    public Callback getImageLoadedCallback(final ImageView imageView) {
        final Callback imageLoadedCallback = new Callback() {
            PhotoViewAttacher mAttacher;

            @Override
            public void onSuccess() {
                if (mAttacher != null) {
                    mAttacher.update();
                } else {
                    mAttacher = new PhotoViewAttacher(imageView);
                }
            }

            @Override
            public void onError() {
                // TODO Auto-generated method stub
            }
        };
            return imageLoadedCallback;
    }


}


