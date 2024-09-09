package com.example.jumpgame;

import java.util.HashMap;

import android.widget.ImageView;
import android.graphics.drawable.Drawable;

/**
 * プレイヤークラス．
 */
public abstract class Player {
    private final ImageView image;
    private final HashMap<String, Drawable> imageMap;
    private float x;
    private float y;
    private final float runV0;
    private final float jumpV0;
    private float vX = 0;
    private float vY = 0;
    private final int skillMaxNum;
    private int skillNum = 0;
    private boolean skillFlg;
    private int score = 0;
    public final PlayerState STANDING = new Standing(this);
    public final PlayerState JUMPING = new Jumping(this);
    public final PlayerState FALLING = new Falling(this);
    private PlayerState playerState = FALLING;
    protected Skill skill;

    public Player(ImageView image, HashMap<String, Drawable> imageMap, float runV0, float jumpV0, int skillMaxNum) {
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
     * 得点のゲッターメソッド．
     *
     * @return プレイヤーの得点
     */
    public int getScore() {
        return score;
    }

    /**
     * 初速度のゲッターメソッド．
     *
     * @return プレイヤーの初速度
     */
    public float getV0() {
        return jumpV0;
    }

    /**
     * 画像のゲッターメソッド．
     *
     * @return プレイヤーのImageView
     */
    public ImageView getImage() {
        return this.image;
    }

    /**
     * プレイヤーの画像マップを取得するメソッド．
     * 画像マップはキーとしてプレイヤーの状態を表す文字列を使用し，
     * 対応するDrawableオブジェクトを保持する．
     *
     * @return プレイヤーの画像マップを表すHashMap<String, Drawable>
     */
    public HashMap<String, Drawable> getImageMap() {
        return this.imageMap;
    }

    /**
     * 垂直方向の速度セッターメソッド．
     *
     * @param vY 垂直方向の速度
     */
    public void setVY(float vY) {
        this.vY = vY;
    }

    /**
     * スキルが使用可能であるかのフラグセッターメソッド．
     *
     * @param flg スキルが使用可能であるかのフラグ
     */
    public void setSkillFlg(boolean flg) {
        this.skillFlg = flg;
    }


    /**
     * フレームが切り替わる時に情報を更新するメソッド．
     *
     * @param action_flg  画面が押されているかどうかを示すフラグ
     * @param screenWidth 画面の横幅
     * @param frameHeight 画面の縦幅
     * @param gravity     重力
     */
    public void update(boolean action_flg, int screenWidth, int frameHeight, float gravity) {
        vX = runV0;
        // 画面半分で速度が0になる
        if (x + image.getWidth() >= screenWidth / 2.0) {
            vX = 0;
        }

        vY += gravity;
        if (!action_flg) {// 画面が押されていない場合
            if (frameHeight <= y + image.getHeight()) {// 地面にいる場合
                playerState = STANDING;
                vY = 0;
            } else if (vY < 0) {// 上昇中
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
                if (skillFlg && skillNum > 0) {
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
     * 引数のオブジェクトとの衝突判定メソッド．
     *
     * @param object ヒット確認対象のオブジェクト
     * @return 衝突したかの判定
     */
    public boolean isHit(Object object) {
        float xPos = object.getX();
        float yPos = object.getY();
        int width = object.getImage().getWidth();
        int height = object.getImage().getHeight();
        return (x <= xPos && xPos <= x + image.getWidth() || x <= xPos + width && xPos + width <= x + image.getWidth())
                && (y <= yPos && yPos <= y + image.getHeight() || y <= yPos + height && yPos + height <= y + image.getHeight());
    }

    /**
     * 次のフレームでブロックに衝突した際、どの方向から衝突するかを判定するメソッド．
     *
     * @param block            衝突対象のブロックオブジェクト
     * @param nextHitDirection 前回のフレームでの衝突方向
     * @return 次のフレームでの衝突方向を文字列(" left ", " top ", " bottom ", " ")
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
        } else {
            ret = "";
        }
        return ret;
    }

    /**
     * オレンジボールと当たった時の動作メソッド．
     */
    public void hitOrange() {
        score += 10;
    }

    /**
     * ピンクボールと当たった時の動作メソッド．
     */
    public void hitPink() {
        score += 30;
    }

    /**
     * プレイヤーがブロックに衝突した際の動作を処理するメソッド．
     * 衝突の方向に応じてプレイヤーの速度や位置，状態を更新する．
     *
     * @param block        衝突したブロックオブジェクト
     * @param hitDirection 衝突方向（"left", "top", "bottom"）
     * @param action_flg   画面が押されているかどうかを示すフラグ
     * @param gravity      重力
     */
    public void hitBlock(Object block, String hitDirection, boolean action_flg, float gravity) {
        switch (hitDirection) {
            case "left": // 横から当たった場合
                vX = 0;
                x = block.getX() - image.getWidth();
                break;
            case "top": // ブロックの上にいる場合
                playerState = STANDING;
                if (action_flg) {
                    vY = -jumpV0;
                } else {
                    vY = 0;
                }
                y = block.getY() - image.getHeight();
                break;
            case "bottom": // 下から当たった場合
                playerState = FALLING;
                vY = gravity;
                y = block.getY() + block.getImage().getHeight();
                break;
        }
        playerState.update();
    }

    /**
     * 画像の位置をフィールドの座標と同期させるメソッド．
     *
     * @param frameHeight 画面の縦幅
     */
    public void setImage(int frameHeight) {
        x += vX;
        y += vY;
        if (frameHeight - image.getHeight() < y) {
            y = frameHeight - image.getHeight();
        }
        image.setX(x);
        image.setY(y);
    }

    /**
     * スキルの利用回数をリセットするメソッド．
     */
    public void resetSkillNum() {
        this.skillNum = skillMaxNum;
    }

}
