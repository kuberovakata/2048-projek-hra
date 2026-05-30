import javax.swing.*;
import java.awt.*;

public class Dlazdice  extends JLabel {

    public Dlazdice() {
        super("", SwingConstants.CENTER);
        setOpaque(true);
        setPreferredSize(new Dimension(Okno.sirkaDlazdice, Okno.vyskaDlazdice));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        setBackground(COLOR_BLANK_TILE);
    }

    public void nastavitPodleCisla(int cislo){

        if(cislo == 0){
            setText("");
            setIcon(null);
            setBackground(Color.GRAY);
        }else{
            setText("");
        }
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

    private static final Color COLOR_BLANK_TILE = new Color(197, 183, 170);
}

