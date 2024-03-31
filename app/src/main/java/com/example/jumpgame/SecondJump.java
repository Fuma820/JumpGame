package com.example.jumpgame;

public class SecondJump implements Skill {
    private Player player;
    private int maxNum;

    public SecondJump(Player player) {
        this.player = player;
    }

    public void action() {
        player.setVY(-player.getV0());
    }
}
