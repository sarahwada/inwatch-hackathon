package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private Context context;

    // TODO(yujun): I put the enum here so I could use it in the SensorHandler, but feel free to move it. - Sarah
    public enum UserAction {
        PUSH, PULL, TWIST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        context = this;
//        SensorHandler handler = new SensorHandler(context);


        ImageView startGame = (ImageView) findViewById(R.id.start_game);
        startGame.setImageResource(R.drawable.ic_action_play);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });

    }

}
