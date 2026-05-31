import java.util.Random;

/**
 * Třída reprezentuje vnitřní datovou strukturu herního pole 2048.
 * Stará se o udržování hodnot jednotlivých dlaždic v aktuálním stavu.
 * Poskytuje metody pro přístup k těmto datům a obsahuje speciální mechaniky.
 *
 * @author Katka
 */
public class HraciPole {

    private Random random = new Random();

    /** Dvourozměrné pole reprezentující herní mřížku s čísly. */
    private  int hraciPole [][];
    /** Velikost strany herního pole (počet řádků a sloupců). velikost x velikost */
    private int velikost;

    /**
     * Konstruktor pro vytvoření nového herního pole o zadané velikosti.
     * Inicializuje prázdnou mřížku (všechny hodnoty jsou nastaveny na 0).
     * Hned při vytvoření se přidájí dvě dlaždice na začátek hry.
     *
     * @param velikost Počet řádků a sloupců herního pole.
     */
    public HraciPole(int velikost) {
        this.velikost = velikost;
        hraciPole = new int[velikost][velikost];
        pridatNahodneCislo();
        pridatNahodneCislo();
    }

    /**
     * Kopírováním nastaví hodnoty herního pole (prvek po prvku).
     *
     * @param hraciPole pole int[][], ze kterého se hodnoty kopírují.
     */
    public void setHraciPole(int hraciPole [][]) {
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                this.hraciPole[i][j] = hraciPole [i][j];
            }
        }
    }

    /**
     * Vrací nezávislou kopii aktuálního herního pole.
     * <p>
     * vytváří nové dvourozměrné pole a překopíruje do něj všechny hodnoty prvek po prvku.
     * Tento přístup zajišťuje bezpečné zapouzdření dat,takže vnější třídy mohou s daty pracovat, ale nemohou
     * nechtěně přepsat skutečný stav hry.
     * </p>
     *
     * @return Nové pole obsahující aktuální hodnoty dlaždic.
     */
    public int[][] getHraciPole() {
        int [][] kopieHraciPole = new int[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                kopieHraciPole[i][j] = this.hraciPole [i][j];
            }
        }
        return kopieHraciPole;
    }

    /**
     * Vrací velikost herního pole.
     *
     * @return Počet řádků/sloupců pole.
     */
    public int getVelikost() {
        return velikost;
    }

    /**
     * Prohledá celé herní pole a zjistí, zda se v něm nachází konkrétní číselná hodnota.
     *
     * @param hledane Číselná hodnota, které v poli hledáme.
     * @return
     * {@code true}, pokud se hledané číslo v poli nachází alespoň jednou;
     * {@code false}, pokud hodnota v celém poli není.
     */
    public boolean hledatVPoli(int hledane){
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                if (hraciPole[i][j] == hledane) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Přída do pole 2, jestli je náhodné číslo sudé a 4 jestli není.
     * využívá metodu pridatCislo(int pridane)
     */
    public void pridatNahodneCislo(){
        if (random.nextInt()%2==0) {
            pridatCislo(2);
        }else{
            pridatCislo(4);
        }
    }

    /**
     * Umístí novou číselnou dlaždici na náhodně zvolené volné místo v herním poli.
     *
     * Metoda generuje náhodné souřadnice řádku a sloupce. Pokud zjistí, že vybrané
     * políčko je již obsazené (hodnota je různá od 0), generování souřadnic opakuje
     * v cyklu tak dlouho, dokud nenajde prázdné místo. Následně na toto volné místo
     * zapíše zadané číslo.
     *
     * @param pridane Číselná hodnota, která má být do pole přidána.
     */
    private void pridatCislo(int pridane){

        int i = random.nextInt(velikost);
        int j = random.nextInt(velikost);

        while(hraciPole[i][j] != 0){
            i = random.nextInt(velikost);
            j = random.nextInt(velikost);
        }
        hraciPole[i][j] = pridane;
    }

    /**
     * Prohodí pozice dvou dlaždic na základě jejich souřadnic.
     * Tato metoda provádí přímou modifikaci hlavního herního pole.
     *
     * @param r1 Index řádku první dlaždice.
     * @param s1 Index sloupce první dlaždice.
     * @param r2 Index řádku druhé dlaždice.
     * @param s2 Index sloupce druhé dlaždice.
     */
    public void prohoditDlazdice(int r1, int s1, int r2, int s2) {

        int docasna = this.hraciPole[r1][s1];
        this.hraciPole[r1][s1] = this.hraciPole[r2][s2];
        this.hraciPole[r2][s2] = docasna;
    }
}