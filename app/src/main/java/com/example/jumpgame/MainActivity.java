package com.example.jumpgame;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ゲーム画面を表すActivity
 */
public class MainActivity extends AppCompatActivity {
    private TextView scoreLabel;// スコアを表示するテキスト
    private TextView startLabel;// タップしてスタートと表示するテキスト
    private ImageView orangeBallImage;// 10点のボール
    private ImageView pinkBallImage;// 30点のボール
    private ImageView blackBallImage;// ゲームオーバーになるボール
    private ImageView player01Image;
    private ImageView player02Image;
    private ImageView block01Image;
    private HashMap<String, Drawable> player01List;
    private HashMap<String, Drawable> player02List;

    private final Handler handler = new Handler();// Handlerはスレッド間の通信をするためのクラス
    private Timer timer = new Timer();// Timerはバックグラウンドスレッドでタスクをスケジュールするクラス
    private SoundPlayer soundPlayer;

    private Player player;
    private Object blackBall;
    private Object pinkBall;
    private Object orangeBall;
    private Object normalBlock;

    private String nextHitDirection;

    private int frameHeight;
    private int screenWidth;

    private float gravity;
    private int score = 0;// 得点

    private boolean action_flg = false;// 画面がタッチされているか判定
    private boolean start_flg = false;

    /**
     * Activity作成時メソッド
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);
        // Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        // プレイヤーの画像
        player01Image = findViewById(R.id.player01_standing);
        player01List = new HashMap<>();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_standing01 = getResources().getDrawable(R.drawable.player01_standing);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_jumping01 = getResources().getDrawable(R.drawable.player01_standing);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_falling01 = getResources().getDrawable(R.drawable.player01_falling);
        player01List.put("standing", drawable_standing01);
        player01List.put("jumping", drawable_jumping01);
        player01List.put("falling", drawable_falling01);
        //プレイヤーの画像
        player02Image = findViewById(R.id.player02_standing);
        player02List = new HashMap<>();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_standing02 = getResources().getDrawable(R.drawable.player02_standing);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_jumping02 = getResources().getDrawable(R.drawable.player02_standing);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable_falling02 = getResources().getDrawable(R.drawable.player02_standing);
        player02List.put("standing", drawable_standing02);
        player02List.put("jumping", drawable_jumping02);
        player02List.put("falling", drawable_falling02);

        // オブジェクト
        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        orangeBallImage = findViewById(R.id.orange);
        pinkBallImage = findViewById(R.id.pink);
        blackBallImage = findViewById(R.id.black);
        block01Image = findViewById(R.id.block01);
        // ViewのsetメソッドでViewの位置を設定
        blackBallImage.setX(-screenWidth);
        orangeBallImage.setX(-screenWidth);
        pinkBallImage.setX(-screenWidth);
        block01Image.setX(-screenWidth);
        player01Image.setX(-screenWidth);
        player02Image.setX(-screenWidth);

        scoreLabel.setText("Score : 0");
    }

    /**
     * フレームが切り替わる時に情報を更新するメソッド
     */
    @SuppressLint("SetTextI18n")
    public void update() {
        player.update(action_flg, screenWidth, frameHeight, gravity);
        orangeBall.update();
        pinkBall.update();
        blackBall.update();
        normalBlock.update();
        nextHitDirection = player.getHitDirection(normalBlock, nextHitDirection);
        if (player.isHit(orangeBall)) {
            player.hitOrange();
            orangeBall.hitAction(soundPlayer);
        }
        if (player.isHit(pinkBall)) {
            player.hitPink();
            pinkBall.hitAction(soundPlayer);
        }
        if (player.isHit(normalBlock)) {
            player.hitBlock(normalBlock, nextHitDirection, action_flg, gravity);
        }
        if (player.isHit(blackBall)) {
            // Game Over!
            if (timer != null) {
                timer.cancel();
                timer = null;
                soundPlayer.playOverSound();
            }
            // 結果画面へ
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE", score);// 得点を次のActivityに渡す
            startActivity(intent);
        }
        player.setImage(frameHeight);
        score = player.getScore();
        scoreLabel.setText("Score : " + score);
    }

    /**
     * 画面がタッチされた時のメソッド
     *
     * @param event The touch screen event being processed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!start_flg) {
            start_flg = true;
            gravity = 3;
            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            switch (Objects.requireNonNull(getIntent().getStringExtra("PLAYER"))) {
                case "DoubleJumpPlayer":
                    player01Image.setX(0);
                    player = new DoubleJumpPlayer(player01Image, player01List, 2, 60, 1);
                    break;
                case "TakoyakiPlayer":
                    player02Image.setX(0);
                    player = new TakoyakiPlayer(player02Image, player02List, 2, 60, 1);
                    break;
            }
            blackBall = new BlackBall(blackBallImage, 20, screenWidth, frameHeight);
            orangeBall = new OrangeBall(orangeBallImage, 10, screenWidth, frameHeight);
            pinkBall = new PinkBall(pinkBallImage, 30, screenWidth, frameHeight);
            normalBlock = new NormalBlock(block01Image, 5, screenWidth, frameHeight);

            startLabel.setVisibility(View.GONE);// Viewを非表示にする
            timer.schedule(new TimerTask() {// 20msごとにTimerTaskを繰り返す
                /**
                 * 指定された時間に実行されるメソッド
                 */
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        /**
                         * 指定された時間に実行されるメソッド
                         * TimerがHandlerを呼び出し，HandlerがchangePosメソッドを呼び出す
                         */
                        @Override
                        public void run() {
                            update();
                        }
                    });
                }
            }, 0, 20);
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }

}