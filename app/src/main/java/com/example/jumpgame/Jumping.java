package com.example.jumpgame;

/**
 * ジャンプ状態クラス．
 */
public class Jumping implements PlayerState {
    Player player;

    public Jumping(Player player) {
        this.player = player;
    }

    /**
     * プレイヤーの状態更新メソッド．
     */
    @Override
    public void update() {
        player.getImage().setImageDrawable(player.getImageMap().get("jumping"));
    }

}
