import javax.swing.*;
import java.awt.*;

public class HlavniMenu extends JFrame {

    private Image obrazekPozadi;

    public HlavniMenu() {

        ImageIcon icon = new ImageIcon("obrazky/menu.png");
        obrazekPozadi = icon.getImage();

        setTitle("2048 - Hlavní Menu");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelSPozadim = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (obrazekPozadi != null) {
                    g.drawImage(obrazekPozadi, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panelSPozadim.setLayout(new BoxLayout(panelSPozadim, BoxLayout.Y_AXIS));
        panelSPozadim.add(Box.createVerticalStrut(320));

        JPanel obsahBilehoBoxu = new JPanel();
        obsahBilehoBoxu.setLayout(new BoxLayout(obsahBilehoBoxu, BoxLayout.Y_AXIS));
        obsahBilehoBoxu.setOpaque(false);

        JPanel panelVyberu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelVyberu.setOpaque(false);
        panelVyberu.setMaximumSize(new Dimension(800, 40));

        JLabel popisek = new JLabel("Vyber velikost pole:");
        popisek.setFont(new Font("Arial", Font.BOLD, 16));
        popisek.setForeground(new Color(50, 0, 50));

        panelVyberu.add(popisek);

        Integer[] velikosti = {4, 6, 8};
        JComboBox<Integer> vyberVelikosti = new JComboBox<>(velikosti);

        panelVyberu.add(vyberVelikosti);

        obsahBilehoBoxu.add(panelVyberu);
        obsahBilehoBoxu.add(Box.createVerticalStrut(20));

        JButton startTlacitko = new JButton("Spustit hru");
        startTlacitko.setFont(new Font("Arial", Font.BOLD, 18));
        startTlacitko.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelTlacitka = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTlacitka.setOpaque(false);
        panelTlacitka.setMaximumSize(new Dimension(800, 50));
        panelTlacitka.add(startTlacitko);

        obsahBilehoBoxu.add(panelTlacitka);
        panelSPozadim.add(obsahBilehoBoxu);
        panelSPozadim.add(Box.createVerticalGlue());

        startTlacitko.addActionListener(e -> {
            int zvolenaVelikost = (int) vyberVelikosti.getSelectedItem();

            this.dispose();

            Hra hra = new Hra(zvolenaVelikost);
            Okno okno = new Okno(hra);
            Ovladani ovladani = new Ovladani();
            ovladani.vlozitHru(hra);
            ovladani.vlozitOkno(okno);
            okno.vlozitOvladani(ovladani);
        });

        setContentPane(panelSPozadim);
        setVisible(true);
    }
}

