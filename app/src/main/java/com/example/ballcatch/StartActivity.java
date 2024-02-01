package com.example.ballcatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * スタート画面を表すActivity
 */
public class StartActivity extends AppCompatActivity {
    /**
     * Activity生成時のメソッド
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    /**
     * ゲームをスタートするメソッド
     * @param view
     */
    public void startGame(View view) {
        // MainActivityへ移動
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
