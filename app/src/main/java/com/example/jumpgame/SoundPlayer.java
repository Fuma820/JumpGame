package com.example.jumpgame;

import android.content.Context;
import android.media.SoundPool;
import android.media.AudioManager;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    public SoundPlayer(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
    }

    /**
     * ヒット音を再生するメソッド．
     */
    public void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * ゲームオーバー音を再生するメソッド．
     */
    public void playOverSound() {
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
