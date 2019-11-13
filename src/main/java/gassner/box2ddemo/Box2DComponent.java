package gassner.box2ddemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.*;
import java.awt.*;

public class Box2DComponent extends JComponent
{
    private long time;

    private final World world;
    private Body ball1, ball2, ball3;
    private Body ground;
    private Body roof;
    private Body wall1, wall2;

    public Box2DComponent()
    {
        World.setVelocityThreshold(0);
        world = new World(new Vector2(0, 9.8f), false);

        ground = createWall(100f, 700f, 600f, 1f);
        roof = createWall(100f, 100f, 600f, 1f);
        wall1 = createWall(100f, 100f, 1f, 600f);
        wall2 = createWall(700f, 100f, 1f, 600f);
        ball1 = createBall(100, 150, 40, 100, 70);
        ball2 = createBall(150, 150, 30, 100, -50);
        ball3 = createBall(250, 350, 65, 100, -100);

        time = System.currentTimeMillis();
    }

    private Body createWall(float vX, float vY, float hX, float hY)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(vX, vY));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body wall = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hX, hY);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        wall.createFixture(fixtureDef);
        return wall;
    }

    private Body createBall(int vX, int vY, int radius, int forceX, int forceY)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(vX, vY));
        Body ball = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;
        ball.createFixture(fixtureDef);

        ball.applyForceToCenter(forceX, forceY, true);

        return ball;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        long currentTime = System.currentTimeMillis();

        world.step((currentTime - time)/1000f, 6, 2);
        time = currentTime;

        graphics.fillOval((int) ball1.getPosition().x,
                (int) ball1.getPosition().y,
                40, 40);

        graphics.fillOval((int) ball2.getPosition().x,
                (int) ball2.getPosition().y,
                30, 30);

        graphics.fillOval((int) ball3.getPosition().x,
                (int) ball3.getPosition().y,
                65, 65);

        graphics.fillRect((int)ground.getPosition().x,
                (int)ground.getPosition().y,
                600, 1);

        graphics.fillRect((int)roof.getPosition().x,
                (int)roof.getPosition().y,
                600, 1);

        graphics.fillRect((int)wall1.getPosition().x,
                (int)wall1.getPosition().y,
                1, 600);

        graphics.fillRect((int)wall2.getPosition().x,
                (int)wall2.getPosition().y,
                1, 600);

        repaint();
    }
}
