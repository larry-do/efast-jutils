package com.eazitasc.security;

import com.eazitasc.entity.Sys_persistent_login;
import com.eazitasc.util.EasyUtils;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository(value = "HibernatePersistentTokenRepository")
@Transactional
public class HibernatePersistentTokenRepositoryImpl implements PersistentTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        Sys_persistent_login persistentLogin = new Sys_persistent_login();
        persistentLogin.setUsername(persistentRememberMeToken.getUsername());
        persistentLogin.setSeries(persistentRememberMeToken.getSeries());
        persistentLogin.setLast_used(persistentRememberMeToken.getDate());
        persistentLogin.setToken(persistentRememberMeToken.getTokenValue());
        this.entityManager.persist(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        Sys_persistent_login persistentLogin = this.entityManager.find(Sys_persistent_login.class, series);
        if (persistentLogin == null) return null;
        return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLast_used());
    }

    @Override
    public void updateToken(String series, String token, Date lastDate) {
        Sys_persistent_login persistentLogin = entityManager.find(Sys_persistent_login.class, series);
        persistentLogin.setToken(token);
        persistentLogin.setLast_used(lastDate);
        entityManager.merge(persistentLogin);
    }

    @Override
    public void removeUserTokens(String series) {
        Sys_persistent_login persistentLogin = entityManager.find(Sys_persistent_login.class, series);
        if (persistentLogin == null) return;
        entityManager.remove(persistentLogin);
    }
}
