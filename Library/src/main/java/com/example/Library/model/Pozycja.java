package com.example.Library.model;

public class Pozycja {

    private String ISBN;
    private String autorName;
    private String tytul;
    private String wydawcaName;
    private int rok;
    private double cena;

    public Pozycja(String ISBN, String autorName, String tytul, String wydawcaName, int rok, double cena) {
        this.ISBN = ISBN;
        this.autorName = autorName;
        this.tytul = tytul;
        this.wydawcaName = wydawcaName;
        this.rok = rok;
        this.cena = cena;
    }

    public Pozycja() {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAutorName() {
        return autorName;
    }

    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getWydawcaName() {
        return wydawcaName;
    }

    public void setWydawcaName(String wydawcaName) {
        this.wydawcaName = wydawcaName;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Pozycja{" +
                "ISBN='" + ISBN + '\'' +
                ", autorName='" + autorName + '\'' +
                ", tytul='" + tytul + '\'' +
                ", wydawcaName='" + wydawcaName + '\'' +
                ", rok=" + rok +
                ", cena=" + cena +
                '}';
    }
}
