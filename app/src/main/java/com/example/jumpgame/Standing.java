package com.example.jumpgame;

/**
 * 立っている状態クラス．
 */
public class Standing implements PlayerState {
    Player player;

    public Standing(Player player) {
        this.player = player;
    }

    /**
     * プレイヤーの状態を更新するメソッド．
     */
    @Override
    public void update() {
        player.resetSkillNum();// スキルの回数をリセット
        player.setSkillFlg(false);//スキルフラグをリセット
        player.getImage().setImageDrawable(player.getImageMap().get("standing"));
    }

}
