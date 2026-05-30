import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Okno extends JFrame {

    Hra hra;

    public static final int sirkaDlazdice = 120;
    public static final int vyskaDlazdice = 120;

    private JLabel zobrazeniScore;
    private Dlazdice dlazdice [][];
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

        dlazdice = new Dlazdice[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                dlazdice[i][j] = new Dlazdice();
                herniPanel.add(dlazdice[i][j]);
            }
        }
        this.add(herniPanel, BorderLayout.CENTER);

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if( e.getKeyCode()==KeyEvent.VK_UP ) {
                    ovladani.tahOdUzivatele(Hra.nahoru);
                }

                if( e.getKeyCode()==KeyEvent.VK_DOWN ) {
                    ovladani.tahOdUzivatele(Hra.dolu);
                }

                if( e.getKeyCode()==KeyEvent.VK_LEFT ) {
                    ovladani.tahOdUzivatele(Hra.vLevo);
                }

                if( e.getKeyCode()==KeyEvent.VK_RIGHT ) {
                    ovladani.tahOdUzivatele(Hra.vPravo);
                }
            }
        });
    }
    public void obnoveniHracihoPole(HraciPole pole, int skore){
        int [][] hraciPole = pole.getHraciPole();
        for(int i = 0; i < hra.getHraciPole().getVelikost(); i++){
            for (int j = 0; j < hra.getHraciPole().getVelikost(); j++) {
                dlazdice[i][j].nastavitPodleCisla(hraciPole[i][j]);
            }
        }
        zobrazeniScore.setText("Skóre: " + skore);

    }

    public void vysledekHry(String string){
        JOptionPane.showMessageDialog(this, string, "KONEC HRY!", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new HlavniMenu().setVisible(true);

    }
}

