package com.example.jumpgame;

import android.widget.ImageView;

public class NormalBlock extends Object{
    public NormalBlock(ImageView image, int v, int screenWidth, int frameHeight) {
        super(image, v, screenWidth, frameHeight);
        this.respornX = screenWidth * 2;
    }

    @Override
    public void hitAction(SoundPlayer soundPlayer){}
}
