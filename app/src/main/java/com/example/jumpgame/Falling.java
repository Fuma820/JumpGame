package com.example.jumpgame;

/**
 * 落下状態クラス．
 */
public class Falling implements PlayerState {
    Player player;

    public Falling(Player player) {
        this.player = player;
    }

    /**
     * プレイヤーの状態更新メソッド．
     */
    @Override
    public void update() {
        player.getImage().setImageDrawable(player.getImageMap().get("falling"));
    }

}
