package oop;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Info {
    private java.lang.String[] tegelasteNimekiri = {"Libahunt", "Käsilane", "Müürsepp", "Selgeltnägija",
            "Röövel", "Paharet",
            "Joodik", "Unetu", "Kütt", "Äbarik", "Külaelanik"};
    private Map<String, ArrayList<Integer>> parameetrid;
    private static Map<String, String> tekst;
    private int[] tegelased = {2, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0};

    public int[] getTegelased() {
        return this.tegelased;
    }

    public Map<String, ArrayList<Integer>> getParameetrid() {
        return parameetrid;
    }

    public void setTegelased(int[] tegelased) {this.tegelased = tegelased;}

    public int getTegelasedLength(){
        return tegelased.length;
    }

    public Integer getMangutaimer() {
        return this.parameetrid.get("mängutaimer").get(0);
    }

    public Integer getRollitaimer() {
        return this.parameetrid.get("rollitaimer").get(0);
    }

    public String getTekst(String võti) {
        return this.tekst.get(võti);
    }

    public void setRoll(int aeg){
        ArrayList<Integer> nimekiri = new ArrayList<>();
        nimekiri.add(aeg);
        parameetrid.put("rollitaimer", nimekiri);
    }
    public void setTaimer(int aeg){
        ArrayList<Integer> nimekiri = new ArrayList<>();
        nimekiri.add(aeg);
        parameetrid.put("mängutaimer", nimekiri);
    }

    public void getPara(String võti) {
        System.out.println(parameetrid.get(võti).get(0));
    }

    public void seaded() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Seadete muutmine: ");
        System.out.println("Trüki \"MT\", et muuta voorutaimerit.");
        System.out.println("Trüki \"RT\", et muuta lihtsate rollide taimerit.");
        System.out.println("Trüki \"RTR\", et muuta raskete rollide taimerit.");
        int i = 1;
        while (i == 1) {
            String valik = scan.nextLine().trim().toUpperCase();
            switch (valik) {
                case "MT"://voorutaimer
                    System.out.println("Soovid muuta voorutaimerit.");
                    System.out.println("Palun sisesta uus aeg sekundites: ");
                    parameetrid.put("mängutaimer", new ArrayList<Integer>(scan.nextInt()));
                    i = 0;
                    break;
                case "RT"://lihtsate rollide taimer
                    System.out.println("Soovid muuta lihtsate rollide taimerit.");
                    System.out.println("Palun sisesta uus aeg sekundites: ");
                    parameetrid.put("rollitaimer", new ArrayList<Integer>(scan.nextInt()));
                    i = 0;
                    break;
                case "RTR"://raskete rollide taimer
                    System.out.println("Soovid muuta raskete rollide taimerit.");
                    System.out.println("Palun sisesta uus aeg sekundites: ");
                    parameetrid.put("rollitaimerRaske", new ArrayList<Integer>(scan.nextInt()));
                    i = 0;
                    break;
                default:
                    System.out.println("Ebaõnnestunud sisend, proovi uuesti");
            }
        }
    }

    public void juhuslikudTegelased(int mängijateArv) throws InterruptedException {
        //meetod juhuslike tegelaste valimiseks
        if (mängijateArv > 10 || mängijateArv < 3) {
            System.out.println("Mängijate arv võib olla 3-10, palun sisesta uus mängijate arv: ");
        } else {
            int[] juhuslikudTegelased = new int[11];
            // massiiv, kus on iga tegelase kohta number, mitu tükki mängus on
            juhuslikudTegelased[0] = 2; // alati 2 libahunti
            int loositavateTegelasteArv = mängijateArv + 1;
            int i = 0;
            while (i < loositavateTegelasteArv) { // tsükke tegelaste loosimiseks
                int tegelaseNumber = (int) Math.round(Math.random() * 9 + 1); // vahemikus 1 - 10 juhusliku arvu leidmine
                if (juhuslikudTegelased[tegelaseNumber] == 0) { // kui tegelast pole, siis kontrollib, et kui Müürsepp, siis paneb kohe 2 tegelast
                    if (tegelaseNumber == 2) {
                        juhuslikudTegelased[2] = 2;
                        i += 2;
                    } else {
                        juhuslikudTegelased[tegelaseNumber] = 1;
                        i++;
                    }
                }
            }
            tegelased = juhuslikudTegelased;
            System.out.println("Kõik tegelased on valitud! Mängus on: ");
            for (int k = 0; k < juhuslikudTegelased.length; k++) {
                if (juhuslikudTegelased[k] > 0)
                    System.out.println(juhuslikudTegelased[k] + " " + tegelasteNimekiri[k]);
            }
        }
        System.out.println("Mäng hakkab peagi");
        TimeUnit.SECONDS.sleep(4);
    }


    public void setTegelased(int z) {
        //Võtab sisendit ja hakkab lisama tegelasi massiivi
        //Küsimise järjekord on Käsilane, Müürsepp(müürsepp on 2 tegelast), Selgeltnägija, Röövel, Paharet, Joodik, Unetu, Kütt, Äbarik, 3 Külaelanikku
        int[] valitudTegelased = new int[11];
        Scanner scan = new Scanner(System.in);
        valitudTegelased[0] = 2; // alati 2 libahunti
        System.out.println("Mängus on 2 libahunti, keda soovid veel lisada?");
        while (true) {
            for (int i = z + 1, j = 1; i > 0 && j < tegelasteNimekiri.length; j++) {
                while (true) {
                    System.out.println("Valida veel " + i + " tegelast. Kas lisame: " + tegelasteNimekiri[j] + "? (Y/N)");
                    String valik = scan.nextLine().trim().toUpperCase();
                    if (valik.equals("Y") && !tegelasteNimekiri[j].equals("Müürsepp") && !tegelasteNimekiri[j].equals("Külaelanik")) {
                        valitudTegelased[j] = 1;
                        i--;
                        break;
                    }
                    if (valik.equals("Y") && tegelasteNimekiri[j].equals("Müürsepp")) {
                        valitudTegelased[j] = 2;
                        i -= 2;
                        break;
                    }
                    if (valik.equals("Y") && tegelasteNimekiri[j].equals("Külaelanik")) {
                        System.out.println("Mitu külaelanikku lisame? ");
                        int külaelanikeArv = scan.nextInt();
                        valitudTegelased[j] = külaelanikeArv;
                        i -= külaelanikeArv;
                        break;
                    }
                    if (valik.equals("N")) {
                        valitudTegelased[j] = 0;
                        break;
                    } else {
                        System.out.println("Midagi läks valesti, proovi uuesti!");
                    }
                }
            }
            int tegelasteArv = IntStream.of(valitudTegelased).sum();
            if (tegelasteArv < z + 3)
                System.out.println("Valisid liiga vähe tegelasi. Palun vali uuesti.");
            else break;
        }
        System.out.println("Kõik tegelased on valitud! Mängus on: ");
        for (int k = 0; k < valitudTegelased.length; k++) {
            if (valitudTegelased[k] > 0)
                System.out.println(valitudTegelased[k] + " " + tegelasteNimekiri[k]);
        }
        tegelased = valitudTegelased;
    }

    public Info() throws Exception {
        this.parameetrid = new HashMap<>(); //hashmap parameetrite jaoks
        ArrayList<String> defaultsAndmed = Tekst("Default.txt"); //loeb failist andmed
        for (String value : defaultsAndmed) { //tsükke failist saadud ridade läbimiseks
            String[] reaJupid = value.trim().split("-"); //jagab faili rea juppideks
            String asenda = reaJupid[1].replace("[", "").replace("]", "");//eemaldab märgid "[" ja "]" .
            String[] elemendiJupid = asenda.split(",");// jagab rea 2. elemendi juppideks
            ArrayList<Integer> elemendid = new ArrayList<>(); // loob teise elemendi jaoks listi
            for (String s : elemendiJupid) {
                // käib tsükliga rea teise elemendi jupid läbi ja teisendab täisarvuks
                elemendid.add(Integer.parseInt(s.trim()));
            }
            parameetrid.put(reaJupid[0], elemendid);// lisab parameetrite hashmapi uue elemendi
        }
        this.tekst = new HashMap<>(); //hashmap parameetrite jaoks
        ArrayList<String> defaultsAndmed2 = Tekst("Tekst.txt"); //loeb failist andmed
        for (String s : defaultsAndmed2) { //tsükke failist saadud ridade läbimiseks
            String[] reaJupid = s.trim().split("-"); //jagab faili rea juppideks
            String asenda2 = reaJupid[1].replace("[", "").replace("]", "");//eemaldab märgid "[" ja "]" .
            tekst.put(reaJupid[0], asenda2);// lisab parameetrite hashmapi uue elemendi
        }
        for (int i = 0; i < 8; i++) {
            tegelased[i] = parameetrid.get("tegelased").get(i);
        }
    }

    //Loeb sisse teksti reahaaval ja annab Info meetodile
    public ArrayList<String> Tekst(String failiNimi) throws Exception {
        //meetod failist sisse lugemiseks (saab kasutada libahunt.txt ja default.txt sisse lugemiseks
        ArrayList<String> tekst = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(failiNimi), "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                tekst.add(rida);
            }
        }
        return tekst;
    }

    public String[] laused(String võti) {
        String osa = tekst.get(võti);
        String[] tükid = osa.split("/");
        for (String s : tükid) {
            System.out.println(s);
        }
        return tükid;
    }

    public void TekstTagasi() {
//        //Kirjutab defauldid tagasi "Default.txt" faili
        ArrayList<Integer> tegelased_listina = new ArrayList<>();
        for (int el : tegelased){
            tegelased_listina.add(el);
        }
        parameetrid.put("tegelased", tegelased_listina);

        try (java.io.PrintWriter defaultid = new java.io.PrintWriter("Default.txt", "UTF-8")) {
            for (Map.Entry<String, ArrayList<Integer>> e : parameetrid.entrySet()
                //for-each tsükkel uue defaulti hashmapi läbi käimiseks ja faili printimiseks
            ) {
                defaultid.println(e.getKey()
                        + "-" + e.getValue());
            }
        } catch (Exception exception){
            System.out.println("Ei leidnud default faili kirjutamisel");
        }
        try (java.io.PrintWriter defaultid = new java.io.PrintWriter("Tekst.txt", "UTF-8")) {
            for (Map.Entry<String, String> e : tekst.entrySet()
                //for-each tsükkel uue defaulti hashmapi läbi käimiseks ja faili printimiseks
            ) {
                defaultid.println(e.getKey()
                        + "-[" + e.getValue() + "]");
            }
        } catch (Exception e){
            System.out.println("Ei leidnud tekstifaili");
        }
    }
}
