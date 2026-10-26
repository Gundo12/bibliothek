package com.example.Bucherei.model;

public enum Stockwerk {
    OG1("1. OG"),
    OG2("2. OG");

    private final String bezeichnung;

    Stockwerk(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}

