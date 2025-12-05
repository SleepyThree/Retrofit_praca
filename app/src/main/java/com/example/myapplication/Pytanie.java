package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("tresc")
    private String trescPytania;
    private String odpa;
    private String odpb;
    private String odpc;
    private int poprawna;

    public Pytanie(String trescPytania, int poprawna, String odpc, String odpb, String odpa) {
        this.trescPytania = trescPytania;
        this.poprawna = poprawna;
        this.odpc = odpc;
        this.odpb = odpb;
        this.odpa = odpa;
    }

    public String getTrescPytania() {
        return trescPytania;
    }

    public void setTrescPytania(String trescPytania) {
        this.trescPytania = trescPytania;
    }

    public int getPoprawna() {
        return poprawna;
    }

    public void setPoprawna(int poprawna) {
        this.poprawna = poprawna;
    }

    public String getOdpc() {
        return odpc;
    }

    public void setOdpc(String odpc) {
        this.odpc = odpc;
    }

    public String getOdpb() {
        return odpb;
    }

    public void setOdpb(String odpb) {
        this.odpb = odpb;
    }

    public String getOdpa() {
        return odpa;
    }

    public void setOdpa(String odpa) {
        this.odpa = odpa;
    }
}
