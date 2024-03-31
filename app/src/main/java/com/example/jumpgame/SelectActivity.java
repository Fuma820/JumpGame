package com.example.jumpgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity {
    private String playerSelect;// 次のActivityに渡すplayerの値
    private ImageButton player01Button;
    private ImageButton player02Button;
    /**
     * アクティビティ作成時のメソッド
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        playerSelect = "NotSelected";
        player01Button = findViewById(R.id.player01_select);
        player02Button = findViewById(R.id.player02_select);
    }

    /**
     * ゲームスタート
     *
     * @param view
     */
    public void startGame(View view) {
        if (playerSelect.equals("NotSelected")) {
            Context context = getApplicationContext();
            CharSequence text = "プレイヤーが選択されていません";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("PLAYER", playerSelect);
            startActivity(intent);
        }
    }

    /**
     * playerを設定するメソッド
     *
     * @param view
     */
    public void setPlayer(View view) {
        if (view.getId() == R.id.player01_select) {
            playerSelect = "DoubleJumpPlayer";
            player01Button.setBackground(getResources().getDrawable(R.drawable.shape_outer_select_frame));
            player02Button.setBackground(getResources().getDrawable(R.drawable.shape_outer_frame));// ID || 配列　で管理したい
        } else if (view.getId() == R.id.player02_select) {
            playerSelect = "TakoyakiPlayer";
            player02Button.setBackground(getResources().getDrawable(R.drawable.shape_outer_select_frame));
            player01Button.setBackground(getResources().getDrawable(R.drawable.shape_outer_frame));
        }
    }

}
