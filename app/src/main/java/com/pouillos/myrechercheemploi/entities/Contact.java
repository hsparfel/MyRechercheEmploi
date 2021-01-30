package com.pouillos.myrechercheemploi.entities;

import com.pouillos.myrechercheemploi.dao.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;


import com.pouillos.myrechercheemploi.enumeration.TypeContact;
import com.pouillos.myrechercheemploi.dao.ContactDao;
import com.pouillos.myrechercheemploi.dao.SocieteDao;




@Entity
public class Contact implements Comparable<Contact>{

    @Id
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @Convert(converter = TypeContactConverter.class, columnType = Long.class)
    private TypeContact typeContact;


    private String tel;


    private String mail;


    @NotNull
    private long societeId;

    @ToOne(joinProperty = "societeId")
    private Societe societe;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2046468181)
    private transient ContactDao myDao;





    @Generated(hash = 1276240375)
    public Contact(Long id, @NotNull String nom, @NotNull String prenom, TypeContact typeContact,
            String tel, String mail, long societeId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.typeContact = typeContact;
        this.tel = tel;
        this.mail = mail;
        this.societeId = societeId;
    }





    @Generated(hash = 672515148)
    public Contact() {
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





    public String getPrenom() {
        return this.prenom;
    }





    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }





    public TypeContact getTypeContact() {
        return this.typeContact;
    }





    public void setTypeContact(TypeContact typeContact) {
        this.typeContact = typeContact;
    }





    public String getTel() {
        return this.tel;
    }





    public void setTel(String tel) {
        this.tel = tel;
    }





    public String getMail() {
        return this.mail;
    }





    public void setMail(String mail) {
        this.mail = mail;
    }





    public long getSocieteId() {
        return this.societeId;
    }





    public void setSocieteId(long societeId) {
        this.societeId = societeId;
    }





    @Generated(hash = 231511371)
    private transient Long societe__resolvedKey;





    /** To-one relationship, resolved on first access. */
    @Generated(hash = 860249786)
    public Societe getSociete() {
        long __key = this.societeId;
        if (societe__resolvedKey == null || !societe__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SocieteDao targetDao = daoSession.getSocieteDao();
            Societe societeNew = targetDao.load(__key);
            synchronized (this) {
                societe = societeNew;
                societe__resolvedKey = __key;
            }
        }
        return societe;
    }





    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1330150812)
    public void setSociete(@NotNull Societe societe) {
        if (societe == null) {
            throw new DaoException(
                    "To-one property 'societeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.societe = societe;
            societeId = societe.getId();
            societe__resolvedKey = societeId;
        }
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }





    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }





    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2088270543)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContactDao() : null;
    }





    public static class TypeContactConverter implements PropertyConverter<TypeContact, Long> {
        @Override
        public TypeContact convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (TypeContact typeContact : TypeContact.values()) {
                if (typeContact.id == databaseValue) {
                    return typeContact;
                }
            }
            return TypeContact.Default;
        }

        @Override
        public Long convertToDatabaseValue(TypeContact entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    @Override
    public String toString() {
        return nom+" "+prenom+" - "+this.getSociete().getNom()+" - "+this.getTypeContact().toString();
    }

    @Override
    public int compareTo(Contact o) {
        return this.nom.compareTo(o.nom);
    }

}
