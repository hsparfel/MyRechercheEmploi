package com.pouillos.myrechercheemploi.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TypeRdv {

    //Objets directement construits
    Default(0,"?"),
    Site(1,"Site"),
    Visio(2,"Visio"),
    Tel(3,"Tel");

    public long id;
    public String name = "";

    //Constructeur
    TypeRdv(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public List<TypeRdv> listAll() {
        List<TypeRdv> listToReturn = new ArrayList<>();
        List<TypeRdv> listTypeRdv = Arrays.asList(TypeRdv.values());
        for (TypeRdv typeRdv : listTypeRdv) {
            if (typeRdv != TypeRdv.Default) {
                listToReturn.add(typeRdv);
            }
        }
        return listToReturn;
    }
}
