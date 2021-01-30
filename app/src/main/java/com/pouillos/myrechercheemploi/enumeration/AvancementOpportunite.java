package com.pouillos.myrechercheemploi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum AvancementOpportunite {

    //Objets directement construits
    Default(0,"?"),
    Presentation(1,"Presentation"),
    CV(2,"CV"),
    Entretien(3,"Entretien"),
    EnAttente(4,"En Attente"),
    Refuse(5,"Refuse");

    public long id;
    public String name = "";

    //Constructeur
    AvancementOpportunite(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public List<AvancementOpportunite> listAll() {
        List<AvancementOpportunite> listToReturn = new ArrayList<>();
        List<AvancementOpportunite> listAvancementOpportunite = Arrays.asList(AvancementOpportunite.values());
        for (AvancementOpportunite avancementOpportunite : listAvancementOpportunite) {
            if (avancementOpportunite != AvancementOpportunite.Default) {
                listToReturn.add(avancementOpportunite);
            }
        }
        return listToReturn;
    }

    public static AvancementOpportunite rechercheParNom(String nom){
        for(AvancementOpportunite v : values()){
            if( v.name.equals(nom)){
                return v;
            }
        }
        return null;
    }
}
