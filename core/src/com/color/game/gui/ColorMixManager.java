package com.color.game.gui;


import com.badlogic.gdx.graphics.Color;
import com.color.game.elements.staticelements.platforms.ElementColor;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ColorMixManager {

    public static Color getAdditionOf(Color a, Color b){

        if(a == Color.BLUE){
            if(b == Color.BLUE){
                return Color.BLUE;
            }else if(b == Color.RED){
                return Color.PURPLE;
            }else if(b == Color.YELLOW){
                return Color.GREEN;
            }
        }else if(a == Color.RED){
            if(b == Color.BLUE){
                return Color.PURPLE;
            }else if(b == Color.RED){
                return Color.RED;
            }else if(b == Color.YELLOW){
                return Color.ORANGE;
            }
        }else if(a == Color.YELLOW){
            if(b == Color.BLUE){
                return Color.GREEN;
            }else if(b == Color.RED){
                return Color.ORANGE;
            }else if(b == Color.YELLOW){
                return Color.YELLOW;
            }
        }

        return Color.BLACK;
    }

    public static ElementColor getElementColorFromGDX(Color color){
        if(Objects.equals(color, Color.BLUE)){
            return ElementColor.BLUE;
        }
        if(Objects.equals(color, Color.RED)){
            return ElementColor.RED;
        }
        if(Objects.equals(color, Color.YELLOW)){
            return ElementColor.YELLOW;
        }
        if(Objects.equals(color, Color.PURPLE)){
            return ElementColor.PURPLE;
        }
        if(Objects.equals(color, Color.ORANGE)){
            return ElementColor.ORANGE;
        }
        if(Objects.equals(color, Color.GREEN)){
            return ElementColor.GREEN;
        }

        return ElementColor.BLACK;
    }

    public static Color randomizeRYBColor(){
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);

        Random random = new Random();

        int firstColor = random.nextInt(3);
        int secondColor = random.nextInt(3);
        while(secondColor == firstColor){
            secondColor = random.nextInt(3);
        }

        return getAdditionOf(colors.get(firstColor), colors.get(secondColor));
    }

    public static boolean contains(ElementColor firstColor, ElementColor secondColor){
        if(firstColor == secondColor){
            return true;
        }else {
            switch (firstColor) {
                case GREEN:
                    return (secondColor == ElementColor.BLUE || secondColor == ElementColor.YELLOW);
                case ORANGE:
                    return (secondColor == ElementColor.RED || secondColor == ElementColor.YELLOW);
                case PURPLE:
                    return (secondColor == ElementColor.BLUE || secondColor == ElementColor.RED);
                case BLACK:
                    return (secondColor == ElementColor.BLUE || secondColor == ElementColor.YELLOW ||
                            secondColor == ElementColor.RED || secondColor == ElementColor.PURPLE ||
                            secondColor == ElementColor.ORANGE || secondColor == ElementColor.GREEN);

            }
        }

        return false;
    }
}
