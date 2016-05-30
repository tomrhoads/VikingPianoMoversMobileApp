package com.vikingpianomovers.vikingpianomoversmobileapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();

        //layout params for LinearLayout
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //Create a new LinearLayout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        //layout params for widgets
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //create a new Button widget
        Button button = new Button(this);
        button.setText("Click Me");
        button.setLayoutParams(params);


        //add the views to the Layout

        layout.addView(button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addListenerOnButton() {
        button1 = (Button) findViewById(R.id.imageButtonSelector);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent aboutPage = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(aboutPage);
            }
        });
        button2 = (Button) findViewById(R.id.imageButtonSelector2);
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent galleryPage = new Intent(MainActivity.this, GalleryActivity.class);
                MainActivity.this.startActivity(galleryPage);
            }
        });
        button3 = (Button) findViewById(R.id.imageButtonSelector3);
        button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent storagePage = new Intent(MainActivity.this, StorageActivity.class);
                MainActivity.this.startActivity(storagePage);

            }
        });
        button4 = (Button) findViewById(R.id.imageButtonSelector4);
        button4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String phoneNumber = "4152792225";
                dialPhoneNumber(phoneNumber);

            }
        });
        button5 = (Button) findViewById(R.id.imageButtonSelector5);
        button5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent quoteForm = new Intent(MainActivity.this, QuoteFormActivity.class);
                MainActivity.this.startActivity(quoteForm);
            }
        });
        button6 = (Button) findViewById(R.id.imageButtonSelector6);
        button6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String yelpSite = "http://www.yelp.com/biz/viking-piano-movers-san-rafael";
                clickYelp(yelpSite);

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialPhoneNumber(String phoneNumber){
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + phoneNumber));
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }
    public void clickYelp(String yelpSite){
        Intent y = new Intent();
        y.setAction(Intent.ACTION_VIEW);
        y.addCategory(Intent.CATEGORY_BROWSABLE);
        y.setData(Uri.parse("http://www.yelp.com/biz/viking-piano-movers-san-rafael"));
        startActivity(y);
    }
}
