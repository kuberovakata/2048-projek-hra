public class Ovladani {

    private Hra hra2048;

    public Ovladani() {
    }

    public void vlozitHru(Hra hra2048) {
        this.hra2048 = hra2048;
    }

    public void tahOdUzivatele(char tah){
        boolean tahProveden = hra2048.procesTahu(tah);
        System.out.println("tah:" + tah);
    }
}
