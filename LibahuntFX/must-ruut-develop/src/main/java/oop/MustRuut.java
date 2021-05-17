package oop;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MustRuut extends Application {
    private BorderPane juur_intro = new BorderPane();
    private BorderPane juur_reeglid = new BorderPane();
    private BorderPane juur_tegelased = new BorderPane();
    private FlowPane juur_käsud = new FlowPane();
    private BorderPane juur_mäng = new BorderPane();
    private BorderPane juur_seaded = new BorderPane();
    private FlowPane juur_aftermath = new FlowPane();

    private Scene intro_s = new Scene(juur_intro, 400, 400);
    private Scene reeglid_s = new Scene(juur_reeglid, 400, 400);
    private Scene tegelased_s = new Scene(juur_tegelased, 400, 400);
    private Scene käsud = new Scene(juur_mäng, 400, 400);
    private Scene seaded_s = new Scene(juur_seaded, 400, 400);
    private Info info;
    private boolean numbrid = true;

    private IntegerProperty mängutaimer_int = new SimpleIntegerProperty(180);


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage peaLava) throws Exception {
        info = new Info();

        set_scenes(peaLava);
        peaLava.setTitle("Ühe Öö Libahunt");
        peaLava.setScene(intro_s);
        peaLava.show();
    }

    public void set_scenes(Stage pealava) {

        //INTRO STSEEN

        Button alusta_b = new Button("ALUSTA");
        Button reeglid_b = new Button("REEGLID MÄNGUKS");
        Button seaded_b = new Button("SEADED");
        alusta_b.setFont(Font.font("Times New Roman", 40));
        reeglid_b.setFont(Font.font("Times New Roman", 20));
        seaded_b.setFont(Font.font("Times New Roman", 20));

        //TEEB INTRO STSEENI NUPPUDE EVENTHANDLERID
        alusta_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(tegelased_s);
            }
        });
        reeglid_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(reeglid_s);
            }
        });
        seaded_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(seaded_s);
            }
        });

        //PANEB INTRO NUPUD PAIKA
        juur_intro.setBottom(seaded_b);
        juur_intro.setCenter(alusta_b);
        juur_intro.setTop(reeglid_b);
        BorderPane.setAlignment(reeglid_b, Pos.CENTER);
        BorderPane.setAlignment(seaded_b, Pos.CENTER);

        //REEGLID STSEEN

        Button exitReeglid_b = new Button("VÄLJU");
        exitReeglid_b.setFont(Font.font("Times New Roman", 20));
        exitReeglid_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(intro_s);
            }
        });
        Text tekst = new Text();
        String reeglid_string = info.getTekst("reeglid").replace("*", "\n");
        tekst.setText(reeglid_string);//Peab kuidagi failist saama sobivas formaadis. Reavahetused ei taha töötada
        tekst.setFont(Font.font("Times New Roman", 17));
        tekst.setTextAlignment(TextAlignment.CENTER);
        juur_reeglid.setTop(exitReeglid_b);
        juur_reeglid.setCenter(tekst);

        //TEGELASTE VALIMISE STSEEN

        //"Libahunt", "Käsilane", "Müürsepp", "Selgeltnägija", "Röövel", "Paharet",
        //            "Joodik", "Unetu", "Kütt", "Äbarik", "Külaelanik"
        Button exitTegelased = new Button("TAGASI");
        Button selgeltnägija = new Button("SELGELTNÄGIJA");
        Button libahunt1 = new Button("LIBAHUNT");
        libahunt1.setEffect(new Glow());
        Button libahunt2 = new Button("LIBAHUNT");
        libahunt2.setEffect(new Glow());
        Button käsilane = new Button("KÄSILANE");
        Button müürsepp1 = new Button("MÜÜRSEPP");
        Button müürsepp2 = new Button("MÜÜRSEPP");
        Button röövel = new Button("RÖÖVEL");
        Button paharet = new Button("PAHARET");
        Button joodik = new Button("JOODIK");
        Button unetu = new Button("UNETU");
        Button kütt = new Button("KÜTT");
        Button äbarik = new Button("ÄBARIK");
        Button külaelanik1 = new Button("KÜLAELANIK");
        Button külaelanik2 = new Button("KÜLAELANIK");
        Button külaelanik3 = new Button("KÜLAELANIK");
        Button tegelasteSalvestamine = new Button("SALVESTA TEGELASED");

        // tegelaste valiku nupud panin listi
        ArrayList<Button> nupud = new ArrayList<>(Arrays.asList(selgeltnägija, käsilane, müürsepp1, müürsepp2,
                röövel, paharet, joodik, unetu, kütt, äbarik, külaelanik1, külaelanik2, külaelanik3));
        Button voor = new Button("LAS ÖÖ ALATA ...");
        voor.setFont(Font.font("Times New Roman", 20));

        int tegelasteArv = 0;
        int[] defaultTegelased = info.getTegelased();
        for (int tegelane : defaultTegelased
        ) {
            tegelasteArv += tegelane;
        }

        //Tegelaste arvu ja juhisetekstid
        int mängijateArv = tegelasteArv - 3;
        String loendur = "Hetkel salvestatud on " + tegelasteArv + " tegelast. Mängida saab " +
                mängijateArv + " inimest.";
        Text mängijateLoendur = new Text(loendur);
        Text juhisValikuks = new Text("Vali siit loendelust tegelased ja seejärel salvesta. Siis saad ööga alustada.");

        //tsükliga käib defaultid läbi ja paneb valitutele efekti peale (alustab peale libahunti)
        for (int i = 1; i < defaultTegelased.length; i++) {
            if (defaultTegelased[i] > 0) {
                if (i == 1)
                    käsilane.setEffect(new Glow());
                if (i == 2) {
                    müürsepp1.setEffect(new Glow());
                    müürsepp2.setEffect(new Glow());
                }
                if (i == 3)
                    selgeltnägija.setEffect(new Glow());
                if (i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9)
                    nupud.get(i).setEffect(new Glow());
                if (i == 10)
                    if (defaultTegelased[10] == 1)
                        külaelanik1.setEffect(new Glow());
                if (defaultTegelased[10] == 2) {
                    külaelanik1.setEffect(new Glow());
                    külaelanik2.setEffect(new Glow());
                }
                if (defaultTegelased[10] == 3) {
                    külaelanik1.setEffect(new Glow());
                    külaelanik2.setEffect(new Glow());
                    külaelanik3.setEffect(new Glow());
                }
            }
        }

        // tegelaste nuppudele lisasin MouseEventi tsükliga, müürsepad käivad koos
        for (Button nupp : nupud
        ) {
            nupp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (nupp.equals(müürsepp1) || nupp.equals(müürsepp2)) {
                        if (nupp.getEffect() == null) {
                            müürsepp1.setEffect(new Glow());
                            müürsepp2.setEffect(new Glow());
                        } else {
                            müürsepp1.setEffect(null);
                            müürsepp2.setEffect(null);
                        }
                    } else {
                        if (nupp.getEffect() == null) {
                            nupp.setEffect(new Glow());
                        } else {
                            nupp.setEffect(null);
                        }
                    }
                }
            });
        }

        // valitud tegelaste defaulti salvestamine
        tegelasteSalvestamine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                int[] salvestatavad = new int[11];
                salvestatavad[0] = 2;
                int tegelaste_arv2 = 2;
                for (int i = 0; i < nupud.size(); i++) {
                    if (nupud.get(i).getEffect() != null) {
                        tegelaste_arv2++;
                        if (i == 0) {
                            salvestatavad[3] = 1;
                        }
                        if (i == 1) {
                            salvestatavad[1] = 1;
                        }
                        if (i == 2 || i == 3) {
                            salvestatavad[2] = 2;
                            //tegelaste_arv2++;
                        }
                        if (i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9) {
                            salvestatavad[i] = 1;
                        } else {
                            salvestatavad[10] += 1;
                        }
                        ;
                    }
                }
                try {
                    if (tegelaste_arv2 < 6) {
                        throw new TegelasiVäheErind("Liiga vähe valitud tegelasi");
                    }

                    info.setTegelased(salvestatavad);
                    Stage teade = new Stage();
                    Text salvestatud = new Text("Tegelased salvestatud!");

                    Button ok = new Button("OK");

                    VBox vbox = new VBox();
                    vbox.getChildren().addAll(salvestatud, ok);
                    vbox.setAlignment(Pos.CENTER);
                    Scene salv_s = new Scene(vbox, 200, 80);
                    teade.setScene(salv_s);
                    teade.show();
                    int finalTegelaste_arv = tegelaste_arv2;
                    ok.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            teade.hide();
                            mängijateLoendur.setText("Hetkel salvestatud on " + finalTegelaste_arv + " tegelast. Mängida saab " +
                                    (finalTegelaste_arv - 3) + " inimest.");
                        }
                    });
                } catch (TegelasiVäheErind ex) {
                    Stage teade = new Stage();
                    Text salvestatud = new Text("Tegelasi liiga vähe valitud!");
                    VBox vbox = new VBox();
                    vbox.getChildren().addAll(salvestatud);
                    vbox.setAlignment(Pos.CENTER);
                    Scene salv_s = new Scene(vbox, 200, 80);
                    teade.setScene(salv_s);
                    teade.show();
                }
            }
        });

        //Tegelaste valimisest tagasi minemine või alustamine
        exitTegelased.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(intro_s);
            }
        });
        //Hääletusnupp, lisan vahele, sest vajalik lisada eventhandler threadile
        Button hääleta = new Button("HÄÄLETAMA!!!(SPACE)");

        voor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                pealava.setScene(käsud);
                mängutaimer_int.set(info.getMangutaimer());
                LõimTaimer lõim = new LõimTaimer(mängutaimer_int, info);
                lõim.start();
                pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        lõim.interrupt();
                    }
                });

                hääleta.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pealava.setScene(intro_s);
                        lõim.interrupt();

                    }
                });
                hääleta.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.SPACE) {
                            pealava.setScene(intro_s);
                            lõim.interrupt();
                            event.consume();
                        }
                    }
                });
            }
        });

        //Paigutab kõik need nupud
        juur_tegelased.setTop(exitTegelased);
        voor.setAlignment(Pos.CENTER);

        HBox juh_ja_salv = new HBox(5);
        juh_ja_salv.setAlignment(Pos.CENTER);
        juh_ja_salv.getChildren().addAll(tegelasteSalvestamine);
        VBox alumineBox = new VBox(5);
        alumineBox.getChildren().addAll(juhisValikuks, mängijateLoendur, juh_ja_salv, voor);
        alumineBox.setAlignment(Pos.CENTER);
        juur_tegelased.setBottom(alumineBox);

        HBox tegelased = new HBox(10);
        tegelased.setAlignment(Pos.CENTER);
        VBox es_kol = new VBox(10);
        es_kol.getChildren().addAll(libahunt1, libahunt2, käsilane, müürsepp1, müürsepp2);
        VBox te_kol = new VBox(10);
        te_kol.getChildren().addAll(selgeltnägija, röövel, paharet, joodik, unetu);
        VBox ko_kol = new VBox(10);
        ko_kol.getChildren().addAll(kütt, äbarik, külaelanik1, külaelanik2, külaelanik3);
        es_kol.setAlignment(Pos.CENTER);
        te_kol.setAlignment(Pos.CENTER);
        ko_kol.setAlignment(Pos.CENTER);
        tegelased.getChildren().addAll(es_kol, ko_kol, te_kol);
        juur_tegelased.setCenter(tegelased);

        //VOORU stseen

        Text allesjäänud = new Text("Alles on jäänud " + mängutaimer_int.getValue());

        /*hääleta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(intro_s);
            }
        });*/
        /*juur_mäng.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    pealava.setScene(intro_s);
                }
            }
        });*/
        mängutaimer_int.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                allesjäänud.setText("Alles on jäänud: " + newValue.intValue());
                if (newValue.intValue() == 0) {
                    allesjäänud.setText("HÄÄLETAMAAAA!!!!!");
                }
            }
        });
        VBox mänguvoor = new VBox(allesjäänud, hääleta);
        mänguvoor.setAlignment(Pos.CENTER);
        juur_mäng.setCenter(mänguvoor);

        //SEADED STSEEN

        Slider taimer_sli = new Slider(120, 900, info.getMangutaimer());
        Slider tgelane_aeg = new Slider(2, 9, info.getRollitaimer());
        taimer_sli.setBlockIncrement(10);
        tgelane_aeg.setBlockIncrement(1);

        Text tegelanePaus = new Text("Öötegevuse kestvus: " + String.valueOf((int) (tgelane_aeg.getValue())));
        Text vooruAeg = new Text("Vooru kestvus: " + String.valueOf((int) taimer_sli.getValue()));


        taimer_sli.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int aeg = newValue.intValue();
                vooruAeg.setText("Vooru kestvus: " + String.valueOf(aeg));
                info.setTaimer(aeg);
                allesjäänud.setText("Alles on jäänud: " + newValue.intValue());
            }
        });
        tgelane_aeg.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int aeg = newValue.intValue();
                tegelanePaus.setText("Öötegevuse kestvus: " + String.valueOf(aeg));
                info.setRoll(aeg);
            }
        });

        Button seaded_välju = new Button("TAGASI");
        seaded_välju.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pealava.setScene(intro_s);
            }
        });

        juur_seaded.setTop(seaded_välju);
        GridPane raam = new GridPane();
        raam.add(vooruAeg, 0, 0);
        raam.add(taimer_sli, 1, 0);
        raam.add(tegelanePaus, 0, 1);
        raam.add(tgelane_aeg, 1, 1);
        raam.setHgap(10);
        raam.setVgap(10);
        juur_seaded.setCenter(raam);

        //Teen kinnipanemise handleri, et salvestaks muutujad
        pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                info.TekstTagasi();
            }
        });
    }
}

/*//AINULT NUPUD TEHTUD, FUNKTSIONAALSUST VEEL POLE
        juhuslikud.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Stage mitu = new Stage();
                Text salvestatud = new Text("Sisesta mängijate arv vahemikus 3-13");
                TextField m_arv = new TextField();
                String m_arv_st = m_arv.getText();
                Button valmis = new Button("Valmis");
                VBox vbox = new VBox();
                vbox.getChildren().addAll(salvestatud, m_arv, valmis);
                Scene juh_s = new Scene(vbox, 200, 80);
                mitu.setScene(juh_s);
                mitu.show();
                valmis.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        mitu.hide();
                    }
                });
            }
        });*/
