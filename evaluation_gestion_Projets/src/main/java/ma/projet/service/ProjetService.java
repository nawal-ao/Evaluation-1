package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjetService implements IDao<Projet> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Projet o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Projet o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Projet o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Projet findById(int id) {
        return getCurrentSession().get(Projet.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Projet> findAll() {
        return getCurrentSession()
                .createQuery("from Projet", Projet.class)
                .list();
    }

    // Afficher la liste des tâches planifiées pour un projet
    @Transactional(readOnly = true)
    public List<Tache> getTachesPlanifieesByProjet(int projetId) {
        Query<Tache> query = getCurrentSession().createQuery(
                "from Tache t where t.projet.id = :projetId " +
                        "and t.dateDebutPlanifie is not null and t.dateFinPlanifie is not null", Tache.class);
        query.setParameter("projetId", projetId);
        return query.list();
    }

    // Afficher la liste des tâches réalisées avec les dates réelles
    @Transactional(readOnly = true)
    public List<Tache> getTachesRealiseesWithDatesReelles(int projetId) {
        Query<Tache> query = getCurrentSession().createQuery(
                "from Tache t where t.projet.id = :projetId " +
                        "and t.dateDebutReelle is not null and t.dateFinReelle is not null", Tache.class);
        query.setParameter("projetId", projetId);
        return query.list();
    }

    // Afficher les détails d'un projet avec ses tâches réalisées
    @Transactional(readOnly = true)
    public void afficherDetailsProjet(int projetId) {
        Projet projet = findById(projetId);
        if (projet == null) {
            System.out.println("Aucun projet trouvé avec l'ID: " + projetId);
            return;
        }

        List<Tache> tachesRealisees = getTachesRealiseesWithDatesReelles(projetId);

        System.out.printf("Projet : %d | Nom : %s | Date début : %td %tB %tY%n",
                projet.getId(), projet.getNom(),
                projet.getDateDebut(), projet.getDateDebut(), projet.getDateDebut());

        System.out.println("Liste des tâches:");
        System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");
        System.out.println("---\t---\t\t-----------------\t---------------");

        for (Tache tache : tachesRealisees) {
            System.out.printf("%d\t%-12s\t%td/%tm/%tY\t\t%td/%tm/%tY%n",
                    tache.getId(), tache.getNom(),
                    tache.getDateDebutReelle(), tache.getDateDebutReelle(), tache.getDateDebutReelle(),
                    tache.getDateFinReelle(), tache.getDateFinReelle(), tache.getDateFinReelle());
        }
    }
}