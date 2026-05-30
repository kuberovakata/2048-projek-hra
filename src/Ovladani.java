public class Ovladani {

    private Hra hra2048;
    private Okno okno;

    public Ovladani() {
    }

    public void vlozitHru(Hra hra2048) {
        this.hra2048 = hra2048;
    }

    public void vlozitOkno(Okno Okno) {
        this.okno = Okno;
        okno.obnoveniHracihoPole(hra2048.getHraciPole(), hra2048.getSkore());
    }

    public void tahOdUzivatele(char tah){
        boolean tahProveden = hra2048.procesTahu(tah);
        System.out.println("tah:" + tah);

        if (tahProveden) {
            hra2048.getHraciPole().pridatNahodneCislo();
            okno.obnoveniHracihoPole(hra2048.getHraciPole(), hra2048.getSkore());
        }

        if (hra2048.konecHry()) {
            okno.vysledekHry("PROHRÁL JSI.");
        }

        if (hra2048.jeNovaVyhra()) {
            int volba = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    "Gratuluji, dosáhl jsi 2048! Chceš pokračovat ve hře dál?",
                    "VYHRÁL JSI!",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            if (volba == javax.swing.JOptionPane.NO_OPTION) {
                okno.dispose();
                new HlavniMenu().setVisible(true);
            }
        }
    }
}
