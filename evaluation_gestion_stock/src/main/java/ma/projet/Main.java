package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        // Récupérer les services
        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneService = context.getBean(LigneCommandeService.class);
        CategorieService categorieService = context.getBean(CategorieService.class);

        // Création des catégories
        Categorie cat1 = new Categorie();
        cat1.setCode("C1");
        cat1.setLibelle("Catégorie 1");

        Categorie cat2 = new Categorie();
        cat2.setCode("C2");
        cat2.setLibelle("Catégorie 2");

        // Sauvegarder les catégories d'abord
        categorieService.create(cat1);
        categorieService.create(cat2);

        // Création des produits
        Produit p1 = new Produit();
        p1.setReference("ES12");
        p1.setPrix(120f);
        p1.setCategorie(cat1);

        Produit p2 = new Produit();
        p2.setReference("ZR85");
        p2.setPrix(100f);
        p2.setCategorie(cat1);

        Produit p3 = new Produit();
        p3.setReference("EE85");
        p3.setPrix(200f);
        p3.setCategorie(cat2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        // Création des commandes
        Commande c1 = new Commande();
        c1.setDate(LocalDate.of(2013, 3, 14));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(LocalDate.of(2013, 5, 20));
        commandeService.create(c2);

        // Création des lignes de commande
        LigneCommandeProduit l1 = new LigneCommandeProduit();
        l1.setProduit(p1);
        l1.setCommande(c1);
        l1.setQuantite(7);

        LigneCommandeProduit l2 = new LigneCommandeProduit();
        l2.setProduit(p2);
        l2.setCommande(c1);
        l2.setQuantite(14);

        LigneCommandeProduit l3 = new LigneCommandeProduit();
        l3.setProduit(p3);
        l3.setCommande(c2);
        l3.setQuantite(5);

        ligneService.create(l1);
        ligneService.create(l2);
        ligneService.create(l3);

        // Affichage des résultats
        System.out.println("\n--- Produits par catégorie C1 ---");
        List<Produit> produitsCat1 = produitService.getProduitsByCategorie(cat1);
        for (Produit p : produitsCat1) {
            System.out.printf("Réf : %-5s Prix : %-6.2f Catégorie : %s%n",
                    p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
        }

        System.out.println("\n--- Produits par commande c1 ---");
        List<Produit> produitsCommande1 = produitService.getProduitsByCommande(c1.getId());
        for (Produit p : produitsCommande1) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n--- Produits commandés entre 2013-01-01 et 2013-12-31 ---");
        LocalDate start = LocalDate.of(2013, 1, 1);
        LocalDate end = LocalDate.of(2013, 12, 31);
        List<Produit> produitsDates = produitService.getProduitsCommandesBetweenDates(start, end);
        for (Produit p : produitsDates) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n--- Produits prix > 100 DH ---");
        List<Produit> produitsChers = produitService.getProduitsPrixSuperieur(100f);
        for (Produit p : produitsChers) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n--- Détails de la commande 1 ---");
        produitService.afficherDetailsCommande(c1.getId());

        context.close();
    }
}