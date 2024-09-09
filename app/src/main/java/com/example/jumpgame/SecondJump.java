package com.example.jumpgame;

/**
 * 2段ジャンプスキルクラス．
 */
public class SecondJump implements Skill {
    private final Player player;

    public SecondJump(Player player) {
        this.player = player;
    }

    /**
     * スキル使用時のアクションメソッド．
     */
    public void action() {
        player.setVY(-player.getV0());
    }

}
