package game;

import javax.swing.*;
import java.awt.*;

public class NumberButton extends JButton {
    private Location location;
    private int number;
    private Color backgroundColor;
    private Color textColor;

    public NumberButton(Location location, int number, Color backgroundColor, Color textColor) {
        this(number, backgroundColor, textColor);
        this.location = location;
        super.setBounds(location.getxLocation(), location.getyLocation(), 100, 100);
    }

    public NumberButton(int number, Color backgroundColor, Color textColor) {
        this.number = number;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        super.setPreferredSize(new Dimension(100, 100));
        super.setOpaque(true);
        super.setBorderPainted(false);
        super.setBackground(backgroundColor);
        super.setForeground(textColor);
        super.setMargin(new Insets(0, 0, 0, 0));
        super.setFont(new Font("Arial", Font.BOLD, 24));
        if( number > 0) {
            super.setText(Integer.toString(number));
        }

    }

    public Location getButtonLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
}
