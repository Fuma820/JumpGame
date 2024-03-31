package com.example.jumpgame;

public class Falling implements PlayerState{
    Player player;
    public Falling(Player player){
        this.player=player;
    }
    @Override
    public void update(){
        player.getImage().setImageDrawable(player.getImageMap().get("falling"));
    }
}
