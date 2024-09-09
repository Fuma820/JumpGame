package com.example.jumpgame;

import android.widget.ImageView;
import android.graphics.drawable.Drawable;

import java.util.HashMap;


/**
 * たこ焼きプレイヤークラス
 */
public class TakoyakiPlayer extends Player {
    public TakoyakiPlayer(ImageView image, HashMap<String, Drawable> imageMap, float runV0, float jumpV0, int skillMaxNum) {
        super(image, imageMap, runV0, jumpV0, skillMaxNum);
        skill = new SecondJump(this);
    }

}