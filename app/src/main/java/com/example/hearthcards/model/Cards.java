package com.example.hearthcards.model;

public class Cards {

    private String Id;
    private String Name;
    private String Flavor;
    private String Artist;
    private String Classe;

    public Cards() {
    }

    public Cards(String id, String name, String flavor, String artist, String classe) {
        Id = id;
        Name = name;
        Flavor = flavor;
        Artist = artist;
        Classe = classe;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getFlavor() {
        return Flavor;
    }

    public String getArtist() {
        return Artist;
    }

    public String getClasse() {
        return Classe;
    }


    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setFlavor(String flavor) {
        Flavor = flavor;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public void setClasse(String classe) {
        Classe = classe;
    }
}
