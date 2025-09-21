package com.example.myapplication;

public class Zekir_ {
    private String _zekir;
    private String _note;
    private int _counts;
    private char _category;

    public Zekir_(String _zekir) {
        this._zekir = _zekir;
    }

    public Zekir_(String _zekir, int _counts, char _category) {
        this._zekir = _zekir;
        this._counts = _counts;
        this._category = _category;
    }

    public Zekir_(String _note, String _zekir, int _counts, char _category) {
        this._note = _note;
        this._zekir = _zekir;
        this._counts = _counts;
        this._category = _category;
    }

    public String get_zekir() {
        return _zekir;
    }

    public void set_zekir(String _zekir) {
        this._zekir = _zekir;
    }

    public int get_counts() {
        return _counts;
    }

    public void set_counts(int _counts) {
        this._counts = _counts;
    }

    public char get_category() {
        return _category;
    }

    public void set_category(char _category) {
        this._category = _category;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public void add_Zekir(String _zekir, int _counts, char _category){

    }
}
