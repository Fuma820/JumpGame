package com.example.jumpgame;

import android.widget.ImageView;

/**
 * ゲーム内で使用されるオブジェクト抽象クラス．
 */
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

    /**
     * オブジェクトの位置を毎フレーム更新するメソッド．
     * オブジェクトが画面外に出た場合は再配置される．
     */
    public void update() {
        x -= v;
        if (x < -image.getWidth()) {
            x = respornX;
            y = (float) Math.floor(Math.random() * (frameHeight - image.getHeight()));
        }
        image.setX(x);
        image.setY(y);
    }

    /**
     * オブジェクトが衝突した際のアクションを処理する抽象メソッド．
     *
     * @param soundPlayer 衝突時の音を再生するためのサウンドプレイヤー
     */
    public abstract void hitAction(SoundPlayer soundPlayer);

    /**
     * オブジェクトの現在のx座標を取得するメソッド．
     *
     * @return オブジェクトのx座標
     */
    public float getX() {
        return x;
    }

    /**
     * オブジェクトの現在のy座標を取得するメソッド．
     *
     * @return オブジェクトのy座標
     */
    public float getY() {
        return y;
    }

    /**
     * オブジェクトの移動速度を取得するメソッド．
     *
     * @return オブジェクトの移動速度
     */
    public int getV() {
        return v;
    }

    /**
     * オブジェクトのImageViewを取得するメソッド．
     *
     * @return オブジェクトのImageView
     */
    public ImageView getImage() {
        return this.image;
    }

}
