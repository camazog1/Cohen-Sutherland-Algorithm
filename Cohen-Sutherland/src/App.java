import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        Canvas c1 = new Canvas();
        Panel p1 = new Panel();
        c1.add(p1);
        c1.setVisible(true);
        c1.setLocationRelativeTo(null);
        c1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c1.setTitle("Clipping algorithm (Cohen-Sutherland)");
    }
}