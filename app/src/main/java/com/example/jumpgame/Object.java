package com.example.jumpgame;

import android.widget.ImageView;

public abstract class Object {
    protected ImageView image;
    protected float x;
    protected float y;
    protected int v;
    int respornX;
    int screenWidth;
    int frameHeight;

    public Object(ImageView image, int v, int screenWidth, int frameHeight) {
        this.image = image;
        this.screenWidth = screenWidth;
        this.frameHeight = frameHeight;
        this.v = v;
        x = -image.getWidth();
    }

    public void update() {
        x -= v;
        if (x < 0 - image.getWidth()) {
            x = respornX;
            y = (float) Math.floor(Math.random() * (frameHeight - image.getHeight()));
        }
        image.setX(x);
        image.setY(y);
    }

    public abstract void hitAction(SoundPlayer soundPlayer);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getV() {
        return v;
    }

    public ImageView getImage() {
        return this.image;
    }

}
