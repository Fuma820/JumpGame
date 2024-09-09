package com.example.jumpgame;

import android.widget.ImageView;

/**
 * ピンクボールクラス．
 */
public class PinkBall extends Object {
    public PinkBall(ImageView image, int v, int screenWidth, int frameHeight) {
        super(image, v, screenWidth, frameHeight);
        this.respornX = screenWidth * 10;
    }

    /**
     * 衝突時アクションメソッド．
     *
     * @param soundPlayer 衝突時の音を再生するためのサウンドプレイヤー
     */
    @Override
    public void hitAction(SoundPlayer soundPlayer) {
        x -= screenWidth;
        image.setX(x);
        soundPlayer.playHitSound();
    }

}

