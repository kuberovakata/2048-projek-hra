import javax.swing.*;
import java.awt.*;

/**
 * Třída reprezentuje vizuál jedné dlaždice na herním poli.
 * Rozšiřuje {@link JLabel}.
 * Stará se o načítání a zobrazování grafických obrázků
 * (ikon) pro každou hodnotu hry 2048 (od prázdného políčka 0 až po hodnotu 32768).
 *
 * @author Katka
 */
public class Dlazdice  extends JLabel {

    /**
     * Konstruktor třídy Dlazdice.
     * <p>
     * Nastaví výchozí vlastnosti grafického prvku: vycentrování obsahu,
     * neprůhlednost (aby bylo vidět pozadí), rozměry přebírá z
     * třídy {@link Okno} a tenký černý okraj oddělující sousední dlaždice.
     * </p>
     */
    public Dlazdice() {

        super("", SwingConstants.CENTER);
        setOpaque(true);
        setPreferredSize(new Dimension(Okno.sirkaDlazdice, Okno.vyskaDlazdice));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
    }

    /**
     * Aktualizuje vzhled dlaždice (obrázek/ikonu) na základě zadané číselné hodnoty.
     * <p>
     * Metoda nejprve vyčistí textový obsah. Následně pomocí konstrukce switch
     * vyhledá odpovídající soubor obrázku (.png) ve složce "obrazky" a nastaví jej
     * jako novou ikonu této dlaždice.
     * </p>
     *
     * @param cislo Aktuální číselná hodnota z herní logiky (např. 0, 2, 4, ..., 2048, ...).
     */
    public void nastavitPodleCisla(int cislo){

        switch(cislo){
            case 0:
                setIcon(new ImageIcon("obrazky/0.png"));
                break;
            case 2:
                setIcon(new ImageIcon("obrazky/2.png"));
                break;
            case 4:
                setIcon(new ImageIcon("obrazky/4.png"));
                break;
            case 8:
                setIcon(new ImageIcon("obrazky/8.png"));
                break;
            case 16:
                setIcon(new ImageIcon("obrazky/16.png"));
                break;
            case 32:
                setIcon(new ImageIcon("obrazky/32.png"));
                break;
            case 64:
                setIcon(new ImageIcon("obrazky/64.png"));
                break;
            case 128:
                setIcon(new ImageIcon("obrazky/128.png"));
                break;
            case 256:
                setIcon(new ImageIcon("obrazky/256.png"));
                break;
            case 512:
                setIcon(new ImageIcon("obrazky/512.png"));
                break;
            case 1024:
                setIcon(new ImageIcon("obrazky/1024.png"));
                break;
            case 2048:
                setIcon(new ImageIcon("obrazky/2048.png"));
                break;
            case 4096:
                setIcon(new ImageIcon("obrazky/4096.png"));
                break;
            case 8192:
                setIcon(new ImageIcon("obrazky/8192.png"));
                break;
            case 16384:
                setIcon(new ImageIcon("obrazky/16384.png"));
                break;
            case 32768:
                setIcon(new ImageIcon("obrazky/32768.png"));
                break;
        }
    }
}

