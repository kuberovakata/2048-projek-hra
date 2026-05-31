import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Okno extends JFrame {

    Hra hra;

    private int prvniRadek = -1;
    private int prvniSloupec = -1;
    Color pruhledna = new java.awt.Color(0, 0, 0, 115);

    public static final int sirkaDlazdice = 120;
    public static final int vyskaDlazdice = 120;

    private JLabel zobrazeniScore;
    private Dlazdice dlazdice [][];
    private Ovladani ovladani;

    public void vlozitOvladani(Ovladani ovladani){
        this.ovladani = ovladani;
    }

    public Okno(Hra hra) {

        this.hra = hra;
        setup();
        setTitle("2 0 4 8");

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                UIManager.put("OptionPane.background", Color.BLACK);
                UIManager.put("Panel.background", Color.BLACK);
                UIManager.put("OptionPane.messageForeground", Color.WHITE);

                int volba = JOptionPane.showConfirmDialog(
                        Okno.this,
                        "Opravdu chceš ukončit rozehranou hru a vrátit se do menu?",
                        "Ukončit hru?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (volba == JOptionPane.YES_OPTION) {
                    Okno.this.dispose();

                    EventQueue.invokeLater(() -> {
                        HlavniMenu menu = new HlavniMenu();
                        menu.setVisible(true);
                    });
                }
            }
        });

        pack();
        setResizable(false);
        setVisible(true);
    }

    private void setup(){

        this.setLayout(new BorderLayout());
        int velikost = hra.getHraciPole().getVelikost();

        zobrazeniScore = new JLabel("   Skóre: " + hra.getSkore(), SwingConstants.LEFT);
        JPanel herniPanel = new JPanel();

        zobrazeniScore.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        zobrazeniScore.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        zobrazeniScore.setOpaque(true);
        zobrazeniScore.setBackground(new Color(40, 32, 44, 255));
        zobrazeniScore.setForeground(new Color(224, 233, 243));
        this.getContentPane().setBackground(Color.BLACK);
        this.add(zobrazeniScore,BorderLayout.NORTH);

        herniPanel.setLayout(new GridLayout(velikost, velikost, 5, 5));
        herniPanel.setBackground(new Color(0, 0, 0));
        herniPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dlazdice = new Dlazdice[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                dlazdice[i][j] = new Dlazdice();
                herniPanel.add(dlazdice[i][j]);

               int radek = i;
               int sloupec = j;

                dlazdice[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        zpracovaniKliknuti(radek, sloupec);
                    }
                });
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

    private void zpracovaniKliknuti(int radek, int sloupec) {

        if (hra.getBonus()<1){
            return;
        }
        if (prvniRadek == -1) {
            prvniRadek = radek;
            prvniSloupec = sloupec;
            dlazdice[radek][sloupec].setBorder(BorderFactory.createLineBorder(pruhledna, 60));
        } else {
            hra.setBonus(hra.getBonus() - 1);
            dlazdice[prvniRadek][prvniSloupec].setBorder(null);
            hra.getHraciPole().prohoditDlazdice(prvniRadek, prvniSloupec, radek, sloupec);
            obnoveniHracihoPole(hra.getHraciPole(), hra.getSkore());
            prvniRadek = -1;
            prvniSloupec = -1;
        }
    }

    public void obnoveniHracihoPole(HraciPole pole, int skore){
        int [][] hraciPole = pole.getHraciPole();
        for(int i = 0; i < hra.getHraciPole().getVelikost(); i++){
            for (int j = 0; j < hra.getHraciPole().getVelikost(); j++) {
                dlazdice[i][j].nastavitPodleCisla(hraciPole[i][j]);
            }
        }
        zobrazeniScore.setText("   Skóre: " + skore + "  Porhození: "+hra.getBonus()+ " x");

    }

    public void vysledekHry(String string){
        JOptionPane.showMessageDialog(this, string, "KONEC HRY!", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new HlavniMenu().setVisible(true);

    }
}

