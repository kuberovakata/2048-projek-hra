import javax.swing.*;
import java.awt.*;

public class HlavniMenu extends JFrame {

    public HlavniMenu() {
        setTitle("2048 - Hlavní Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel nadpis = new JLabel("Vítej ve hře 2048!", SwingConstants.CENTER);
        nadpis.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        add(nadpis);

        JPanel panelVyberu = new JPanel();
        panelVyberu.add(new JLabel("Vyber velikost pole:"));

        Integer[] velikosti = {4, 5, 8};
        JComboBox<Integer> vyberVelikosti = new JComboBox<>(velikosti);
        panelVyberu.add(vyberVelikosti);
        add(panelVyberu);

        JButton startTlacitko = new JButton("Spustit hru");
        startTlacitko.addActionListener(e -> {
            int zvolenaVelikost = (int) vyberVelikosti.getSelectedItem();

            this.dispose();

            //TODO: zapnuti hry
        });

        JPanel panelTlacitka = new JPanel();
        panelTlacitka.add(startTlacitko);
        add(panelTlacitka);
    }
}

