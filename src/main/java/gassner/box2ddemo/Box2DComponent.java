package gassner.box2ddemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.*;
import java.awt.*;

public class Box2DComponent extends JComponent
{
    private final World world;

    public Box2DComponent()
    {
        world = new World(new Vector2(0, 9.8f), false);
    }


    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        world.step(1/60f, 6, 2);

        graphics.drawLine(0 , 0, getWidth(), getHeight());
    }
}
