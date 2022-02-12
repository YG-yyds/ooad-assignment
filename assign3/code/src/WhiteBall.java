import java.awt.*;
import java.util.ArrayList;

public class WhiteBall extends Ball implements Subject<Ball> {
    ArrayList<Ball> observerList = new ArrayList<>();

    public WhiteBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    public void reset() {
        observerList = new ArrayList<>();
    }

    @Override
    public void update(char keyChar) {
        if (MainPanel.getGameStatus() == MainPanel.GameStatus.START) {
            switch (keyChar) {
                case 'a':
                    setXSpeed(-8);
                    break;
                case 'd':
                    setXSpeed(8);
                    break;
                case 'w':
                    setYSpeed(-8);
                    break;
                case 's':
                    setYSpeed(8);
                    break;
                default:
                    break;
            }
            notifyObservers();
        }
    }

    @Override
    public void update(Ball ball) {

    }

    @Override
    public void registerObserver(Ball ball) {
        observerList.add(ball);
    }

    @Override
    public void removeObserver(Ball ball) {
        for (int i = 0; i < observerList.size(); i++) {
            if (observerList.get(i).equals(ball)) {
                observerList.remove(i);
                break;
            }
        }
    }

    @Override
    public void notifyObservers(char keyChar) {
        for (Ball ball : observerList) {
            ball.update(keyChar);
        }
    }

    @Override
    public void notifyObservers() {
        for (Ball ball : observerList) {
            ball.update(this);
        }
    }
}
