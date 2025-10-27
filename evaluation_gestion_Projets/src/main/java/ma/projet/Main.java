package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        // Récupérer les services
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);
        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);

        try {
            // Nettoyer les données existantes
            nettoyerDonnees(employeTacheService, tacheService, projetService, employeService);

            // Création des employés
            Employe emp1 = new Employe("Dupont", "Jean", "jean.dupont@email.com", "0612345678");
            Employe emp2 = new Employe("Martin", "Marie", "marie.martin@email.com", "0623456789");

            employeService.create(emp1);
            employeService.create(emp2);
            System.out.println("✓ Employés créés");

            // Création des projets
            Projet p1 = new Projet("Gestion de stock",
                    LocalDate.of(2013, 1, 14),
                    LocalDate.of(2013, 12, 31),
                    50000.0, emp1);

            Projet p2 = new Projet("Site Web E-commerce",
                    LocalDate.of(2013, 2, 1),
                    LocalDate.of(2013, 11, 30),
                    75000.0, emp2);

            projetService.create(p1);
            projetService.create(p2);
            System.out.println("✓ Projets créés");

            // Création des tâches
            Tache t1 = new Tache("Analyse", "Analyse des besoins", 1500.0,
                    LocalDate.of(2013, 1, 15), LocalDate.of(2013, 2, 15), p1);
            t1.setDateDebutReelle(LocalDate.of(2013, 2, 10));
            t1.setDateFinReelle(LocalDate.of(2013, 2, 20));

            Tache t2 = new Tache("Conception", "Conception technique", 2000.0,
                    LocalDate.of(2013, 2, 16), LocalDate.of(2013, 3, 15), p1);
            t2.setDateDebutReelle(LocalDate.of(2013, 3, 10));
            t2.setDateFinReelle(LocalDate.of(2013, 3, 15));

            Tache t3 = new Tache("Développement", "Développement application", 5000.0,
                    LocalDate.of(2013, 3, 16), LocalDate.of(2013, 5, 15), p1);
            t3.setDateDebutReelle(LocalDate.of(2013, 4, 10));
            t3.setDateFinReelle(LocalDate.of(2013, 4, 25));

            Tache t4 = new Tache("Design", "Design interface", 800.0,
                    LocalDate.of(2013, 2, 1), LocalDate.of(2013, 2, 28), p2);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);
            System.out.println("✓ Tâches créées");

            // Création des affectations employé-tâche
            EmployeTache et1 = new EmployeTache(
                    LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20), emp1, t1);
            EmployeTache et2 = new EmployeTache(
                    LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15), emp1, t2);
            EmployeTache et3 = new EmployeTache(
                    LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25), emp2, t3);

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            System.out.println("✓ Affectations employé-tâche créées");

            // Tests des fonctionnalités
            System.out.println("\n=== TESTS DES FONCTIONNALITÉS ===");

            // 1. Détails d'un projet avec tâches réalisées
            System.out.println("\n--- Détails du projet 'Gestion de stock' ---");
            projetService.afficherDetailsProjet(p1.getId());

            // 2. Tâches réalisées par un employé
            System.out.println("\n--- Tâches réalisées par Jean Dupont ---");
            employeService.afficherTachesRealiseesParEmploye(emp1.getId());

            // 3. Projets gérés par un employé
            System.out.println("\n--- Projets gérés par Marie Martin ---");
            List<Projet> projetsMarie = employeService.getProjetsGeresByEmploye(emp2.getId());
            for (Projet p : projetsMarie) {
                System.out.printf("- %s (Budget: %.2f DH)%n", p.getNom(), p.getBudget());
            }

            // 4. Tâches avec prix > 1000 DH
            System.out.println("\n--- Tâches avec prix > 1000 DH ---");
            List<Tache> tachesCheres = tacheService.getTachesPrixSuperieur1000();
            for (Tache t : tachesCheres) {
                System.out.printf("- %s: %.2f DH (Projet: %s)%n",
                        t.getNom(), t.getPrix(), t.getProjet().getNom());
            }

            // 5. Tâches réalisées entre deux dates
            System.out.println("\n--- Tâches réalisées entre 2013-03-01 et 2013-04-30 ---");
            List<Tache> tachesDates = tacheService.getTachesRealiseesBetweenDates(
                    LocalDate.of(2013, 3, 1), LocalDate.of(2013, 4, 30));
            for (Tache t : tachesDates) {
                System.out.printf("- %s: %s à %s%n",
                        t.getNom(), t.getDateDebutReelle(), t.getDateFinReelle());
            }

            System.out.println("\n Application de gestion de projets exécutée avec succès !");

        } catch (Exception e) {
            System.err.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }

    private static void nettoyerDonnees(EmployeTacheService ets, TacheService ts,
                                        ProjetService ps, EmployeService es) {
        List<EmployeTache> employeTaches = ets.findAll();
        for (EmployeTache et : employeTaches) {
            ets.delete(et);
        }

        List<Tache> taches = ts.findAll();
        for (Tache t : taches) {
            ts.delete(t);
        }

        List<Projet> projets = ps.findAll();
        for (Projet p : projets) {
            ps.delete(p);
        }

        List<Employe> employes = es.findAll();
        for (Employe e : employes) {
            es.delete(e);
        }
    }
}