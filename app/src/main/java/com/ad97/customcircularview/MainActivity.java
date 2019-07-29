package com.ad97.customcircularview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ad97.cccv.CustomCornerCircularView;
import com.ad97.cccv.ModelInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private CustomCornerCircularView cornerCircularView;
    private List<ModelInfo> modelInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cornerCircularView = findViewById(R.id.cccv);
        modelInfo = new ArrayList<>();

        // adding information of the item to get menu on screen with these added items
        modelInfo.add(new ModelInfo("Message", Color.RED,R.drawable.message,R.color.colorAccent,null ));
        modelInfo.add(new ModelInfo("Mic",Color.BLUE,R.drawable.mic,R.color.colorPrimary,null ));
        modelInfo.add(new ModelInfo("Photo",Color.MAGENTA,R.drawable.photo,R.color.colorPrimaryDark,null ));
        modelInfo.add(new ModelInfo("Settings",Color.BLACK,R.drawable.settings,R.color.colorAccent,
                new Intent(MainActivity.this,NextActivity.class).putExtra("type","Settings")));

        // setting menu items from our itemList (modelInfo)
        cornerCircularView.setMenuFromList(modelInfo);
        // setting addButton color according to our choice
        cornerCircularView.setAddButtonColor(Color.BLACK);
    }
}
