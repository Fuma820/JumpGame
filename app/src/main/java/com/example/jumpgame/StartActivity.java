package com.example.jumpgame;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * スタート画面を表すActivityクラス．
 */
public class StartActivity extends AppCompatActivity {
    /**
     * Activity生成時のメソッド
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    /**
     * プレイヤー選択画面への遷移メソッド．
     *
     * @param view 　このメソッドを呼び出したView
     */
    public void selectPlayer(View view) {
        startActivity(new Intent(getApplicationContext(), SelectActivity.class));
    }

}
