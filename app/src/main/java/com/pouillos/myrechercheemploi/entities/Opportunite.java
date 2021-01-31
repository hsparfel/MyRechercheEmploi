package com.pouillos.myrechercheemploi.entities;

import com.pouillos.myrechercheemploi.enumeration.AvancementOpportunite;


import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.pouillos.myrechercheemploi.dao.DaoSession;
import com.pouillos.myrechercheemploi.dao.ContactDao;
import com.pouillos.myrechercheemploi.dao.OpportuniteDao;
import com.pouillos.myrechercheemploi.dao.SocieteDao;

@Entity
public class Opportunite implements Comparable<Opportunite>{

    @Id
    private Long id;

    @NotNull
    private String detail;

    @Convert(converter = AvancementOpportuniteConverter.class, columnType = Long.class)
    private AvancementOpportunite avancementOpportunite;

    @NotNull
    private long contactId;

    @ToOne(joinProperty = "contactId")
    private Contact contact;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 435491102)
    private transient OpportuniteDao myDao;

    @Generated(hash = 1170772916)
    public Opportunite(Long id, @NotNull String detail, AvancementOpportunite avancementOpportunite,
            long contactId) {
        this.id = id;
        this.detail = detail;
        this.avancementOpportunite = avancementOpportunite;
        this.contactId = contactId;
    }

    @Generated(hash = 1666832491)
    public Opportunite() {
    }



    @Generated(hash = 321829790)
    private transient Long contact__resolvedKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public AvancementOpportunite getAvancementOpportunite() {
        return avancementOpportunite;
    }

    public void setAvancementOpportunite(AvancementOpportunite avancementOpportunite) {
        this.avancementOpportunite = avancementOpportunite;
    }

    public long getContactId() {
        return this.contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
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
    @Generated(hash = 480234687)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOpportuniteDao() : null;
    }



    public static class AvancementOpportuniteConverter implements PropertyConverter<AvancementOpportunite, Long> {
        @Override
        public AvancementOpportunite convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (AvancementOpportunite avancementOpportunite : AvancementOpportunite.values()) {
                if (avancementOpportunite.id == databaseValue) {
                    return avancementOpportunite;
                }
            }
            return AvancementOpportunite.Default;
        }

        @Override
        public Long convertToDatabaseValue(AvancementOpportunite entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    @Override
    public String toString() {
        return this.getContact().toString()+" - "+this.getDetail();
    }

    @Override
    public int compareTo(Opportunite o) {
        return this.detail.compareTo(o.detail);
    }



}
