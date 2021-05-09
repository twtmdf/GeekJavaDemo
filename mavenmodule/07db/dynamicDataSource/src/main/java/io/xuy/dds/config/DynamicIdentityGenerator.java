package io.xuy.dds.config;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGeneratorHelper;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class DynamicIdentityGenerator extends IdentityGenerator implements Configurable {

    private String entityName;

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object object)throws HibernateException {
        final Serializable id = s.getEntityPersister( entityName, object ).getIdentifier( object, s );
        if ( id == null ) {
            return IdentifierGeneratorHelper.POST_INSERT_INDICATOR;
        }
        return id;
    }
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        entityName = params.getProperty(ENTITY_NAME);
        if ( entityName == null ) {
            throw new MappingException("no entity name");
        }
    }
}
