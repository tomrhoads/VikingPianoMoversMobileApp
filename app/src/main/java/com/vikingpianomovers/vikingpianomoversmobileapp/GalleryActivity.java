package com.vikingpianomovers.vikingpianomoversmobileapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Created by Tom on 5/19/2015.
 */



public class GalleryActivity extends Activity {

    private ImageAdapter mImageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        // Create a new ImageAdapter (subclass of FragmentStatePagerAdapter)
        // ViewPager uses support library, so use getSupportFragmentManager()
        // instead of getFragmentManager()
        mImageAdapter = new ImageAdapter(getFragmentManager());

        // Set the Adapter on the ViewPager
        mViewPager.setAdapter(mImageAdapter);

    }
}


