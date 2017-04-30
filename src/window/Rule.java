package window;

import java.awt.*;
import javax.swing.*;

public class Rule extends JComponent {

    public static final int INCH = Toolkit.getDefaultToolkit().
            getScreenResolution();
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int SIZE = 35;

    public int orientation;
    private int increment;
    private int units;

    public Rule(int o, boolean m) {
        orientation = o;
        setIncrementAndUnits();
    }

    public void setIsMetric(boolean isMetric) {
        setIncrementAndUnits();
        repaint();
    }

    private void setIncrementAndUnits() {
        units = 10;
        increment = units * 10;
    }

    public int getIncrement() {
        return increment;
    }

    public void setPreferredHeight(int ph) {
        setPreferredSize(new Dimension(SIZE, ph));
    }

    public void setPreferredWidth(int pw) {
        setPreferredSize(new Dimension(pw, SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();

        // Fill clipping area with dirty brown/orange.
        g.setColor(new Color(230, 163, 4));
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        // Do the ruler labels in a small font that's black.
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(Color.black);

        // Some vars we need.
        int end = 0;
        int start = 0;
        int tickLength = 0;
        String text = null;

        // Use clipping bounds to calculate first and last tick locations.
        if (orientation == HORIZONTAL) {
            start = (drawHere.x / increment) * increment;
            end = (((drawHere.x + drawHere.width) / increment) + 1)
                    * increment;
        } else {
            start = (drawHere.y / increment) * increment;
            end = (((drawHere.y + drawHere.height) / increment) + 1)
                    * increment;
        }

        // Make a special case of 0 to display the number
        // within the rule and draw a units label.
        if (start == 0) {
            tickLength = 10;
            if (orientation == HORIZONTAL) {
                g.drawLine(0, SIZE - 1, 0, SIZE - tickLength - 1);
            } else {
                g.drawLine(SIZE - 1, 0, SIZE - tickLength - 1, 0);
            }
            start = increment;
        }

        // ticks and labels
        for (int i = start; i < end; i += increment) {
            if (i % units == 0) {
                tickLength = 10;
                text = Integer.toString(i / units);
            } else {
                tickLength = 7;
                text = null;
            }

            if (tickLength != 0) {
                if (orientation == HORIZONTAL) {
                    g.drawLine(i, SIZE - 1, i, SIZE - tickLength - 1);
                    if (text != null) {
                        g.drawString(text, i - 3, 21);
                    }
                } else {
                    g.drawLine(SIZE - 1, i, SIZE - tickLength - 1, i);
                    if (text != null) {
                        g.drawString(text, 9, i + 3);
                    }
                }
            }
        }
    }
}