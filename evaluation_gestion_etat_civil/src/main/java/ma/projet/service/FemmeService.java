package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Femme;
import ma.projet.classes.Mariage;
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
public class FemmeService implements IDao<Femme> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Femme o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Femme o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Femme o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Femme findById(int id) {
        return getCurrentSession().get(Femme.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Femme> findAll() {
        return getCurrentSession()
                .createQuery("from Femme", Femme.class)
                .list();
    }

    // Trouver la femme la plus âgée
    @Transactional(readOnly = true)
    public Femme findFemmeLaPlusAgee() {
        Query<Femme> query = getCurrentSession().createQuery(
                "from Femme f order by f.dateNaissance asc", Femme.class);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    // REMPLACEMENT: Requête native pour le nombre d'enfants d'une femme entre deux dates
    @Transactional(readOnly = true)
    public long getNombreEnfantsBetweenDates(int femmeId, LocalDate dateDebut, LocalDate dateFin) {
        // Utilisation d'une requête native simple
        Query<Object[]> query = getCurrentSession().createNativeQuery(
                "SELECT SUM(m.nombre_enfants) FROM mariage m " +
                        "WHERE m.femme_id = :femmeId " +
                        "AND m.date_debut BETWEEN :dateDebut AND :dateFin");
        query.setParameter("femmeId", femmeId);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0;
    }

    // Requête nommée - Femmes mariées au moins deux fois
    @Transactional(readOnly = true)
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Query<Femme> query = getCurrentSession().createNamedQuery(
                "Femme.findFemmesMarieesAuMoinsDeuxFois", Femme.class);
        return query.list();
    }

    // Méthode utilitaire pour afficher les femmes mariées plusieurs fois
    @Transactional(readOnly = true)
    public void afficherFemmesMarieesPlusieursFois() {
        List<Femme> femmes = getFemmesMarieesAuMoinsDeuxFois();
        System.out.println("Femmes mariées au moins deux fois :");
        for (Femme femme : femmes) {
            System.out.printf("- %s (%d mariages)%n", femme, femme.getMariages().size());
        }
    }

    // Méthode alternative pour compter les enfants avec HQL
    @Transactional(readOnly = true)
    public long getNombreEnfantsBetweenDatesHQL(int femmeId, LocalDate dateDebut, LocalDate dateFin) {
        Query<Long> query = getCurrentSession().createQuery(
                "SELECT SUM(m.nombreEnfants) FROM Mariage m " +
                        "WHERE m.femme.id = :femmeId " +
                        "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Long.class);
        query.setParameter("femmeId", femmeId);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);

        Long result = query.uniqueResult();
        return result != null ? result : 0;
    }
}