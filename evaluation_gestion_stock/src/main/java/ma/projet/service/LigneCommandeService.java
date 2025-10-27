package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.LigneCommandeProduit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(LigneCommandeProduit o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(LigneCommandeProduit o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public LigneCommandeProduit findById(int id) {
        return getCurrentSession().get(LigneCommandeProduit.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneCommandeProduit> findAll() {
        return getCurrentSession()
                .createQuery("from LigneCommandeProduit", LigneCommandeProduit.class)
                .list();
    }
}