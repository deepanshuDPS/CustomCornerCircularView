package com.ad97.cccv;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomCornerCircularView extends ConstraintLayout implements View.OnTouchListener {

    /**
     * @param viewInfoList
     * This parameter is used for creating List to store information related to menu item using ViewInfo class as generics
     * @param modelInfoList
     * This parameter is used as a List to fetch and then set the information of the menu item using ModelInfo class as generics
     * @param mContext
     * This parameter is current activity context set by constructor on creation of CustomCornerCircularView Object
     * @param constraintLayout
     * This parameter is used to get view of Constraint Layout using findViewById(..) to set view according the addButton (which is centered)
     * @param addButton
     * This parameter is used to get view of FloatingActionButton using findViewById(..) to set functions performed by this button
     * @param valueAnimator
     * This parameter used as ValueAnimator class object to handle all animations or rotations of the project
     * @param set
     * This parameter uses as ConstraintSet class object used to clone and apply different view structure according to project requirements
     */
    private List<ViewInfo> viewInfoList;
    private List<ModelInfo> modelInfoList;
    private Context mContext;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton addButton;
    private boolean close, up, clockwise;
    private ValueAnimator valueAnimator;
    private int initialX, initialY, finalX, finalY, rotation, width, height, addButtonColor, currentValue,corner_side,
            wh_array[][], onScreenIndex[] = new int[3], corner_angle_array[][] = {{90, 135, 180, 270}, {270, 225, 180, 135}, {0, 45, 90, 315}, {0, 315, 270, 45}};
    private ConstraintSet set;

    public CustomCornerCircularView(Context context) {
        super(context);
    }

    /**
     * constructor called when the object of our custom class is created
     *
     * @param context setting context of current activity
     * @param attrs   setting attributes from current xml or activity
     */
    public CustomCornerCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.CustomCornerCircularView);
        try {
            corner_side = array.getInt(R.styleable.CustomCornerCircularView_corner_side, 3);
        } finally {
            array.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_corner_circular_view, this);
        constraintLayout = findViewById(R.id.root_layout);
        set = new ConstraintSet();

        addButton = new FloatingActionButton(mContext);
        addButton.setLayoutParams(new Constraints.LayoutParams((int) getResources().getDimension(R.dimen._45sdp), (int) getResources().getDimension(R.dimen._45sdp)));
        addButton.setId(View.generateViewId());
        addButton.setImageResource(R.drawable.add);
        constraintLayout.addView(addButton);
        set.clone(constraintLayout);

        if (corner_side == 0) {
            set.connect(addButton.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, (int) getResources().getDimension(R.dimen._5sdp));
            set.connect(addButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen._5sdp));
            set.applyTo(constraintLayout);
        } else if (corner_side == 1) {
            set.connect(addButton.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, (int) getResources().getDimension(R.dimen._5sdp));
            set.connect(addButton.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, (int) getResources().getDimension(R.dimen._5sdp));
            set.applyTo(constraintLayout);
        } else if (corner_side == 2) {
            set.connect(addButton.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen._15sdp));
            set.connect(addButton.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, (int) getResources().getDimension(R.dimen._15sdp));
            set.applyTo(constraintLayout);
        } else {
            set.connect(addButton.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen._15sdp));
            set.connect(addButton.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, (int) getResources().getDimension(R.dimen._15sdp));
            set.applyTo(constraintLayout);
        }

        viewInfoList = new ArrayList<>();

        close = true;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        wh_array = new int[][]{{0, 0}, {width, 0}, {0, height}, {width, height}};
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (close) {
                    addView();
                    addButton.setImageResource(R.drawable.minus);
                    close = false;
                } else {
                    removeView();
                    addButton.setImageResource(R.drawable.add);
                    close = true;
                }
            }
        });

    }

    public CustomCornerCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method is used to set addButton background according to users choice
     *
     * @param addButtonColor used to set addButton color according to user requirements
     */
    public void setAddButtonColor(int addButtonColor) {
        this.addButtonColor = addButtonColor;
        addButton.setBackgroundTintList(ColorStateList.valueOf(this.addButtonColor));
    }

    /**
     * This method is used to add Menu List from the modelInfo List to set the with views required for menu
     *
     * @param modelInfo used to set information to viewInfoList with the menu item views (FloatingActionButton and TextView)
     */
    public void setMenuFromList(List<ModelInfo> modelInfo) {
        modelInfoList = modelInfo;
        if (modelInfo.size() == 2) {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), corner_angle_array[corner_side][0]));

            viewInfoList.add(new ViewInfo(modelInfoList.get(1).getText(), new TextView(mContext), modelInfo.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(1).getButtonBackground(),
                    modelInfoList.get(1).getButtonIcon(), corner_angle_array[corner_side][1]));

        } else if (modelInfo.size() == 1) {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), corner_angle_array[corner_side][1]));
        } else {
            viewInfoList.add(new ViewInfo(modelInfoList.get(0).getText(), new TextView(mContext), modelInfo.get(0).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(0).getButtonBackground(),
                    modelInfoList.get(0).getButtonIcon(), corner_angle_array[corner_side][0]));
            viewInfoList.add(new ViewInfo(modelInfoList.get(1).getText(), new TextView(mContext), modelInfo.get(1).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(1).getButtonBackground(),
                    modelInfoList.get(1).getButtonIcon(), corner_angle_array[corner_side][1]));
            viewInfoList.add(new ViewInfo(modelInfoList.get(2).getText(), new TextView(mContext), modelInfo.get(2).getTextColor(),
                    new FloatingActionButton(mContext), modelInfoList.get(2).getButtonBackground(),
                    modelInfoList.get(2).getButtonIcon(), corner_angle_array[corner_side][2]));
            for (int i = 3; i < modelInfo.size(); i++) {
                viewInfoList.add(new ViewInfo(modelInfoList.get(i).getText(), new TextView(mContext), modelInfo.get(i).getTextColor(),
                        new FloatingActionButton(mContext), modelInfoList.get(i).getButtonBackground(),
                        modelInfoList.get(i).getButtonIcon(), corner_angle_array[corner_side][3]));
            }
        }

    }

    /**
     * addView() method is used to add views on addButton Click
     */
    private void addView() {
        Log.i("Size", "" + viewInfoList.size());
        currentValue = 1;
        onScreenIndex = new int[]{0, 1, 2};
        for (int i = 0; i < viewInfoList.size(); i++) {
            FloatingActionButton fabTemp = viewInfoList.get(i).getActionButton();
            TextView textTemp = viewInfoList.get(i).getTextView();
            fabTemp.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(viewInfoList.get(i).getButtonBackground())));
            fabTemp.setImageResource(viewInfoList.get(i).getButtonIcon());
            fabTemp.setId(View.generateViewId());
            fabTemp.setLayoutParams(new Constraints.LayoutParams((int) getResources().getDimension(R.dimen._45sdp), (int) getResources().getDimension(R.dimen._45sdp)));
            textTemp.setId(View.generateViewId());
            textTemp.setText(viewInfoList.get(i).getText());
            textTemp.setTextColor(viewInfoList.get(i).getTextColor());
            textTemp.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (0.04 * width));
            constraintLayout.addView(fabTemp);
            constraintLayout.addView(textTemp);
            set.clone(constraintLayout);
            set.constrainCircle(fabTemp.getId(), addButton.getId(), (int) (width * 0.3), viewInfoList.get(i).getAngle());
            set.connect(textTemp.getId(), ConstraintSet.TOP, fabTemp.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(textTemp.getId(), ConstraintSet.RIGHT, fabTemp.getId(), ConstraintSet.RIGHT, 0);
            set.connect(textTemp.getId(), ConstraintSet.LEFT, fabTemp.getId(), ConstraintSet.LEFT, 0);
            set.applyTo(constraintLayout);
            viewInfoList.get(i).setId(fabTemp.getId());
            fabTemp.setOnTouchListener(this);
        }
    }

    /**
     * removeView() method is used to remove views on addButton Click
     */
    private void removeView() {
        FloatingActionButton fabTemp;
        TextView textTemp;
        for (int i = 0; i < viewInfoList.size(); i++) {
            fabTemp = viewInfoList.get(i).getActionButton();
            textTemp = viewInfoList.get(i).getTextView();
            constraintLayout.removeView(fabTemp);
            constraintLayout.removeView(textTemp);
        }
    }

    /**
     * This method is called when the button is clicked not dragged or moved by the user
     *
     * @param v used to set current view clicked and then performing function according to user requirements
     */
    private void onClickButton(View v) {

        for (int i = 0; i < viewInfoList.size(); i++) {
            if (v.getId() == viewInfoList.get(i).getId()) {
                if (modelInfoList.get(i).getIntentForActivity() != null)
                    mContext.startActivity(modelInfoList.get(i).getIntentForActivity());
                else
                    Toast.makeText(mContext, "You Clicked " + modelInfoList.get(i).getText(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * This method is overridden and defined to perform drag operations on all the menu items of the project
     *
     * @param view        current view touched
     * @param motionEvent which type of motion performed
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                initialX = (int) motionEvent.getRawX() - wh_array[corner_side][0];
                initialY = (int) motionEvent.getRawY() - wh_array[corner_side][1];
                up = true;
                break;
            case MotionEvent.ACTION_UP:
                up = false;
                finalX = (int) motionEvent.getRawX() - wh_array[corner_side][0];
                finalY = (int) motionEvent.getRawY() - wh_array[corner_side][1];
                if (initialX == finalX && initialY == initialY)
                    onClickButton(view);
                break;
            case MotionEvent.ACTION_MOVE:
                if (viewInfoList.size() > 3) {
                    finalX = (int) motionEvent.getRawX() - wh_array[corner_side][0];
                    finalY = (int) motionEvent.getRawY() - wh_array[corner_side][1];
                    rotation = (initialX * finalY - initialY * finalX);
                    if (rotation > 0 && up) {
                        up = false;
                        clockwise = true;
                    } else if (rotation < 0 && up) {
                        up = false;
                        clockwise = false;
                    }
                    if (clockwise) {
                        if (currentValue == 45) {
                            if (corner_side == 0 || corner_side == 2)
                                for (int i = 2; i >= 0; i--)
                                    onScreenIndex[i] = onScreenIndex[i] > 0 ? onScreenIndex[i] - 1 : viewInfoList.size() - 1;
                            else
                                for (int i = 0; i < 3; i++)
                                    onScreenIndex[i] = onScreenIndex[i] < viewInfoList.size() - 1 ? onScreenIndex[i] + 1 : 0;
                            currentValue = 1;
                        }
                        switch (corner_side) {
                            case 0:
                                animate(2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], 90);
                                break;
                            case 1:
                                animate(2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], -90);
                                break;
                            case 2:
                            case 3:
                                animate(2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], 0);
                        }
                    } else {
                        if (currentValue == -45) {
                            if (corner_side == 0 || corner_side == 2)
                                for (int i = 0; i < 3; i++)
                                    onScreenIndex[i] = onScreenIndex[i] < viewInfoList.size() - 1 ? onScreenIndex[i] + 1 : 0;
                            else
                                for (int i = 2; i >= 0; i--)
                                    onScreenIndex[i] = onScreenIndex[i] > 0 ? onScreenIndex[i] - 1 : viewInfoList.size() - 1;
                            currentValue = 1;
                        }
                        switch (corner_side) {
                            case 0:
                                animate(-2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], 90);
                                break;
                            case 1:
                                animate(-2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], -90);
                                break;
                            case 2:
                            case 3:
                                animate(-2, corner_angle_array[corner_side][1], corner_angle_array[corner_side][2], 0);
                        }
                    }

                }
        }
        return true;
    }


    /**
     * This method is used to perform animation using valueAnimator either it is clockwise or anticlockwise
     *
     * @param degRange degree value we want to rotate on single move/drag
     * @param angle1   angle used by different items on screen to move according to current situation on screen
     * @param angle2   angle used by different items on screen to move according to current situation on screen
     */
    private void animate(int degRange, final int angle1, final int angle2, final int ninety) {
        currentValue += degRange;
        valueAnimator = ValueAnimator.ofInt(currentValue + degRange, currentValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int val = (Integer) valueAnimator.getAnimatedValue();
                LayoutParams element1 = (LayoutParams) viewInfoList.get(onScreenIndex[0]).getActionButton().getLayoutParams();
                LayoutParams element2 = (LayoutParams) viewInfoList.get(onScreenIndex[1]).getActionButton().getLayoutParams();
                LayoutParams element3 = (LayoutParams) viewInfoList.get(onScreenIndex[2]).getActionButton().getLayoutParams();

                element1.circleAngle = val + ninety;
                element2.circleAngle = val + angle1;
                element3.circleAngle = val + angle2;
                viewInfoList.get(onScreenIndex[0]).getActionButton().setLayoutParams(element1);
                viewInfoList.get(onScreenIndex[1]).getActionButton().setLayoutParams(element2);
                viewInfoList.get(onScreenIndex[2]).getActionButton().setLayoutParams(element3);
            }
        });
        valueAnimator.start();
    }
}
