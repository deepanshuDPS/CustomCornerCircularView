package com.ad97.cccv;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

public class ViewInfo {

    private String text;
    private FloatingActionButton actionButton;
    private TextView textView;
    private int buttonBackground, angle, buttonIcon, id, textColor;


    /**
     * constructor is used to set all information of the menu item
     * @param text             used as a text to setText of the TextView
     * @param textView         used as the TextView reference for the menu item
     * @param textColor        used as text color to set text color of the TextView
     * @param actionButton     used as the FloatingActionButton reference for the menu item
     * @param buttonBackground used as button background to set background of FloatingActionButton for the menu item
     * @param buttonIcon       used as button icon to set FloatingActionButton icon for the menu item
     * @param angle            used to set angle for the view of the menu item
     */
    public ViewInfo(String text, TextView textView, int textColor, FloatingActionButton actionButton, int buttonBackground, int buttonIcon, int angle) {
        this.text = text;
        this.textView = textView;
        this.textColor = textColor;
        this.actionButton = actionButton;
        this.buttonBackground = buttonBackground;
        this.buttonIcon = buttonIcon;
        this.angle = angle;
    }

    /**
     * used to set the ids of the views after generating view ids
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * all other methods are getters of this class used to fetch information according to project
     */

    public String getText() {
        return text;
    }

    public FloatingActionButton getActionButton() {
        return actionButton;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getButtonBackground() {
        return buttonBackground;
    }

    public int getAngle() {
        return angle;
    }

    public int getButtonIcon() {
        return buttonIcon;
    }

    public int getId() {
        return id;
    }

    public int getTextColor() {
        return textColor;
    }
}
