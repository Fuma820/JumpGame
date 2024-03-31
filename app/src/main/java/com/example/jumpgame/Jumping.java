package com.example.jumpgame;

public class Jumping implements PlayerState{
    Player player;
    public Jumping(Player player){
        this.player=player;
    }
    @Override
    public void update(){
        player.getImage().setImageDrawable(player.getImageMap().get("jumping"));
    }
}
