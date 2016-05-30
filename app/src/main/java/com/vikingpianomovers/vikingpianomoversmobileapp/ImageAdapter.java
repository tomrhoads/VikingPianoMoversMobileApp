package com.vikingpianomovers.vikingpianomoversmobileapp;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

// Manages Fragments holding ImageViews
public class ImageAdapter extends FragmentStatePagerAdapter {

    // List of IDs corresponding to the images
    private Integer[] mImageIds = { R.drawable.simg1, R.drawable.simg2,
            R.drawable.simg3, R.drawable.simg4, R.drawable.simg5,
            R.drawable.simg6, R.drawable.simg7 };

    public ImageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ImageHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ImageHolderFragment.RES_ID, mImageIds[i]);
        args.putString(ImageHolderFragment.POS, String.valueOf(i));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

}