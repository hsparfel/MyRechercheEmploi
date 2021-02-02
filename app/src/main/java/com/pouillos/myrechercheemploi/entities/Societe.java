package com.pouillos.myrechercheemploi.entities;

import com.pouillos.myrechercheemploi.enumeration.TypeSociete;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Societe implements Comparable<Societe> {

    @Id
    private Long id;

    @NotNull
    private String nom;

    private String adresse;

    private String cp;

    private String ville;

    @Convert(converter = TypeSocieteConverter.class, columnType = Long.class)
    private TypeSociete typeSociete;

    private boolean hasPremierContact;
    private boolean hasEntretienRh;
    private boolean hasEntretienTechnique;
    private boolean hasEntretienManager;
    private boolean hasEntretienAffaire;
    private boolean hasTestTechnique;

    @Generated(hash = 1627738957)
    public Societe(Long id, @NotNull String nom, String adresse, String cp, String ville,
            TypeSociete typeSociete, boolean hasPremierContact, boolean hasEntretienRh,
            boolean hasEntretienTechnique, boolean hasEntretienManager,
            boolean hasEntretienAffaire, boolean hasTestTechnique) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.typeSociete = typeSociete;
        this.hasPremierContact = hasPremierContact;
        this.hasEntretienRh = hasEntretienRh;
        this.hasEntretienTechnique = hasEntretienTechnique;
        this.hasEntretienManager = hasEntretienManager;
        this.hasEntretienAffaire = hasEntretienAffaire;
        this.hasTestTechnique = hasTestTechnique;
    }

    @Generated(hash = 1926605167)
    public Societe() {
    }

    


    public static class TypeSocieteConverter implements PropertyConverter<TypeSociete, Long> {
        @Override
        public TypeSociete convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (TypeSociete typeSociete : TypeSociete.values()) {
                if (typeSociete.id == databaseValue) {
                    return typeSociete;
                }
            }
            return TypeSociete.Default;
        }

        @Override
        public Long convertToDatabaseValue(TypeSociete entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }



    @Override
    public String toString() {
        return nom+" - "+typeSociete;
    }

    @Override
    public int compareTo(Societe o) {
        return this.nom.compareTo(o.nom);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return this.cp;
    }

    public void setCp(String Cp) {
        this.cp = Cp;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String Ville) {
        this.ville = Ville;
    }

    public TypeSociete getTypeSociete() {
        return this.typeSociete;
    }

    public void setTypeSociete(TypeSociete typeSociete) {
        this.typeSociete = typeSociete;
    }

    public boolean getHasPremierContact() {
        return this.hasPremierContact;
    }

    public void setHasPremierContact(boolean hasPremierContact) {
        this.hasPremierContact = hasPremierContact;
    }

    public boolean getHasEntretienRh() {
        return this.hasEntretienRh;
    }

    public void setHasEntretienRh(boolean hasEntretienRh) {
        this.hasEntretienRh = hasEntretienRh;
    }

    public boolean getHasEntretienTechnique() {
        return this.hasEntretienTechnique;
    }

    public void setHasEntretienTechnique(boolean hasEntretienTechnique) {
        this.hasEntretienTechnique = hasEntretienTechnique;
    }

    public boolean getHasEntretienManager() {
        return this.hasEntretienManager;
    }

    public void setHasEntretienManager(boolean hasEntretienManager) {
        this.hasEntretienManager = hasEntretienManager;
    }

    public boolean getHasEntretienAffaire() {
        return this.hasEntretienAffaire;
    }

    public void setHasEntretienAffaire(boolean hasEntretienAffaire) {
        this.hasEntretienAffaire = hasEntretienAffaire;
    }

    public boolean getHasTestTechnique() {
        return this.hasTestTechnique;
    }

    public void setHasTestTechnique(boolean hasTestTechnique) {
        this.hasTestTechnique = hasTestTechnique;
    }

    
}
