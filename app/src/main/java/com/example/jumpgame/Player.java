package com.example.jumpgame;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.HashMap;

public abstract class Player {
    private ImageView image;
    private HashMap<String, Drawable> imageMap;
    private float x;
    private float y;
    private float runV0;
    private float jumpV0;
    private float vX = 0;
    private float vY = 0;
    private int skillMaxNum;
    private int skillNum = 0;
    private boolean skillFlg;
    private int score = 0;
    public final PlayerState STANDING = new Standing(this);
    public final PlayerState JUMPING = new Jumping(this);
    public final PlayerState FALLING = new Falling(this);
    private PlayerState playerState = FALLING;
    protected Skill skill;

    public Player(ImageView image, HashMap imageMap, float runV0, float jumpV0, int skillMaxNum) {
        this.image = image;
        this.imageMap = imageMap;
        this.skillMaxNum = skillMaxNum;
        this.skillFlg = false;
        this.jumpV0 = jumpV0;
        this.runV0 = runV0;
        this.x = image.getX();
        this.y = image.getY();
    }

    /**
     * フレームが切り替わる時に情報を更新するメソッド
     * @param action_flg
     * @param screenWidth
     * @param frameHeight
     * @param gravity
     */
    public void update(boolean action_flg, int screenWidth, int frameHeight, float gravity) {
        vX = runV0;
        // 画面半分で速度が0になる
        if (x + image.getWidth() >= screenWidth / 2) {
            vX = 0;
        }

        vY += gravity;
        if (!action_flg) {// 画面が押されていない場合
            if (frameHeight <= y + image.getHeight()) {// 地面にいる場合
                playerState = STANDING;
                vY = 0;
            } else if (vY < 0) {// 上昇中
                //vY = 0;// 小ジャンプ
                playerState = JUMPING;
            } else {// 落下中
                playerState = FALLING;
            }
            skillFlg = true;
        } else {// 画面が押されている場合
            if (y >= frameHeight - image.getHeight()) {// 地面にいる場合
                playerState = STANDING;
                vY = -jumpV0;
            } else {// 空中にいる場合
                if (skillFlg == true && skillNum > 0) {
                    skill.action();
                    skillNum--;
                }
                if (vY < 0) {// 上昇中
                    playerState = JUMPING;
                } else {// 落下中
                    playerState = FALLING;
                }
            }
        }

        playerState.update();
    }

    /**
     * 引数のオブジェクトに当たっているか確認するためのメソッド
     * @param object
     * @return
     */
    public boolean isHit(Object object) {
        float xPos = object.getX();
        float yPos = object.getY();
        int width = object.getImage().getWidth();
        int height = object.getImage().getHeight();
        boolean ret = false;
        if ((x <= xPos && xPos <= x + image.getWidth() || x <= xPos + width && xPos + width <= x + image.getWidth())
                && (y <= yPos && yPos <= y + image.getHeight() || y <= yPos + height && yPos + height <= y + image.getHeight())) {
            ret = true;
        }
        return ret;
    }

    /**
     * 次のフレームでブロックに当たる時どの方向から当たるか取得するメソッド
     * @param block
     * @param nextHitDirection
     * @return
     */
    public String getHitDirection(Object block, String nextHitDirection) {
        float nextPlayerRight = x + vX + image.getWidth();
        float nextBlockLeft = block.getX() - block.getV();
        float nextY = y + vY;
        float yPos = block.getY();
        int height = block.getImage().getHeight();

        String ret = nextHitDirection;
        //次のフレームでぶつかる場合
        if ((nextBlockLeft <= nextPlayerRight) && (nextY <= yPos && yPos <= nextY + image.getHeight() || nextY <= yPos + height && yPos + height <= nextY + image.getHeight())) {
            if (x + image.getWidth() <= block.getX()) {// 現在はx方向が重なっていない
                ret = "left";
            }
            if (y + image.getHeight() <= yPos) {// 現在はy方向が重ねっていない
                ret = "top";
            } else if (yPos + height <= y) {
                ret = "bottom";
            }
        }
        else{
            ret="";
        }
        return ret;
    }

    /**
     * オレンジボールと当たった時の動作
     */
    public void hitOrange() {
        score += 10;
    }

    /**
     * ピンクボールと当たった時の動作
     */
    public void hitPink() {
        score += 30;
    }

    /**
     * ブロックに当たった時の動作
     * @param block
     * @param hitDirection
     * @param action_flg
     * @param gravity
     */
    public void hitBlock(Object block, String hitDirection, boolean action_flg, float gravity) {
        if (hitDirection.equals("left")) {// 横から当たった場合
            vX = 0;
            x = block.getX() - image.getWidth();
        } else if (hitDirection.equals("top")) {// ブロックの上にいる場合
            playerState = STANDING;
            if (action_flg) {
                vY = -jumpV0;
            } else {
                vY = 0;
            }
            y = block.getY() - image.getHeight();
        } else if (hitDirection.equals("bottom")) {// 下から当たった場合
            playerState = FALLING;
            vY = gravity;
            y = block.getY() + block.getImage().getHeight();
        }
        playerState.update();
    }

    /**
     * 画像の位置をフィールドの座標と同期させるメソッド
     * @param screenWidth
     * @param frameHeight
     */
    public void setImage(int screenWidth, int frameHeight) {
        x += vX;
        y += vY;
        if (frameHeight - image.getHeight() < y) {
            y = frameHeight - image.getHeight();
        }
        image.setX(x);
        image.setY(y);
    }

    /**
     * スキルの利用回数をリセットするメソッド
     */
    public void resetSkillNum() {
        this.skillNum = skillMaxNum;
    }

    //　ゲッター
    public int getScore() {
        return score;
    }

    public float getV0() {
        return jumpV0;
    }

    public ImageView getImage() {
        return this.image;
    }

    public HashMap<String, Drawable> getImageMap() {
        return this.imageMap;
    }

    // セッター
    public void setVY(float vY) {
        this.vY = vY;
    }

    public void setSkillFlg(boolean flg) {
        this.skillFlg = flg;
    }

}
