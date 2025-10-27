package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Tache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TacheService implements IDao<Tache> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Tache o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Tache o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Tache o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Tache findById(int id) {
        return getCurrentSession().get(Tache.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tache> findAll() {
        return getCurrentSession()
                .createQuery("from Tache", Tache.class)
                .list();
    }

    // Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
    @Transactional(readOnly = true)
    public List<Tache> getTachesPrixSuperieur1000() {
        Query<Tache> query = getCurrentSession().createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
        query.setParameter("prixMin", 1000.0);
        return query.list();
    }

    // Afficher les tâches réalisées entre deux dates
    @Transactional(readOnly = true)
    public List<Tache> getTachesRealiseesBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        Query<Tache> query = getCurrentSession().createNamedQuery("Tache.findByDatesRealisation", Tache.class);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
    }

    // Méthode générique pour les tâches avec prix supérieur
    @Transactional(readOnly = true)
    public List<Tache> getTachesPrixSuperieur(double prixMin) {
        Query<Tache> query = getCurrentSession().createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
        query.setParameter("prixMin", prixMin);
        return query.list();
    }
}