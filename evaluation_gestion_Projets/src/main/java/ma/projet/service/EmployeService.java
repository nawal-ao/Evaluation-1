package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
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
public class EmployeService implements IDao<Employe> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Employe o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Employe o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Employe o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Employe findById(int id) {
        return getCurrentSession().get(Employe.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        return getCurrentSession()
                .createQuery("from Employe", Employe.class)
                .list();
    }

    // Afficher la liste des tâches réalisées par un employé
    @Transactional(readOnly = true)
    public List<EmployeTache> getTachesRealiseesByEmploye(int employeId) {
        Query<EmployeTache> query = getCurrentSession().createQuery(
                "from EmployeTache et where et.employe.id = :employeId", EmployeTache.class);
        query.setParameter("employeId", employeId);
        return query.list();
    }

    // Afficher la liste des projets gérés par un employé
    @Transactional(readOnly = true)
    public List<Projet> getProjetsGeresByEmploye(int employeId) {
        Query<Projet> query = getCurrentSession().createQuery(
                "from Projet p where p.employe.id = :employeId", Projet.class);
        query.setParameter("employeId", employeId);
        return query.list();
    }

    // Afficher les détails des tâches réalisées par un employé
    @Transactional(readOnly = true)
    public void afficherTachesRealiseesParEmploye(int employeId) {
        Employe employe = findById(employeId);
        if (employe == null) {
            System.out.println("Aucun employé trouvé avec l'ID: " + employeId);
            return;
        }

        List<EmployeTache> tachesRealisees = getTachesRealiseesByEmploye(employeId);

        System.out.printf("Tâches réalisées par %s %s:%n", employe.getPrenom(), employe.getNom());
        System.out.println("Tâche\t\tDate Début\tDate Fin");
        System.out.println("-----\t\t----------\t--------");

        for (EmployeTache et : tachesRealisees) {
            Tache tache = et.getTache();
            System.out.printf("%-12s\t%td/%tm/%tY\t%td/%tm/%tY%n",
                    tache.getNom(),
                    et.getDateDebut(), et.getDateDebut(), et.getDateDebut(),
                    et.getDateFin(), et.getDateFin(), et.getDateFin());
        }
    }
}