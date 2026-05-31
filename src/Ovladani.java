import javax.swing.*;

/**
 * Třída funguje jako Controller (řídicí jednotka).
 * <p>
 * Propojuje herní logiku (třídu {@link Hra}) s uživatelským rozhraním (třídou {@link Okno}).
 * Zpracovává tahy uživatele, vyhodnocuje stavy hry (výhra, prohra), spouští generování
 * nových čísel a dává pokyny oknu k překreslení plochy.
 * </p>
 *
 * @author Katka
 */
public class Ovladani {

    /** Instance herní logiky (Model). */
    private Hra hra2048;
    /** Instance hlavního grafického okna hry (View). */
    private Okno okno;

    /**
     * Výchozí konstruktor třídy Ovladani.
     */
    public Ovladani() {
    }

    /**
     * Propojí ovladač s herní logikou.
     *
     * @param hra2048 Instance třídy Hra, která spravuje pravidla a data.
     */
    public void vlozitHru(Hra hra2048) {
        this.hra2048 = hra2048;
    }

    /**
     * Propojí ovladač s grafickým oknem a provede prvotní vykreslení herní plochy.
     * Metoda automaticky načte aktuální stav pole a skóre z modelu a předá je oknu.
     *
     * @param Okno Instance třídy Okno představující herní okno.
     */
    public void vlozitOkno(Okno Okno) {
        this.okno = Okno;
        /** Prvotní synchronizace grafiky s daty  */
        okno.obnoveniHracihoPole(hra2048.getHraciPole(), hra2048.getSkore());
    }

    /**
     * Hlavní metoda pro zpracování tahu hráče (posunu dlaždic).
     * <p>
     * Metoda předá požadavek na tah herní logice. Pokud byl tah platný (dlaždice se posunuly):
     * 1. Vygeneruje se nová náhodná dlaždice (2 nebo 4).
     * 2. Aktualizuje se grafické zobrazení herního pole a skóre.
     * Následně metoda zkontroluje stav hry. Pokud nastal konec hry, zobrazí hlášení o prohře.
     * Pokud hráč právě dosáhl hodnoty 2048, zobrazí se dialogové okno s dotazem, zda si přeje
     * pokračovat, nebo se vrátit do hlavního menu.
     * </p>
     *
     * @param tah Znak reprezentující směr tahu ('W', 'A', 'S', 'D' nebo šipky).
     */
    public void tahOdUzivatele(char tah){
        /** Pokus o provedení tahu v logice hry */
        boolean tahProveden = hra2048.procesTahu(tah);
        System.out.println("tah:" + tah);

        /** Pokud se pole po tahu jakkoli změnilo */
        if (tahProveden) {
            /** Přidání nového čísla */
            hra2048.getHraciPole().pridatNahodneCislo();
            /** Překreslení okna */
            okno.obnoveniHracihoPole(hra2048.getHraciPole(), hra2048.getSkore());
        }

        /** Kontrola, zda už hráč nemá žádné platné tahy (Prohra) */
        if (hra2048.konecHry()) {
            okno.vysledekHry("PROHRÁL JSI.");
        }

        /** Kontrola, zda hráč poprvé vytvořil dlaždici 2048 (Výhra) */
        if (hra2048.jeNovaVyhra()) {

            /** Zobrazení vyskakovacího dialogu s výběrem Ano/Ne */
            int volba = JOptionPane.showConfirmDialog(
                    null,
                    "Gratuluji, dosáhl jsi 2048! Chceš pokračovat ve hře dál?",
                    "VYHRÁL JSI!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            /** Pokud hráč zvolí "Ne", zavře se herní okno a otevře se hlavní menu */
            if (volba == JOptionPane.NO_OPTION) {
                okno.dispose();
                new HlavniMenu().setVisible(true);
            }
        }
    }
}
