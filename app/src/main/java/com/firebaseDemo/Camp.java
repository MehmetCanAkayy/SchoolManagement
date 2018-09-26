package com.firebaseDemo;

public class Camp {
    String tur;
    String yuzde;
    String kAlana;
    String kAlanaHediye;
    String hediye;
    String key;
    String mesaj;


    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getYuzde() {
        return yuzde;
    }

    public void setYuzde(String yuzde) {
        this.yuzde = yuzde;
    }

    public String getkAlana() {
        return kAlana;
    }

    public void setkAlana(String kAlana) {
        this.kAlana = kAlana;
    }

    public String getkAlanaHediye() {
        return kAlanaHediye;
    }

    public void setkAlanaHediye(String kAlanaHediye) {
        this.kAlanaHediye = kAlanaHediye;
    }

    public String getHediye() {
        return hediye;
    }

    public void setHediye(String hediye) {
        this.hediye = hediye;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Camp(String key, String tur, String yuzde, String kAlana, String kAlanaHediye, String hediye, String mesaj) {
        this.tur = tur;
        this.yuzde = yuzde;
        this.kAlana = kAlana;
        this.kAlanaHediye = kAlanaHediye;
        this.hediye = hediye;

        this.key=key;
        this.mesaj=mesaj;
    }
    public Camp(){}


}
