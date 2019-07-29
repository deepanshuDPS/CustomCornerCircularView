package com.ad97.cccv;

import android.content.Intent;
import android.support.annotation.Nullable;

public class ModelInfo {

    private String text;
    private int buttonBackground,textColor;
    private int buttonIcon;
    private Intent intentForActivity;

    /**
     * constructor is used to set all information of the menu item according to user requirements
     * @param text text for the TextView
     * @param textColor color for the TextViewColor
     * @param buttonIcon icon for the FloatingActionButtonIcon
     * @param buttonBackground background for the FloatingActionButton
     * @param intentForActivity intent for the activity to start that activity if null then it will generate toast
     */
    public ModelInfo(String text,int textColor ,int buttonIcon ,int buttonBackground, @Nullable Intent intentForActivity) {
        this.text = text;
        this.textColor = textColor;
        this.buttonIcon = buttonIcon;
        this.buttonBackground = buttonBackground;
        this.intentForActivity = intentForActivity;
    }


    /**
     * all these methods are getters of this class used to fetch information according to project
     */

    public String getText() {
        return text;
    }

    public int getButtonBackground() {
        return buttonBackground;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getButtonIcon() {
        return buttonIcon;
    }

    public Intent getIntentForActivity() {
        return intentForActivity;
    }
}
