package com.angadi.generator;

import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.List;

public class MySQLSequenceGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        String sequenceName = "hibernate_sequence";
        String query = "SELECT next_val FROM " + sequenceName + " FOR UPDATE";

        @SuppressWarnings("rawTypes")
        Query q = session.createNativeQuery(query);

        List results = q.getResultList();
        Integer nextVal = 1;

        if (!results.isEmpty()) nextVal = (Integer) results.get(0);

        query = "UPDATE " + sequenceName + " SET next_val = :nextVal";

        q = session.createNativeQuery(query).setParameter("nextVal", nextVal + 1);
        q.executeUpdate();

        return nextVal;
    }
}
