package com.example.ballcatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ゲーム画面を表すActivity
 */
public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;// スコアを表示するテキスト
    private TextView startLabel;// タップしてスタートと表示するテキスト
    private ImageView box;// プレイヤーを表す正方形
    private ImageView orange;// 10点のボール
    private ImageView pink;// 30点のボール
    private ImageView black;// ゲームオーバーになるボール

    private int frameHeight;// フレームの高さ
    private int boxSize;// プレイヤーのサイズ
    private int screenWidth;// ゲーム画面の横幅
    private int screenHeight;// ゲーム画面の縦幅

    private float boxY;// プレイヤーのy座標
    private float orangeX;// オレンジボールのx座標
    private float orangeY;// オレンジボールのy座標
    private float pinkX;// ピンクボールのx座標
    private float pinkY;// ピンクボールのy座標
    private float blackX;// ブラックボールのx座標
    private float blackY;// ブラックボールのy座標

    private int boxSpeed;// プレイヤーの速度
    private int orangeSpeed;// オレンジボールのスピード
    private int pinkSpeed;// ピンクボールのスピード
    private int blackSpeed;// ブラックボールのスピード

    private int score = 0;// 得点

    // Handlerはスレッド間の通信をすためのクラス
    private Handler handler = new Handler();
    // Timerはバックグラウンドスレッドでタスクをスケジュールするクラス
    private Timer timer = new Timer();

    private boolean action_flg = false;// 画面がタッチされているか判定
    private boolean start_flg = false;// ゲームがスタートしているか判定するメソッド

    private SoundPlayer soundPlayer;

    /**
     * Activity作成時のメソッド
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        // Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point point = new Point();// (0, 0)でpointオブジェクト生成
        display.getSize(point);// Pointオブジェクトに画面のサイズを格納する
        screenWidth = point.x;
        screenHeight = point.y;

        boxSpeed = Math.round(screenHeight / 60f);// スクリーンの1/60を四捨五入
        orangeSpeed = Math.round(screenWidth / 60f);
        pinkSpeed = Math.round(screenWidth / 36f);
        blackSpeed = Math.round(screenWidth / 45f);

        orange.setX(-50f);// ViewのsetXメソッドでViewの位置を設定
        orange.setY(-50f);
        pink.setX(-50f);
        pink.setY(-50f);
        black.setX(-50f);
        black.setY(-50f);

        scoreLabel.setText("Score : 0");// 得点の初期値
    }

    /**
     * 位置の変更メソッド
     */
    public void changePos() {
        hitCheck();

        // Orange
        orangeX -= orangeSpeed;// 左に移動
        if (orangeX < 0) {
            orangeX = screenWidth + 10;//ここの数字を大きくすると画面内に戻ってくるまでに時間がかかる
            // ボールが見える範囲でランダムにボールを移動
            orangeY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Black
        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 20;
            blackY = (float)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        // Pink
        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (float)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        // Box
        if (action_flg) {// クリックされている場合
            boxY -= boxSpeed;// 上に移動
        } else {// クリックされていない場合
            boxY += boxSpeed;// 下に移動
        }
        // 画面外には移動しない
        if (boxY < 0) boxY = 0;
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText("Score : " + score);// 得点を更新
    }

    /**
     * プレイヤーの当たり判定時のメソッド
     */
    public void hitCheck() {
        // Orange
        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY + orange.getHeight() / 2;

        if (hitStatus(orangeCenterX, orangeCenterY)) {
            orangeX = -10f;
            score += 10;
            soundPlayer.playHitSound();
        }

        // Pink
        float pinkCenterX = pinkX + pink.getWidth() / 2;
        float pinkCenterY = pinkY + pink.getHeight() / 2;

        if (hitStatus(pinkCenterX, pinkCenterY)) {
            pinkX = -10.0f;
            score += 30;
            soundPlayer.playHitSound();
        }

        // Black
        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if (hitStatus(blackCenterX, blackCenterY)) {
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
    }

    /**
     * プレイヤーのView内に引数の座標が含まれるか判定するメソッド
     * @param centerX
     * @param centerY
     * @return
     */
    public boolean hitStatus(float centerX, float centerY) {
        return (0 <= centerX && centerX <= boxSize &&
                boxY <= centerY && centerY <= boxY + boxSize) ? true : false;
    }

    /**
     * 画面がタッチされた時のメソッド
     * @param event The touch screen event being processed.
     *
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg == false) {// ゲームがスタートしていない場合
            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();// フレームの高さを取得

            boxY = box.getY();
            boxSize = box.getHeight();

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
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }
        else {// ゲームがスタートしている場合
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }
}
