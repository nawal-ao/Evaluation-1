package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
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
public class ProduitService implements IDao<Produit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CommandeService commandeService; // ← Injection par classe

    @Autowired
    private LigneCommandeService ligneCommandeService; // ← Injection par classe

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean create(Produit o) {
        getCurrentSession().save(o);
        return true;
    }

    @Override
    public boolean update(Produit o) {
        getCurrentSession().update(o);
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        getCurrentSession().delete(o);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Produit findById(int id) {
        return getCurrentSession().get(Produit.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findAll() {
        return getCurrentSession()
                .createQuery("from Produit", Produit.class)
                .list();
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduitsByCategorie(Categorie categorie) {
        Query<Produit> query = getCurrentSession().createQuery(
                "from Produit p where p.categorie = :categorie", Produit.class);
        query.setParameter("categorie", categorie);
        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduitsCommandesBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        Query<Produit> query = getCurrentSession().createQuery(
                "select distinct lcp.produit from LigneCommandeProduit lcp " +
                        "where lcp.commande.date between :dateDebut and :dateFin", Produit.class);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduitsByCommande(int commandeId) {
        Query<Produit> query = getCurrentSession().createQuery(
                "select lcp.produit from LigneCommandeProduit lcp " +
                        "where lcp.commande.id = :commandeId", Produit.class);
        query.setParameter("commandeId", commandeId);
        return query.list();
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduitsPrixSuperieur(float prixMin) {
        Query<Produit> query = getCurrentSession().createQuery(
                "from Produit p where p.prix > :prixMin", Produit.class);
        query.setParameter("prixMin", prixMin);
        return query.list();
    }

    @Transactional(readOnly = true)
    public void afficherDetailsCommande(int commandeId) {
        Commande commande = commandeService.findById(commandeId);
        if (commande == null) {
            System.out.println("Aucune commande trouvée avec l'ID: " + commandeId);
            return;
        }

        List<Produit> produits = getProduitsByCommande(commandeId);
        List<LigneCommandeProduit> toutesLignes = ligneCommandeService.findAll();

        LocalDate dateCommande = commande.getDate();
        int idCommande = commande.getId();

        System.out.printf("Commande : %d\t Date : %td %tB %tY%n",
                idCommande, dateCommande, dateCommande, dateCommande);
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\tQuantité");
        System.out.println("---------\t----\t---------");

        for (Produit produit : produits) {
            int quantite = 0;
            for (LigneCommandeProduit ligne : toutesLignes) {
                if (ligne.getCommande().getId() == commandeId &&
                        ligne.getProduit().getId() == produit.getId()) {
                    quantite = ligne.getQuantite();
                    break;
                }
            }
            System.out.printf("%s\t\t%.0f DH\t%d%n",
                    produit.getReference(), produit.getPrix(), quantite);
        }
    }
}