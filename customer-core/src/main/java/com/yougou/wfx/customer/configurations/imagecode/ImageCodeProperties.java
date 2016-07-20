package com.yougou.wfx.customer.configurations.imagecode;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 图片配置
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 上午12:01
 * @since 1.0 Created by lipangeng on 16/3/25 上午12:01. Email:lipg@outlook.com.
 */
@ConfigurationProperties("com.yougou.imagecode")
public class ImageCodeProperties {
    /** 验证码图片的宽度。 */
    private int width = 112;

    /** 验证码图片的高度。 */
    private int height = 32;

    /** 验证码字符个数 */
    private int codeCount = 4;

    /** x轴坐标 */
    private int codeX = width / (codeCount + 2);

    /** 字体高度 */
    private int fontHeight = height - 2;

    /** Y轴坐标 */
    private int codeY = height - 5;

    /** 干扰线数量 */
    private int lines = 4;

    /** 可选择模板 */
    private String[] codeSequence = {"1",
                                     "2",
                                     "3",
                                     "4",
                                     "5",
                                     "6",
                                     "7",
                                     "8",
                                     "9",
                                     "a",
                                     "b",
                                     "c",
                                     "d",
                                     "e",
                                     "f",
                                     "g",
                                     "h",
                                     "i",
                                     "j",
                                     "k",
                                     "l",
                                     "m",
                                     "n",
                                     "o",
                                     "p",
                                     "q",
                                     "r",
                                     "s",
                                     "t",
                                     "u",
                                     "v",
                                     "w",
                                     "x",
                                     "y",
                                     "z",
                                     "A",
                                     "B",
                                     "C",
                                     "D",
                                     "E",
                                     "F",
                                     "J",
                                     "P",
                                     "Q",
                                     "R",
                                     "S",
                                     "T",
                                     "U",
                                     "V",
                                     "W",
                                     "X",
                                     "Y",
                                     "Z"};

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(int codeCount) {
        this.codeCount = codeCount;
    }

    public int getCodeX() {
        return codeX;
    }

    public void setCodeX(int codeX) {
        this.codeX = codeX;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
    }

    public int getCodeY() {
        return codeY;
    }

    public void setCodeY(int codeY) {
        this.codeY = codeY;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public String[] getCodeSequence() {
        return codeSequence;
    }

    public void setCodeSequence(String[] codeSequence) {
        this.codeSequence = codeSequence;
    }
}
