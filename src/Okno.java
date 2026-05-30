import javax.swing.*;
import java.awt.*;

public class Okno extends JFrame {

    Hra hra;

    public static final int sirkaDlazdice = 120;
    public static final int vyskaDlazdice = 120;

    private JLabel zobrazeniScore;
    private Ovladani ovladani;

    public Okno(Hra hra) {
        this.hra = hra;
        setup();
        setTitle("2 0 4 8");
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void vlozitOvladani(Ovladani ovladani){
        this.ovladani = ovladani;
    }

    private void setup(){

        this.setLayout(new BorderLayout());
        zobrazeniScore = new JLabel("Skóre: " + hra.getSkore(), SwingConstants.CENTER);
        JPanel herniPanel = new JPanel();
        int velikost = hra.getHraciPole().getVelikost();

        zobrazeniScore.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        zobrazeniScore.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(zobrazeniScore,BorderLayout.NORTH);

        herniPanel.setLayout(new GridLayout(velikost, velikost, 5, 5));
        herniPanel.setBackground(new Color(0, 0, 0));
        herniPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(herniPanel, BorderLayout.CENTER);
    }
}

