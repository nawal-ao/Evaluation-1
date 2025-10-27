package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Homme;
import ma.projet.classes.Mariage;
import ma.projet.classes.Femme;
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
public class HommeService implements IDao<Homme> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Homme o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Homme o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Homme o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Homme findById(int id) {
        return getCurrentSession().get(Homme.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Homme> findAll() {
        return getCurrentSession()
                .createQuery("from Homme", Homme.class)
                .list();
    }

    // Afficher les épouses d'un homme entre deux dates
    @Transactional(readOnly = true)
    public List<Femme> getEpousesByHommeBetweenDates(int hommeId, LocalDate dateDebut, LocalDate dateFin) {
        Query<Femme> query = getCurrentSession().createQuery(
                "select m.femme from Mariage m where m.homme.id = :hommeId " +
                        "and m.dateDebut between :dateDebut and :dateFin", Femme.class);
        query.setParameter("hommeId", hommeId);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
    }

    // Afficher les mariages d'un homme avec les détails
    @Transactional(readOnly = true)
    public void afficherMariagesHomme(int hommeId) {
        Homme homme = findById(hommeId);
        if (homme == null) {
            System.out.println("Aucun homme trouvé avec l'ID: " + hommeId);
            return;
        }

        Query<Mariage> query = getCurrentSession().createQuery(
                "from Mariage m where m.homme.id = :hommeId order by m.dateDebut", Mariage.class);
        query.setParameter("hommeId", hommeId);
        List<Mariage> mariages = query.list();

        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

        // Mariages en cours
        System.out.println("Mariages En Cours :");
        int count = 1;
        for (Mariage m : mariages) {
            if (m.estEnCours()) {
                System.out.printf("%d. Femme : %s  Date Début : %td/%tm/%tY  Nbr Enfants : %d%n",
                        count++, m.getFemme(),
                        m.getDateDebut(), m.getDateDebut(), m.getDateDebut(),
                        m.getNombreEnfants());
            }
        }

        // Mariages échoués
        System.out.println("Mariages échoués :");
        count = 1;
        for (Mariage m : mariages) {
            if (!m.estEnCours()) {
                System.out.printf("%d. Femme : %s  Date Début : %td/%tm/%tY  " +
                                "Date Fin : %td/%tm/%tY  Nbr Enfants : %d%n",
                        count++, m.getFemme(),
                        m.getDateDebut(), m.getDateDebut(), m.getDateDebut(),
                        m.getDateFin(), m.getDateFin(), m.getDateFin(),
                        m.getNombreEnfants());
            }
        }
    }

    // Méthode avec API Criteria pour compter les hommes mariés à quatre femmes entre deux dates
    @Transactional(readOnly = true)
    public long countHommesMariesQuatreFemmesBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        Query<Long> query = getCurrentSession().createQuery(
                "select count(distinct m.homme.id) from Mariage m " +
                        "where m.dateDebut between :dateDebut and :dateFin " +
                        "group by m.homme.id having count(m.femme.id) >= 4", Long.class);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);

        Long result = query.uniqueResult();
        return result != null ? result : 0;
    }
}