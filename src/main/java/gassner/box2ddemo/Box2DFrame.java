package gassner.box2ddemo;

import javax.swing.*;
import java.awt.*;

public class Box2DFrame extends JFrame
{
    public Box2DFrame()
    {
        setSize(1200, 1000);
        setTitle("Box 2D Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(new Box2DComponent(), BorderLayout.CENTER);
    }

    public static void main(String[] args)
    {
        new Box2DFrame().setVisible(true);
    }
}
