package com.pouillos.myrechercheemploi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum AvancementSociete {

    //Objets directement construits
    Default(0,"?"),
    PremierContact(1,"1er Contact"),
    EntretienRH(2,"Entretien RH"),
    EntretienTechnique(3,"Entretien Technique"),
    EntretienManager(4,"Entretien Manager"),
    EntretienAffaire(5,"Entretien Affaire"),
    TestTechnique(6,"Test Technique");

    public long id;
    public String name = "";

    //Constructeur
    AvancementSociete(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public List<AvancementSociete> listAll() {
        List<AvancementSociete> listToReturn = new ArrayList<>();
        List<AvancementSociete> listAvancementSociete = Arrays.asList(AvancementSociete.values());
        for (AvancementSociete avancementSociete : listAvancementSociete) {
            if (avancementSociete != AvancementSociete.Default) {
                listToReturn.add(avancementSociete);
            }
        }
        return listToReturn;
    }

    public static AvancementSociete rechercheParNom(String nom){
        for(AvancementSociete v : values()){
            if( v.name.equals(nom)){
                return v;
            }
        }
        return null;
    }
}
