package gassner.box2ddemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.*;
import java.awt.*;

public class Box2DComponent extends JComponent
{
    private long time;

    private final World world;
    private Body ball;
    private Body ground;

    public Box2DComponent()
    {
        World.setVelocityThreshold(0);
        world = new World(new Vector2(0, 9.8f), false);

        createBall();
        createGround();

        time = System.currentTimeMillis();
    }

    private void createGround()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(-100f, 200f));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(200f, 1f);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        ground.createFixture(fixtureDef);
    }

    private void createBall()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        ball = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20);
        fixtureDef.shape = shape;
        ball.createFixture(fixtureDef);

        ball.applyForceToCenter(100, -30, true);
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        long currentTime = System.currentTimeMillis();

        world.step((currentTime - time)/1000f, 6, 2);
        time = currentTime;

        graphics.fillOval((int) ball.getPosition().x,
                (int) ball.getPosition().y,
                20, 20);

        graphics.fillRect((int)ground.getPosition().x,
                (int)ground.getPosition().y,
                200, 1);

        repaint();
    }
}
