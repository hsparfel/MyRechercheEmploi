package com.pouillos.myrechercheemploi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TypeSociete {

    //Objets directement construits
    Default(0,"?"),
    Esn(1,"Esn"),
    Client(2,"Client");

    public long id;
    public String name = "";

    //Constructeur
    TypeSociete(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public List<TypeSociete> listAll() {
        List<TypeSociete> listToReturn = new ArrayList<>();
        List<TypeSociete> listTypeSociete = Arrays.asList(TypeSociete.values());
        for (TypeSociete typeSociete : listTypeSociete) {
            if (typeSociete != TypeSociete.Default) {
                listToReturn.add(typeSociete);
            }
        }
        return listToReturn;
    }
}
