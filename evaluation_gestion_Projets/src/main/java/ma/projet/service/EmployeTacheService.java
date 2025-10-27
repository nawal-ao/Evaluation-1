package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.EmployeTache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeTacheService implements IDao<EmployeTache> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(EmployeTache o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(EmployeTache o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(EmployeTache o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeTache findById(int id) {
        return getCurrentSession().get(EmployeTache.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeTache> findAll() {
        return getCurrentSession()
                .createQuery("from EmployeTache", EmployeTache.class)
                .list();
    }
}