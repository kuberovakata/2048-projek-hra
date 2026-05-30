public class Hra {

    public static final char vLevo = 'A';
    public static final char vPravo = 'D';
    public static final char nahoru = 'W';
    public static final char dolu = 'S';

    private HraciPole hraciPole;
    private int skore;
    private boolean vyhraOznamana;

    private int vyherniDlazdice = 2048;
    private int prazdnaDlazdice = 0;

    public Hra (int velikost){
        hraciPole = new HraciPole(velikost);
        skore = 0;
    }

    public HraciPole getHraciPole() {
        return hraciPole;
    }

    public int getSkore() {
        return skore;
    }
}

