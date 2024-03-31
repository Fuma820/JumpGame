package com.example.jumpgame;

import android.widget.ImageView;

public class BlackBall extends Object {
    public BlackBall(ImageView image, int v, int screenWidth, int frameHeight) {
        super(image, v, screenWidth, frameHeight);
        this.respornX = screenWidth * 10;
    }

    @Override
    public void hitAction(SoundPlayer soundPlayer) {
        soundPlayer.playHitSound();
    }

}
