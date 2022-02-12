import java.awt.*;
import java.util.Random;

public class RedBall extends Ball{

    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }
    Random random = new Random();

    @Override
    public void update(char keyChar) {
        switch (keyChar) {
            case 'a':
                setXSpeed(-random.nextInt(3) - 1);
                break;
            case 'd':
                setXSpeed(random.nextInt(3) + 1);
                break;
            case 'w':
                setYSpeed(-random.nextInt(3) - 1);
                break;
            case 's':
                setYSpeed(random.nextInt(3) + 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(Ball ball) {
        if (isIntersect(ball)){
            setXSpeed(ball.getXSpeed());
            setYSpeed(ball.getYSpeed());
        }
    }
}