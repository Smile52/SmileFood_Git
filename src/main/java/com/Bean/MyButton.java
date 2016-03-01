package com.Bean;

/**按钮实体
 * Created by 九龙 on 2015/11/13.
 */
public class MyButton {
    private int imageId;
    private String text;

    public MyButton(int imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }
}
