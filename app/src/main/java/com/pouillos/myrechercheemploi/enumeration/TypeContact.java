package com.pouillos.myrechercheemploi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TypeContact {

    //Objets directement construits
    Default(0,"?"),
    RH(1,"RH"),
    Technique(2,"Technique"),
    Manager(3,"Manager"),
    ChargeAffaire(4,"Charge Affaire"),
    Client(5,"Client");

    public long id;
    public String name = "";

    //Constructeur
    TypeContact(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public List<TypeContact> listAll() {
        List<TypeContact> listToReturn = new ArrayList<>();
        List<TypeContact> listTypeContact = Arrays.asList(TypeContact.values());
        for (TypeContact typeContact : listTypeContact) {
            if (typeContact != TypeContact.Default) {
                listToReturn.add(typeContact);
            }
        }
        return listToReturn;
    }
}
