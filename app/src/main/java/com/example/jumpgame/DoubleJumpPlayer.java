package com.example.jumpgame;

import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 2段ジャンプできるプレイヤークラス
 */
public class DoubleJumpPlayer extends Player {
    public DoubleJumpPlayer(ImageView image, HashMap<String, Drawable> imageMap, float runV0, float jumpV0, int skillMaxNum) {
        super(image, imageMap, runV0, jumpV0, skillMaxNum);
        skill = new SecondJump(this);
    }

}
