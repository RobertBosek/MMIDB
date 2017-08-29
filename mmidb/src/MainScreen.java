import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainScreen extends JFrame{

    private String title = "DB-Administration";
    private static int x = 0, y = 0, width = 400, height = 400;
    private String screen;
    private Font myFont = new Font("Tahoma", Font.BOLD, 13);

    private Container container;
    private JPanel screenContainer;

    private JTextField feedback;
    private JLabel lblHeadquater = new JLabel("Adresse der Zentrale:");
    private JLabel street = new JLabel("St.");
    private JLabel avenue = new JLabel("Av.");
    private JLabel

    public static void main (String[] args) {
        new MainScreen();
    }

    public MainScreen(){
        setTitle(title);
        this.setScreen();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setComponents();

        setVisible(true);
    }

    private void setComponents() {
        container = getContentPane();
        container.setLayout(new BorderLayout());
        feedback = new JTextField();
        feedback.setHorizontalAlignment(JTextField.CENTER);
        feedback.setBorder(new LineBorder(Color.BLACK));
        feedback.setEditable(false);
        container.add(feedback, BorderLayout.SOUTH);
        screenContainer = new JPanel();
        container.add(screenContainer, BorderLayout.CENTER);



        switch (screen) {
            case "manager":
                this.addComponentsManager();
                break;
            case "driver":
                this.addComponentsDriver();
                break;
            default:
                this.addComponentsMain();
                break;
        }

    }

    private void addComponentsManager() {
    }

    private void addComponentsDriver() {
    }

    private void addComponentsMain() {
    }

    private void setScreen() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        x = (int) (d.getWidth() - width) / 2;
        y = (int) (d.getHeight() - height) / 2;
        setBounds(x, y, width, height);
    }
}
