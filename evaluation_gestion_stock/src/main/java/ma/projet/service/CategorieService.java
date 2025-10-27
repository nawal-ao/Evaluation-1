package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Categorie o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Categorie o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Categorie findById(int id) {
        return getCurrentSession().get(Categorie.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        return getCurrentSession()
                .createQuery("from Categorie", Categorie.class)
                .list();
    }
}