package com.example.jumpgame;

public class Standing implements PlayerState{
    Player player;
    public Standing(Player player){
        this.player=player;
    }
    @Override
    public void update(){
        player.resetSkillNum();// スキルの回数をリセット
        player.setSkillFlg(false);//スキルフラグをリセット
        player.getImage().setImageDrawable(player.getImageMap().get("standing"));
    }
}
