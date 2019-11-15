package gassner.box2ddemo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.*;
import java.awt.*;

public class Box2DComponent extends JComponent
{
    private static final float BOX_TO_SCREEN = 10f;
    private static final float SCREEN_TO_BOX = 1f / BOX_TO_SCREEN;

    private long time;

    private final World world;
    private Body ball1, ball2, ball3;
    private Body ground;
    private Body roof;
    private Body wall1, wall2;
    private int radius1, radius2, radius3;

    Box2DComponent()
    {
        World.setVelocityThreshold(0);
        world = new World(new Vector2(0, 9.8f * SCREEN_TO_BOX), false);

        radius1 = 40;
        radius2 = 30;
        radius3 = 65;

        ground = createWall(100f * SCREEN_TO_BOX, 700f * SCREEN_TO_BOX, 600f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        roof = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 600f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        wall1 = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, 600f * SCREEN_TO_BOX);
        wall2 = createWall(700f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, 600f * SCREEN_TO_BOX) ;
        ball1 = createBall(100 * SCREEN_TO_BOX, 150 * SCREEN_TO_BOX, radius1 * SCREEN_TO_BOX, 100, 100);
        ball2 = createBall(150 * SCREEN_TO_BOX, 150 * SCREEN_TO_BOX, radius2 * SCREEN_TO_BOX, 10, -50);
        ball3 = createBall(250 * SCREEN_TO_BOX, 350 * SCREEN_TO_BOX, radius3 * SCREEN_TO_BOX, 1000, -100);

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
        fixtureDef.restitution = 3;
        wall.createFixture(fixtureDef);
        return wall;
    }

    private Body createBall(float vX, float vY, float radius, int forceX, int forceY)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(vX, vY));
        Body ball = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
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

        //width and height of the ball have to be double its radius.
        graphics.fillOval((int) (ball1.getPosition().x * BOX_TO_SCREEN - radius1),
                (int) (ball1.getPosition().y * BOX_TO_SCREEN - radius1),
                radius1 * 2, radius1 * 2);

        graphics.fillOval((int) (ball2.getPosition().x * BOX_TO_SCREEN - radius2),
                (int) (ball2.getPosition().y * BOX_TO_SCREEN - radius2),
                radius2 * 2, radius2 * 2);

        graphics.fillOval((int) (ball3.getPosition().x * BOX_TO_SCREEN - radius3),
                (int) (ball3.getPosition().y * BOX_TO_SCREEN - radius3),
                radius3 * 2, radius3 * 2);

        graphics.fillRect((int)(ground.getPosition().x * BOX_TO_SCREEN),
                (int)(ground.getPosition().y * BOX_TO_SCREEN),
                600, 1);

        graphics.fillRect((int)(roof.getPosition().x * BOX_TO_SCREEN),
                (int)(roof.getPosition().y * BOX_TO_SCREEN),
                600, 1);

        graphics.fillRect((int)(wall1.getPosition().x * BOX_TO_SCREEN),
                (int)(wall1.getPosition().y * BOX_TO_SCREEN),
                1, 600);

        graphics.fillRect((int)(wall2.getPosition().x * BOX_TO_SCREEN),
                (int)(wall2.getPosition().y * BOX_TO_SCREEN),
                1, 600);

        repaint();
    }
}
