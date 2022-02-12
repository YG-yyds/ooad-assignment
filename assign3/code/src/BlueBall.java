import java.awt.*;

public class BlueBall extends Ball {
    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        setXSpeed(-getXSpeed());
        setYSpeed(-getYSpeed());
    }

    @Override
    public void update(Ball ball) {
        if(isIntersect(ball)){
            setXSpeed(-getXSpeed());
            setYSpeed(-getYSpeed());
        }
    }
}
