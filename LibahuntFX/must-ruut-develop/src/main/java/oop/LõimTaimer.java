package oop;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class LõimTaimer extends Thread {
    private final String[] tegelasteNimekiri = {"Heli\\Libahunt.wav", "Heli\\Käsilane.wav", "Heli\\Müürsepp.wav", "Heli\\Selgeltnägija.wav", "Heli\\Röövel.wav", "Heli\\Paharet.wav",
            "Heli\\Joodik.wav", "Heli\\Unetu.wav", "Heli\\Intro.wav", "Heli\\Outro.wav"};
    private final String[] tegelasteNimekiri2 = {"Heli\\Libahunt2.wav", "Heli\\Käsilane2.wav", "Heli\\Müürsepp2.wav", "Heli\\Selgeltnägija2.wav", "Heli\\Röövel2.wav", "Heli\\Paharet2.wav",
            "Heli\\Joodik2.wav", "Heli\\Unetu2.wav"};

    Boolean taimer = true;
    IntegerProperty aeg;
    Info info;

    public LõimTaimer(IntegerProperty aeg, Info info) {
        this.aeg = aeg;
        this.info = info;
    }

    public void run() {

        try {

            int[] tegelased = info.getTegelased();
            Integer rollitaimer = info.getRollitaimer();
            Clip klipp = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(tegelasteNimekiri[8]).getAbsoluteFile());
            klipp.open(audioInputStream);
            klipp.start();
            TimeUnit.MICROSECONDS.sleep(klipp.getMicrosecondLength());

            klipp.stop();
            klipp.close();
            if (Thread.interrupted()) {
                throw new Exception();
            }


            for (int x = 0; x < 8; x++) {
                if (tegelased[x] != 0) {

                    audioInputStream = AudioSystem.getAudioInputStream(new File(tegelasteNimekiri[x]).getAbsoluteFile());
                    klipp.open(audioInputStream);
                    klipp.start();
                    TimeUnit.MICROSECONDS.sleep(klipp.getMicrosecondLength());

                    klipp.stop();
                    klipp.close();
                    TimeUnit.SECONDS.sleep(info.getRollitaimer());
                    if (Thread.interrupted()) {
                        throw new Exception();
                    }


                    audioInputStream = AudioSystem.getAudioInputStream(new File(tegelasteNimekiri2[x]).getAbsoluteFile());
                    klipp.open(audioInputStream);
                    klipp.start();
                    TimeUnit.MICROSECONDS.sleep(klipp.getMicrosecondLength());

                    klipp.stop();
                    klipp.close();
                    if (Thread.interrupted()) {
                        throw new Exception();
                    }

                }
            }
            audioInputStream = AudioSystem.getAudioInputStream(new File(tegelasteNimekiri[9]).getAbsoluteFile());
            klipp.open(audioInputStream);
            klipp.start();
            TimeUnit.MICROSECONDS.sleep(klipp.getMicrosecondLength());


            klipp.stop();
            klipp.close();
            long algaeg = System.currentTimeMillis();
            while (taimer) {

                if (System.currentTimeMillis() - algaeg > 1000) {
                    if (Thread.interrupted()) {
                        throw new Exception();
                    }
                    //System.out.println("olen loobis");
                    aeg.setValue(aeg.getValue() - 1);
                    //System.out.println(aeg.toString());
                    algaeg = System.currentTimeMillis();
                    if (aeg.getValue() == 0) {
                        taimer = false;
                    }
                }

            }
            audioInputStream = AudioSystem.getAudioInputStream(new File("Heli\\Notice.wav").getAbsoluteFile());
            klipp.open(audioInputStream);
            klipp.start();
            TimeUnit.MICROSECONDS.sleep(klipp.getMicrosecondLength());

            klipp.stop();
            klipp.close();
            return;

        } catch (Exception exception) {
            System.out.println("Thread pandi kinni");
            return;
        }

        /*long algaeg = System.currentTimeMillis();

        try {
            while (taimer) {

                if (System.currentTimeMillis() - algaeg > 1000) {
                    if (Thread.interrupted()) {
                        throw new Exception();
                    }
                    //System.out.println("olen loobis");
                    aeg.setValue(aeg.getValue() - 1);
                    //System.out.println(aeg.toString());
                    algaeg = System.currentTimeMillis();
                    if (aeg.getValue() == 0) {
                        taimer = false;
                    }
                }

            }
        } catch (Exception e){
            System.out.println("Taimer pandi kinni");
            return;
        }*/
    }
}
