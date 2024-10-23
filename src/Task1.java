import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Task1 extends JPanel implements ActionListener {
    private double angle = 0; // Кут обертання
    private final Point start = new Point(100, 150); // Початкова точка
    private final Point end = new Point(300, 150); // Кінцева точка
    private double movingPointRatio = 0; // Відношення для руху точки
    private final double segmentLength = 50; // Довжина обертового відрізка

    public Task1() {
        Timer timer = new Timer(50, this); // Таймер з інтервалом 50 мс
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Рухома точка, яка рухається по відрізку
        int movingPointX = (int) (start.x + movingPointRatio * (end.x - start.x));
        int movingPointY = (int) (start.y + movingPointRatio * (end.y - start.y));

        // Відображення основного відрізка
        g.drawLine(start.x, start.y, end.x, end.y);
        
        // Обчислення координат обертового відрізка
        int x1 = (int) (movingPointX + segmentLength * Math.cos(angle));
        int y1 = (int) (movingPointY + segmentLength * Math.sin(angle));
        int x2 = (int) (movingPointX + segmentLength * Math.cos(angle + Math.PI));
        int y2 = (int) (movingPointY + segmentLength * Math.sin(angle + Math.PI));

        // Відображення обертового відрізка
        g.drawLine(movingPointX, movingPointY, x1, y1);
        g.drawLine(movingPointX, movingPointY, x2, y2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Зміна кута
        angle += 0.1;

        // Рух точки вздовж відрізка
        movingPointRatio += 0.01;
        if (movingPointRatio > 1) {
            movingPointRatio = 0; // Скидання, якщо досягнемо кінця відрізка
        }

        repaint(); // Оновлення малюнка
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Segment");
        Task1 task1 = new Task1();
        
        frame.add(task1);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
