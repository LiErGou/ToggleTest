package com.example.administrator.toggletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ToggleView toggleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleView=(ToggleView)findViewById(R.id.toggleview);

//        toggleView.setSlideButton(R.drawable.slide_button);
//
//        toggleView.setSwitchBackground(R.drawable.switch_background);
//
//        toggleView.setState(true);

        toggleView.setSwitchChangeListener(new ToggleView.OnSwitchChangeListener() {
            @Override
            public void onStateChange(boolean state) {
                Toast.makeText(MainActivity.this,"state:"+state,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
