package com.vikingpianomovers.vikingpianomoversmobileapp;

/**
 * Created by Tom on 5/19/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuoteFormActivity extends Activity {

    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    private static final int ACTION_TAKE_VIDEO = 3;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private Bitmap mImageBitmap;

    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
    private VideoView mVideoView;
    private Uri mVideoUri;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
        mVideoUri = null;
        mImageView.setVisibility(View.VISIBLE);
        mVideoView.setVisibility(View.INVISIBLE);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);
        mVideoUri = null;
        mImageView.setVisibility(View.VISIBLE);
        mVideoView.setVisibility(View.INVISIBLE);
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void handleCameraVideo(Intent intent) {
        mVideoUri = intent.getData();
        mVideoView.setVideoURI(mVideoUri);
        mImageBitmap = null;
        mVideoView.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.INVISIBLE);
    }

    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
                }
            };

    Button.OnClickListener mTakePicSOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
                }
            };

    Button.OnClickListener mTakeVidOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakeVideoIntent();
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_form);


        final EditText name = (EditText) findViewById(R.id.EditTextName);
        final EditText emailAddress = (EditText) findViewById(R.id.EditTextEmail);
        final EditText phoneNumber = (EditText) findViewById(R.id.EditPhoneNumber);
        final EditText makeModel = (EditText) findViewById(R.id.EditTextMakeModel);
        final EditText pianoSize = (EditText) findViewById(R.id.EditTextPianoSize);
        final EditText cityOrigin = (EditText) findViewById(R.id.EditTextCityOrigin);
        final EditText cityDest = (EditText) findViewById(R.id.EditTextCityDest);
        final EditText stairs = (EditText) findViewById(R.id.EditTextStairs);
        final EditText additionalComments = (EditText) findViewById(R.id.editTextComments);


        //button that sends data from form to user's email
        Button email = (Button) findViewById(R.id.SubmitButton);
        email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent to send email

                Intent email = new Intent(Intent.ACTION_SEND);

                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"vikingpianomovers@yahoo.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Viking Android App Submission");
                email.putExtra(Intent.EXTRA_TEXT,
                        "name: " + name.getText().toString() + '\n' + '\n' +
                                "Email Address: " + emailAddress.getText().toString() + '\n' + '\n' +
                                "Phone Number: " + phoneNumber.getText().toString() + '\n' + '\n' +
                                "Make and Model: " + makeModel.getText().toString() + '\n' + '\n' +
                                "Piano Size: " + pianoSize.getText().toString() + '\n' + '\n' +
                                "City of Origin: " + cityOrigin.getText().toString() + '\n' + '\n' +
                                "City Destination: " + cityDest.getText().toString() + '\n' + '\n' +
                                "Number of stairs: " + stairs.getText().toString() + '\n' + '\n' +
                                "Additional Info: " + additionalComments.getText().toString()
                );
                startActivity(Intent.createChooser(email, "Send mail..."));
            }
        });
    }
}



















