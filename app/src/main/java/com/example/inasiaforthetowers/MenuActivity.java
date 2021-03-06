package com.example.inasiaforthetowers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    public static int musicCounter = 1;
    Intent bgm;
    Button singleStart;
    Button doubleStart;
    Button checkScore;
    ImageButton goToSetting;
    ImageButton musicSetting;
    private String userID = null;
    private AlertDialog dialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.menu);

        //Main menu button choices
        singleStart = findViewById(R.id.startGame1);
        singleStart.setOnClickListener(this);

        doubleStart = findViewById(R.id.startGame2);
        doubleStart.setOnClickListener(this);

        checkScore = findViewById(R.id.checkScore);
        checkScore.setOnClickListener(this);

        //Setting icon button for setting ID
        goToSetting = findViewById(R.id.gameSetting);
        goToSetting.setOnClickListener(this);

        musicSetting = findViewById(R.id.musicSetting);
        musicSetting.setOnClickListener(this);

    }

    //This part is for the setting pop up
    private EditText newID;

    // Pause the music when exiting app
    public static int getMusicCounter() {
        return musicCounter;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startGame1) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.checkScore) {
//            Intent intent = new Intent(this, HighScore.class);
//            startActivity(intent);
        } else if (view.getId() == R.id.startGame2) {
//            Intent intent = new Intent(this, PlayDouble.class);
//            startActivity(intent);
        } else if (view.getId() == R.id.gameSetting) {
            createNewIDDialogue(this);
        } else if (view.getId() == R.id.musicSetting) {
            if (musicCounter == 1) {
                stopService(new Intent(this, Menu_music.class));
                musicSetting.setBackgroundResource(R.drawable.setting_music2);
                musicCounter = 0;
            } else if (musicCounter == 0) {
                startService(new Intent(this, Menu_music.class));
                musicSetting.setBackgroundResource(R.drawable.setting_music);
                musicCounter = 1;
            }
        }

    }
    // End of setting pop up

    @SuppressLint("SetTextI18n")
    public void createNewIDDialogue(MenuActivity menu) {
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        final View IDPopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        newID = IDPopUpView.findViewById(R.id.enterNewID);
        Button settingPopUpCancel = IDPopUpView.findViewById(R.id.cancelSetting);
        Button settingPopUpSave = IDPopUpView.findViewById(R.id.saveSetting);
        TextView showID = IDPopUpView.findViewById(R.id.showCurrentID);
        showID.setText("ID: " + userID);

        dialogueBuilder.setView(IDPopUpView);
        dialog = dialogueBuilder.create();
        dialog.show();

        settingPopUpSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = newID.getText().toString();
                dialog.dismiss();
            }
        });

        settingPopUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (musicCounter == 1) {
            stopService(new Intent(this, Menu_music.class));
        }
    }

    @Override
    protected void onResume() {
        if (musicCounter == 1) {
            startService(new Intent(this, Menu_music.class));
        }
        super.onResume();
    }

}