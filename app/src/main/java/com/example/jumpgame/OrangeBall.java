package com.example.jumpgame;

import android.widget.ImageView;

/**
 * オレンジボールクラス．
 */
public class OrangeBall extends Object {
    public OrangeBall(ImageView image, int v, int screenWidth, int frameHeight) {
        super(image, v, screenWidth, frameHeight);
        this.respornX = screenWidth * 2;
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
