public class Hra {

    public static final char vLevo = 'A';
    public static final char vPravo = 'D';
    public static final char nahoru = 'W';
    public static final char dolu = 'S';

    private HraciPole hraciPole;
    private int skore;
    private int skoreBonus;
    private int bonus;
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
    public boolean vyhra(){
        return hraciPole.hledatVPoli(vyherniDlazdice);
    }

    public boolean jeNovaVyhra() {
        if (vyhra() && !vyhraOznamana) {
            vyhraOznamana = true;
            return true;
        }
        return false;
    }

    public boolean konecHry(){

        if (hraciPole.hledatVPoli(prazdnaDlazdice)) {
            return false;
        }

        return !jeMoznyTah();
    }

    public boolean jeMoznyTah(){

        int Pole [][] = this.hraciPole.getHraciPole();

        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            for (int j = 0; j < (hraciPole.getVelikost()-1); j++) {
                if( Pole[i][j] == Pole[i][j+1] || Pole[i][j] == Pole[i+1][j] ) {
                    return true;
                }
            }
        }

        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            if (Pole[hraciPole.getVelikost()-1][i] == Pole[hraciPole.getVelikost()-1][i+1]) {
                return true;
            }
        }

        for (int i = 0; i < (hraciPole.getVelikost()-1); i++) {
            if (Pole[i][(hraciPole.getVelikost()-1)] == Pole[i+1][hraciPole.getVelikost()-1]) {
                return true;
            }
        }
        return false;
    }

    public int[] procesTahVLevo(int radek[]){
        int novyRadek[] = new int[hraciPole.getVelikost()];

        int j=0;
        for(int i=0;i<hraciPole.getVelikost();i++){
            if(radek[i]!=0){
                novyRadek[j++]=radek[i];
            }
        }

        for( int i=0; i<(hraciPole.getVelikost()-1); i++ ) {
            if( novyRadek[i]!=0 && novyRadek[i]==novyRadek[i+1]) {
                novyRadek[i] = 2*novyRadek[i];
                skore+=novyRadek[i];
                skoreBonus+=novyRadek[i];
                pocitaniBonusSkore();
                for( j=i+1; j<(hraciPole.getVelikost()-1); j++ ) {
                    novyRadek[j] = novyRadek[j+1];
                }
                novyRadek[(hraciPole.getVelikost()-1)] = 0;
            }
        }
        return novyRadek;
    }

    public int[] prohozeniNaopak(int pole[]) {
        int[] naopak = new int[pole.length];
        for( int i=pole.length-1; i>=0; i-- ) {
            naopak[i] = pole[pole.length - i - 1];
        }
        return naopak;
    }

    public int[] procesTahVPravo(int radek[]) {
        int novyRadek[] = new int[hraciPole.getVelikost()];
        int j = 0;
        for( int i=0; i<hraciPole.getVelikost(); i++ ) {
            if( radek[i]!=0 ) {
                novyRadek[j++] = radek[i];
            }
        }

        novyRadek = prohozeniNaopak(novyRadek);
        novyRadek = procesTahVLevo(novyRadek);

        return prohozeniNaopak(novyRadek);
    }

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

        boolean tahProveden = kontrolaProvedeniTahu(this.hraciPole.getHraciPole(), pole);
        this.hraciPole.setHraciPole(pole);
        return tahProveden;
    }

    public void pocitaniBonusSkore(){

        if (skoreBonus>=1000){
            bonus++;
            skoreBonus=skoreBonus-1000;
        }
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

