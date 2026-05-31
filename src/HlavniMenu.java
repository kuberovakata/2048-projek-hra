import javax.swing.*;
import java.awt.*;

/**
 * Třída reprezentuje úvodní grafické menu hry 2048.
 * Okno obsahuje přizpůsobené grafické pozadí, komponentu pro výběr
 * velikosti herního pole (4x4, 6x6, 8x8) a spouštěcí tlačítko, které
 * spouští samotnou hru a propojí herní logiku s grafickým oknem a ovládáním.
 *
 * @author Katka
 */
public class HlavniMenu extends JFrame {

    /** Obrázek použitý jako pozadí hlavního menu. */
    private Image obrazekPozadi;

    /**
     * Konstruktor třídy HlavniMenu.
     * <p>
     * Vytváří okno menu, načítá externí obrázek na pozadí, nastavuje
     * fixní rozměry okna a rozvržení jednotlivých prvků rozhraní.
     * Dále přidává rozbalovací seznam pro výběr obtížnosti a definuje chování
     * spouštěcího tlačítka.
     * </p>
     */
    public HlavniMenu() {

        /** Načtení obrázku pozadí ze složky projektu */
        ImageIcon icon = new ImageIcon(getClass().getResource("menu.png"));
        obrazekPozadi = icon.getImage();

        /** Základní nastavení parametrů okna (JFrame) */
        setTitle("2048 - Hlavní Menu");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        /**
         * Vytvoření JPanel, který zajišťuje
         * vykreslování obrázku na pozadí přes celé okno menu.
         */
        JPanel panelSPozadim = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (obrazekPozadi != null) {
                    g.drawImage(obrazekPozadi, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        /** Řazení komponentů pod sebe */
        panelSPozadim.setLayout(new BoxLayout(panelSPozadim, BoxLayout.Y_AXIS));
        /** Vytvoření neviditelného prostoru pro text v obrázku) */
        panelSPozadim.add(Box.createVerticalStrut(320));

        /** Seskupení prvků v rozhraní */
        JPanel obsahBilehoBoxu = new JPanel();
        obsahBilehoBoxu.setLayout(new BoxLayout(obsahBilehoBoxu, BoxLayout.Y_AXIS));
        /** Průhledný panel, aby neblokoval obrázek na pozadí */
        obsahBilehoBoxu.setOpaque(false);

        /** Panel pro výběr velikosti hracího pole */
        JPanel panelVyberu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelVyberu.setOpaque(false);
        panelVyberu.setMaximumSize(new Dimension(800, 40));

        JLabel popisek = new JLabel("Vyber velikost pole:");
        popisek.setFont(new Font("Arial", Font.BOLD, 16));
        popisek.setForeground(new Color(50, 0, 50));

        panelVyberu.add(popisek);

        /** Nabídka povolených rozměrů pole */
        Integer[] velikosti = {4, 6, 8};
        JComboBox<Integer> vyberVelikosti = new JComboBox<>(velikosti);

        panelVyberu.add(vyberVelikosti);

        obsahBilehoBoxu.add(panelVyberu);
        /** Mezera mezi výběrem a tlačítkem */
        obsahBilehoBoxu.add(Box.createVerticalStrut(40));

        /** Hlavní spouštěcí tlačítko */
        JButton startTlacitko = new JButton("Spustit hru");
        startTlacitko.setFont(new Font("Arial", Font.BOLD, 18));
        /** Změna kurzoru na "ručičku" při najetí */
        startTlacitko.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelTlacitka = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTlacitka.setOpaque(false);
        panelTlacitka.setMaximumSize(new Dimension(800, 50));
        panelTlacitka.add(startTlacitko);

        obsahBilehoBoxu.add(panelTlacitka);
        panelSPozadim.add(obsahBilehoBoxu);
        panelSPozadim.add(Box.createVerticalGlue());

        /**
         * Akce (ActionListener) pro spouštěcí tlačítko.
         * Zjistí vybraný rozměr, zavře menu a zapne hlavní hru s zvolenou velikostí.
         */
        startTlacitko.addActionListener(e -> {
            /** Získání zvolené velikosti pole z JComboBoxu */
            int zvolenaVelikost = (int) vyberVelikosti.getSelectedItem();

            this.dispose();

            /** Vytvoření samotného jádra a běhu hry */
            Hra hra = new Hra(zvolenaVelikost);
            Okno okno = new Okno(hra);
            Ovladani ovladani = new Ovladani();

            /** Propojení jednotlivých komponentů */
            ovladani.vlozitHru(hra);
            ovladani.vlozitOkno(okno);
            okno.vlozitOvladani(ovladani);
        });

        /** Nastavení panelu jako hlavního okna */
        setContentPane(panelSPozadim);
        setVisible(true);
    }
}

