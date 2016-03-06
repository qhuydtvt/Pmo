package vn.edu.techkids.pomodoro.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by qhuydtvt on 12/11/2015.
 */
public class TaskColor {

    private String name;
    private String value;

    public static String VALUE_INDIGO = "#3F51B5";
    public static String VALUE_CYAN = "#00BCD4";
    public static String VALUE_TEAL = "#009688";
    public static String VALUE_LIGHT_GREEN = "#8BC34A";
    public static String VALUE_AMBER = "#FFC107";
    public static String VALUE_RED = "#F44336";
    public static String VALUE_PINK = "#E91E63";
    public static String VALUE_PURPLE = "#9C27B0";
    public static String VALUE_DEEP_PURPLE = "#673AB7";
    public static String VALUE_LIME = "#CDDC39";
    public static String VALUE_BROWN = "#795548";
    public static String VALUE_GREY = "#9E9E9E";

    private TaskColor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /*public static final String[] ALL_COLORS = new String[]{
            VALUE_LIGHT_GREEN,
            VALUE_RED,
            VALUE_PINK,
            VALUE_PURPLE,
            VALUE_DEEP_PURPLE,
            VALUE_INDIGO,
            VALUE_LIME,
            VALUE_BROWN,
            VALUE_GREY
    };*/


    public static final TaskColor[] ALL_TASK_COLORS = new TaskColor[] {
            new TaskColor("Indigo", VALUE_INDIGO),
            new TaskColor("Cyan", VALUE_CYAN),
            new TaskColor("Teal", VALUE_TEAL),
            new TaskColor("Light Green", VALUE_LIGHT_GREEN),
            new TaskColor("Amber", VALUE_AMBER),
            new TaskColor("Red", VALUE_RED),
            new TaskColor("Pink", VALUE_PINK),
            new TaskColor("Purple", VALUE_PURPLE),
            new TaskColor("Deep purple", VALUE_DEEP_PURPLE),
            new TaskColor("Lime", VALUE_LIME),
            new TaskColor("Brown", VALUE_BROWN),
            new TaskColor("Grey", VALUE_GREY)
    };

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static int indexOf(String color) {
        Logger.d("indexOf", "color = " + color);
        for (int i = 0; i < ALL_TASK_COLORS.length; i++) {
            if (ALL_TASK_COLORS[i].value.equalsIgnoreCase(color)) {
                return i;
            }
        }
        return 0;
    }

    public static void setBackground(View view, String color) {
        Drawable dBackground = view.getBackground();
        dBackground.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);
    }
}
