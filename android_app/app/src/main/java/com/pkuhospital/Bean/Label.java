package com.pkuhospital.Bean;

/**
 * 个人中心界面的标签
 * @author 杨洲
 * @time  2019.12.5
 */
public class Label {
    private String labelText;
    private int labelIcon;

    public Label(){}

    public Label(String text,int icon){
        this.labelText = text;
        this.labelIcon = icon;
    }

    public String getLabelText(){
        return labelText;
    }

    public int getLabelIcon() {
        return labelIcon;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public void setLabelIcon(int labelIcon) {
        this.labelIcon = labelIcon;
    }
}
