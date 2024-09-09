package com.example.jumpgame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ゲームの結果を表示するActivityクラス．
 */
public class ResultActivity extends AppCompatActivity {
    /**
     * Activity生成時メソッド．
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");
        //　アプリ内でデータを共有するためのオブジェクト(MODE_PRIVATEは自アプリのみ読み書き可能)
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (score > highScore) {// 得点がハイスコアより高い場合
            highScoreLabel.setText("High Score : " + score);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);// ハイスコアを格納
            editor.apply();// 編集したデータを適用(非同期)

        } else {// 得点がハイスコア以下の場合
            highScoreLabel.setText("High Score : " + highScore);
        }
    }

    /**
     * もう一度ゲームを行う(Main画面へ戻る)メソッド．
     *
     * @param view メソッドが呼ばれたビュー
     */
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), SelectActivity.class));
    }

}
