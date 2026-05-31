
/**
 * Třída reprezentuje hlavní model (logické jádro) hry 2048.
 * <p>
 * Spravuje stav herního pole, aktuální skóre, herní mechaniku posunu dlaždic
 * do čtyř směrů (W, A, S, D), výpočet bonusových bodů pro získání prohození
 * a vyhodnocování stavu výhry (dosažení 2048) či prohry (Game Over).
 * </p>
 *
 * @author Katka
 */
public class Hra {

    /** Konstanty pro ovládání hry */
    public static final char vLevo = 'A';
    public static final char vPravo = 'D';
    public static final char nahoru = 'W';
    public static final char dolu = 'S';

    /** Instance hracího pole obsahující dlaždice s čísly. */
    private HraciPole hraciPole;
    /** Celkové skóre, které hráč v aktuální hře nasbíral. */
    private int skore;
    /** Dočasné počítadlo skóre sloužící pro výpočet bonusových prohození. */
    private int skoreBonus;
    /** Počet aktuálně dostupných bonusových prohození dlaždic myší. */
    private int bonus;
    /** Příznak zabraňující opakovanému oznamování výhry při každém dalším tahu po dosažení 2048. */
    private boolean vyhraOznamana;

    /** Cílová hodnota dlaždice potřebná pro vítězství. */
    private int vyherniDlazdice = 2048;
    /** Hodnota reprezentující prázdné políčko. */
    private int prazdnaDlazdice = 0;

    /**
     * Konstruktor třídy Hra.
     *
     * @param velikost Rozměr herní mřížky (např. 4 pro pole 4x4).
     */
    public Hra (int velikost){
        hraciPole = new HraciPole(velikost);
        skore = 0;
    }

    /**
     * @return Aktuální instanci hracího pole.
     */
    public HraciPole getHraciPole() {
        return hraciPole;
    }

    /**
     * @return Aktuální celkové skóre hry.
     */
    public int getSkore() {
        return skore;
    }

    /**
     * Zkontroluje, zda se na hracím poli nachází vítězná dlaždice (2048).
     *
     * @return {@code true}, pokud hráč vytvořil dlaždici 2048; jinak {@code false}.
     */
    public boolean vyhra(){
        return hraciPole.hledatVPoli(vyherniDlazdice);
    }

    /**
     * Vyhodnotí, zda se jedná o *novou* výhru, která ještě nebyla hráči oznámena.
     * Zajišťuje, aby se dialogové okno s gratulací zobrazilo pouze jednou.
     *
     * @return {@code true}, pokud hráč právě dosáhl 2048 a ještě o tom nebyl informován.
     */
    public boolean jeNovaVyhra() {
        if (vyhra() && !vyhraOznamana) {
            vyhraOznamana = true;
            return true;
        }
        return false;
    }

    /**
     * Kontroluje, zda hra skončila (Game Over).
     * Hra končí v případě, že je pole zcela plné a zároveň již nelze provést
     * žádný platný tah (sloučení dlaždic).
     *
     * @return {@code true}, pokud nastal konec hry; {@code false}, pokud se stále hraje.
     */
    public boolean konecHry(){

        if (hraciPole.hledatVPoli(prazdnaDlazdice)) {
            return false;
        }

        /** Pokud je plno, rozhoduje, zda lze ještě sloučit sousední dlaždice */
        return !jeMoznyTah();
    }

    /**
     * Pomocná metoda, která zjišťuje, zda v zaplněném poli existují
     * dva horizontálně nebo vertikálně sousedící prvky se stejnou hodnotou.
     *
     * @return {@code true}, pokud je možné provést sloučení; {@code false}, pokud jsou všechny tahy zablokované.
     */
    public boolean jeMoznyTah(){

        int Pole [][] = this.hraciPole.getHraciPole();

        /** 1. Kontrola vnitřní matice (všech prvků kromě posledního řádku a sloupce) */
        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            for (int j = 0; j < (hraciPole.getVelikost()-1); j++) {
                if( Pole[i][j] == Pole[i][j+1] || Pole[i][j] == Pole[i+1][j] ) {
                    return true;
                }
            }
        }

        /** 2. Kontrola sousedů v posledním řádku mřížky */
        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            if (Pole[hraciPole.getVelikost()-1][i] == Pole[hraciPole.getVelikost()-1][i+1]) {
                return true;
            }
        }

        /** 3. Kontrola sousedů v posledním sloupci mřížky */
        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            if (Pole[i][(hraciPole.getVelikost()-1)] == Pole[i+1][hraciPole.getVelikost()-1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hlavní výpočetní algoritmus pro tah směrem vlevo nad jedním řádkem.
     * <p>
     * 1. Komprese: Přesune všechna nenulová čísla na začátek pole (doleva).<br>
     * 2. Sloučení: Pokud mají sousední dlaždice stejnou hodnotu, první se zdvojnásobí,
     * přičte se skóre a zbývající čísla napravo se posunou o jedno políčko vlevo.
     * </p>
     *
     * @param radek Jednorozměrné pole reprezentující jeden řádek hrací plochy.
     * @return Nové zkomprimované a sloučené pole (řádek) po tahu vlevo.
     */
    public int[] procesTahVLevo(int radek[]){
        int novyRadek[] = new int[hraciPole.getVelikost()];

        /** Krok 1: Posun všech čísel doleva (odstranění nulových mezer) */
        int j=0;
        for(int i=0;i<hraciPole.getVelikost();i++){
            if(radek[i]!=0){
                novyRadek[j++]=radek[i];
            }
        }

        /** Krok 2: Slučování stejných sousedních dlaždic */
        for( int i=0; i<(hraciPole.getVelikost()-1); i++ ) {
            if( novyRadek[i]!=0 && novyRadek[i]==novyRadek[i+1]) {
                /** Zdvojnásobení hodnoty */
                novyRadek[i] = 2*novyRadek[i];
                /** Přičtení k celkovému skóre */
                skore+=novyRadek[i];
                /** Přičtení k bonusovému kontu */
                skoreBonus+=novyRadek[i];
                /** Kontrola zisku bonusu */
                pocitaniBonusSkore();

                /** Posun zbývajících prvků po sloučení, aby nevznikla prázdná díra */
                for( j=i+1; j<(hraciPole.getVelikost()-1); j++ ) {
                    novyRadek[j] = novyRadek[j+1];
                }
                /** Vynulování posledního prvku */
                novyRadek[(hraciPole.getVelikost()-1)] = 0;
            }
        }
        return novyRadek;
    }

    /**
     * Pomocná metoda, která obrátí pořadí prvků v poli (zrcadlově převrátí pole).
     * Využívá se pro implementaci tahu vpravo za pomoci tahu vlevo.
     *
     * @param pole Vstupní jednorozměrné pole čísel.
     * @return Nové pole s obráceným pořadím prvků.
     */
    public int[] prohozeniNaopak(int pole[]) {
        int[] naopak = new int[pole.length];
        for( int i=pole.length-1; i>=0; i-- ) {
            naopak[i] = pole[pole.length - i - 1];
        }
        return naopak;
    }

    /**
     * Zpracuje tah směrem vpravo nad jedním řádkem.
     * Pole nejprve zrcadlově obrátí, provede standardní
     * tah vlevo a výsledek opět zrcadlově otočí zpět.
     *
     * @param radek Jednorozměrné pole reprezentující jeden řádek hrací plochy.
     * @return Nové pole po zpracování tahu vpravo.
     */
    public int[] procesTahVPravo(int radek[]) {
        int novyRadek[] = new int[hraciPole.getVelikost()];
        int j = 0;
        for( int i=0; i<hraciPole.getVelikost(); i++ ) {
            if( radek[i]!=0 ) {
                novyRadek[j++] = radek[i];
            }
        }

        /** Otočení */
        novyRadek = prohozeniNaopak(novyRadek);
        /** Provedení tahu vlevo */
        novyRadek = procesTahVLevo(novyRadek);

        /** Otočení zpět */
        return prohozeniNaopak(novyRadek);
    }

    /**
     * Porovná původní stav matice s novým stavem po provedení tahu.
     * Používá se k ověření, zda byl tah platný (zda se na ploše vůbec něco pohnulo).
     *
     * @param staryPole Původní stav matice před tahem.
     * @param novePole Nový stav matice po simulaci tahu.
     * @return {@code true}, pokud se matice liší (tah změnil herní pole); jinak {@code false}.
     */
    private boolean kontrolaProvedeniTahu(int [][]staryPole, int [][]novePole) {
        for( int i=0; i<hraciPole.getVelikost(); i++ ) {
            for( int j=0; j<hraciPole.getVelikost(); j++ ) {
                if( staryPole[i][j] != novePole[i][j] ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Hlavní řídicí metoda pro zpracování jakéhokoli tahu (W, A, S, D).
     * <p>
     * Podle zadaného znaku směru transformuje příslušné řádky nebo sloupce matice.
     * U tahů nahoru/dolů extrahuje sloupce do dočasných polí, provede posun
     * a vrátí je zpět do matice. Na konci vyhodnotí, zda byl tah platný, a aktualizuje
     * stav hracího pole.
     * </p>
     *
     * @param tah Znak reprezentující směr (vLevo, vPravo, nahoru, dolu).
     * @return {@code true}, pokud byl tah úspěšně proveden (pole se změnilo); {@code false} při neplatném tahu.
     */
    public boolean procesTahu(char tah) {
        int [][] pole = this.hraciPole.getHraciPole();
        switch(tah) {
            case vLevo:
            {
                for( int i=0; i<hraciPole.getVelikost(); i++ ){
                    int novyRadek[] = procesTahVLevo(pole[i]);
                    for( int j=0; j<hraciPole.getVelikost(); j++ ) {
                        pole[i][j] = novyRadek[j];
                    }
                }
            }
            break;
            case vPravo:
            {
                for( int i=0; i<hraciPole.getVelikost(); i++ ){
                    int novyRadek[] = procesTahVPravo(pole[i]);
                    for( int j=0; j<hraciPole.getVelikost(); j++ ) {
                        pole[i][j] = novyRadek[j];
                    }
                }
            }
            break;
            case nahoru:
            {
                for( int j=0; j<hraciPole.getVelikost(); j++ ) {
                    int radek[] = new int[hraciPole.getVelikost()];
                    for( int i=0; i<hraciPole.getVelikost(); i++ ) {
                        radek[i] = pole[i][j];
                    }

                    int novyRadek[] = procesTahVLevo(radek);

                    for( int i=0; i<hraciPole.getVelikost(); i++ ) {
                        pole[i][j] = novyRadek[i];
                    }
                }
            }
            break;
            case dolu:
            {
                for( int j=0; j<hraciPole.getVelikost(); j++ ) {
                    int radek[] = new int[hraciPole.getVelikost()];
                    for( int i=0; i<hraciPole.getVelikost(); i++ ) {
                        radek[i] = pole[i][j];
                    }

                    int novyRadek[] = procesTahVPravo(radek);

                    for( int i=0; i<hraciPole.getVelikost(); i++ ) {
                        pole[i][j] = novyRadek[i];
                    }
                }
            }
            break;
        }

        /** Kontrola, zda tah vyvolal nějakou změnu v herním poli */
        boolean tahProveden = kontrolaProvedeniTahu(this.hraciPole.getHraciPole(), pole);
        /** Uložení nového stavu pole */
        this.hraciPole.setHraciPole(pole);
        return tahProveden;
    }

    /**
     * Logika správy bonusů. Každých nasbíraných 1000 bodů (uložených v skoreBonus)
     * odmění hráče jedním bonusovým prohozením a odečte 1000 bodů z bonusového konta.
     */
    public void pocitaniBonusSkore(){

        if (skoreBonus>=1000){
            bonus++;
            skoreBonus=skoreBonus-1000;
        }
    }

    /**
     * @return Aktuální počet dostupných bonusových prohození.
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Nastaví počet dostupných bonusových prohození (např. při odečtení po použití).
     *
     * @param bonus Nový počet bonusů.
     */
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

