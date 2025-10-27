package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Commande o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Commande o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Commande o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Commande findById(int id) {
        return getCurrentSession().get(Commande.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commande> findAll() {
        return getCurrentSession()
                .createQuery("from Commande", Commande.class)
                .list();
    }
}