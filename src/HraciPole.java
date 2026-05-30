import java.util.Random;

public class HraciPole {

    private  int hraciPole [][];
    private int velikost;
    private Random random = new Random();

    public HraciPole(int velikost) {
        this.velikost = velikost;
        hraciPole = new int[velikost][velikost];
        pridatNahodneCislo();
        pridatNahodneCislo();
    }

    public void setHraciPole(int hraciPole [][]) {
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                this.hraciPole[i][j] = hraciPole [i][j];
            }
        }
    }

    public int[][] getHraciPole() {
        int [][] kopieHraciPole = new int[velikost][velikost];
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                kopieHraciPole[i][j] = this.hraciPole [i][j];
            }
        }
        return kopieHraciPole;
    }

    public int getVelikost() {
        return velikost;
    }

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

    public void pridatNahodneCislo(){
        if (random.nextInt()%2==0) {
            pridatCislo(2);
        }else{
            pridatCislo(4);
        }
    }

    private void pridatCislo(int pridane){

        int i = random.nextInt(velikost);
        int j = random.nextInt(velikost);

        while(hraciPole[i][j] != 0){
            i = random.nextInt(velikost);
            j = random.nextInt(velikost);
        }

        hraciPole[i][j] = pridane;
    }
}