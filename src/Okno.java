import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Třída reprezentuje hlavní grafické okno běžící hry 2048.
 * <p>
 * Stará se o kompletní vykreslení herní mřížky (pomocí komponenty {@link Dlazdice}),
 * zobrazení aktuálního skóre a počtu dostupných bonusů. Okno zachytává stisky kláves
 * pro pohyb dlaždic a kliknutí myší pro aktivaci bonusového prohazování.
 * </p>
 *
 * @author Katka
 */
public class Okno extends JFrame {

    /** Vytvoření herní logiky, která uchovává aktuální stav a data hry. */
    Hra hra;

    /** Index řádku a sloupce první vybrané dlaždice pro bonusové prohození. Hodnota -1 znamená, že žádná není vybraná. */
    private int prvniRadek = -1;
    private int prvniSloupec = -1;

    Color pruhledna = new java.awt.Color(0, 0, 0, 115);

    /** Statické konstanty definující šířku a výšku jedné dlaždice v pixelech. */
    public static final int sirkaDlazdice = 120;
    public static final int vyskaDlazdice = 120;

    /** Popisek (JLabel) zobrazující aktuální skóre a počet zbývajících prohození v horní části okna. */
    private JLabel zobrazeniScore;
    /** Dvourozměrné pole reprezentující vizuální mřížku na obrazovce. */
    private Dlazdice dlazdice [][];
    /** Instance controlleru, kterému okno předává akce od uživatele (klávesnice/myš). */
    private Ovladani ovladani;

    /**
     * Vloží (naimportuje) instanci controlleru pro předávání uživatelských vstupů.
     *
     * @param ovladani Instance třídy Ovladani.
     */
    public void vlozitOvladani(Ovladani ovladani){
        this.ovladani = ovladani;
    }

    /**
     * Konstruktor třídy Okno.
     * <p>
     * Nastaví referenci na hru, vygeneruje uživatelské rozhraní pomocí metody {@link #setup()},
     * ošetří bezpečné zavírání okna s potvrzovacím dialogem a vycentruje
     * okno na střed obrazovky.
     * </p>
     *
     * @param hra Aktuální instance běžící hry.
     */
    public Okno(Hra hra) {

        this.hra = hra;
        setup();
        setTitle("2 0 4 8");

        /** Vypnutí automatického zavření, abychom mohli zobrazit vlastní dialog */
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                /** Stylování potvrzovacího dialogu do černé barvy */
                UIManager.put("OptionPane.background", Color.BLACK);
                UIManager.put("Panel.background", Color.BLACK);
                UIManager.put("OptionPane.messageForeground", Color.WHITE);

                /** Pokud hráč potvrdí odchod, okno se zavře a otevře se HlavniMenu */
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

        /** Automatické přizpůsobení velikosti okna podle komponent uvnitř */
        pack();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Inicializuje a rozmístí všechny grafické komponenty v okně.
     * <p>
     * Vytvoří horní panel se skóre a centrální panel s mřížkou (GridLayout) podle zvolené
     * velikosti pole. Každé dlaždici přiřadí MouseListener pro detekci kliknutí (bonus)
     * a celému oknu přidá KeyListener, který reaguje na šipky klávesnice pro pohyb herních prvků.
     * </p>
     */
    private void setup(){

        this.setLayout(new BorderLayout());
        int velikost = hra.getHraciPole().getVelikost();

        /** Inicializace a formátování textu skóre */
        zobrazeniScore = new JLabel("   Skóre: " + hra.getSkore(), SwingConstants.LEFT);
        JPanel herniPanel = new JPanel();

        zobrazeniScore.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        zobrazeniScore.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        zobrazeniScore.setOpaque(true);
        zobrazeniScore.setBackground(new Color(40, 32, 44, 255));
        zobrazeniScore.setForeground(new Color(224, 233, 243));
        this.getContentPane().setBackground(Color.BLACK);
        this.add(zobrazeniScore,BorderLayout.NORTH);

        /** Nastavení mřížky pro herní dlaždice s mezerami 5 pixelů */
        herniPanel.setLayout(new GridLayout(velikost, velikost, 5, 5));
        herniPanel.setBackground(new Color(0, 0, 0));
        herniPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        /** Generování jednotlivých dlaždic a přidání posluchače myši */
        dlazdice = new Dlazdice[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                dlazdice[i][j] = new Dlazdice();
                herniPanel.add(dlazdice[i][j]);

                /** Pomocné proměnné, protože anonymní třída vyžaduje efektivně finální proměnné */
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

        /** Posluchač stisknutých kláves (šipek) pro ovládání směru hry */
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

    /**
     * Zajišťuje logiku bonusového prohazování dvou dlaždic na základě kliknutí myší.
     * <p>
     * Pokud hráč nemá žádný bonus, metoda ihned skončí. Při prvním kliknutí se uloží souřadnice
     * vybrané dlaždice a vizuálně se zvýrazní jejím ohraničením. Při druhém kliknutí na jinou dlaždici
     * se odečte jeden bonus, ohraničení se zruší, v logice se čísla prohodí a celé herní pole
     * se překreslí.
     * </p>
     *
     * @param radek Index řádku dlaždice, na kterou uživatel kliknul.
     * @param sloupec Index sloupce dlaždice, na kterou uživatel kliknul.
     */
    private void zpracovaniKliknuti(int radek, int sloupec) {

        /** Kontrola, zda má hráč vůbec k dispozici nějaké bonusy */
        if (hra.getBonus()<1){
            return;
        }

        /** Výběr první dlaždice pro výměnu */
        if (prvniRadek == -1) {
            prvniRadek = radek;
            prvniSloupec = sloupec;
            /** Vizuální označení vybrané dlaždice širokým poloprůhledným okrajem */
            dlazdice[radek][sloupec].setBorder(BorderFactory.createLineBorder(pruhledna, 60));
        } else {
            /** Výběr druhé dlaždice -> provedení akce */
            /** Odečtení bonusu */
            hra.setBonus(hra.getBonus() - 1);
            /** Reset okraje u první dlaždice */
            dlazdice[prvniRadek][prvniSloupec].setBorder(null);

            /** Logické prohození hodnot v poli a překreslení grafiky */
            hra.getHraciPole().prohoditDlazdice(prvniRadek, prvniSloupec, radek, sloupec);
            obnoveniHracihoPole(hra.getHraciPole(), hra.getSkore());

            /** Resetování výběrových indexů pro další případné použití */
            prvniRadek = -1;
            prvniSloupec = -1;
        }
    }

    /**
     * Kompletně aktualizuje vzhled herního plánu podle aktuálního stavu dat.
     * Projde všechny dlaždice a změní jejich text a pozadí na základě čísel.
     * Zároveň aktualizuje text v horním řádku (skóre a počet zbývajících bonusů).
     *
     * @param pole Aktuální herní pole s daty.
     * @param skore Aktuální celkové skóre hry, které se má zobrazit.
     */
    public void obnoveniHracihoPole(HraciPole pole, int skore){
        int [][] hraciPole = pole.getHraciPole();
        for(int i = 0; i < hra.getHraciPole().getVelikost(); i++){
            for (int j = 0; j < hra.getHraciPole().getVelikost(); j++) {
                dlazdice[i][j].nastavitPodleCisla(hraciPole[i][j]);
            }
        }
        /** Přepis textu skóre a počítadla prohození */
        zobrazeniScore.setText("   Skóre: " + skore + "  Prohození: "+hra.getBonus()+ " x");

    }

    /**
     * Zobrazí vyskakovací okno s konečným výsledkem hry (např. informaci o prohře).
     * Po potvrzení dialogu uživatelem se aktuální herní okno uzavře a dojde k otevření
     * hlavního menu.
     *
     * @param string Text zprávy, která se má uživateli zobrazit (např. "PROHRÁL JSI.").
     */
    public void vysledekHry(String string){
        JOptionPane.showMessageDialog(this, string, "KONEC HRY!", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new HlavniMenu().setVisible(true);

    }
}

