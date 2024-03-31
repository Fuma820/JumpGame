package com.example.jumpgame;

import android.widget.ImageView;

import java.util.HashMap;

public class DoubleJumpPlayer extends Player {
    public DoubleJumpPlayer(ImageView image, HashMap imageMap, float runV0, float jumpV0, int skillMaxNum) {
        super(image, imageMap, runV0, jumpV0, skillMaxNum);
        skill = new SecondJump(this);
    }

}
