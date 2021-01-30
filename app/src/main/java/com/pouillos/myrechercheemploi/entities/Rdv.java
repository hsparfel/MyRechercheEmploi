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

import java.util.Date;

import com.pouillos.myrechercheemploi.enumeration.TypeRdv;

import com.pouillos.myrechercheemploi.dao.ContactDao;
import com.pouillos.myrechercheemploi.dao.RdvDao;

@Entity
public class Rdv implements Comparable<Rdv>{

    @Id
    private Long id;

    @NotNull
    private long contactId;

    @ToOne(joinProperty = "contactId")
    private Contact contact;

    @NotNull
    private Date date;

    @NotNull
    private String dateString;

    @Convert(converter = TypeRdvConverter.class, columnType = Long.class)
    private TypeRdv typeRdv;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 533885189)
    private transient RdvDao myDao;


    @Generated(hash = 792070719)
    public Rdv(Long id, long contactId, @NotNull Date date, @NotNull String dateString,
            TypeRdv typeRdv) {
        this.id = id;
        this.contactId = contactId;
        this.date = date;
        this.dateString = dateString;
        this.typeRdv = typeRdv;
    }

    @Generated(hash = 1174504408)
    public Rdv() {
    }

    @Generated(hash = 321829790)
    private transient Long contact__resolvedKey;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public long getContactId() {
        return this.contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public TypeRdv getTypeRdv() {
        return this.typeRdv;
    }

    public void setTypeRdv(TypeRdv typeRdv) {
        this.typeRdv = typeRdv;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 714839487)
    public Contact getContact() {
        long __key = this.contactId;
        if (contact__resolvedKey == null || !contact__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactDao targetDao = daoSession.getContactDao();
            Contact contactNew = targetDao.load(__key);
            synchronized (this) {
                contact = contactNew;
                contact__resolvedKey = __key;
            }
        }
        return contact;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 675168246)
    public void setContact(@NotNull Contact contact) {
        if (contact == null) {
            throw new DaoException(
                    "To-one property 'contactId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contact = contact;
            contactId = contact.getId();
            contact__resolvedKey = contactId;
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
    @Generated(hash = 365600449)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRdvDao() : null;
    }

    public static class TypeRdvConverter implements PropertyConverter<TypeRdv, Long> {
        @Override
        public TypeRdv convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (TypeRdv typeRdv : TypeRdv.values()) {
                if (typeRdv.id == databaseValue) {
                    return typeRdv;
                }
            }
            return TypeRdv.Default;
        }

        @Override
        public Long convertToDatabaseValue(TypeRdv entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    @Override
    public String toString() {
        return this.getContact().getNom()+" - "+this.getContact().getSociete().getNom()+" - "+this.getDateString();
    }

    @Override
    public int compareTo(Rdv o) {
        return this.date.compareTo(o.date);
    }

}
