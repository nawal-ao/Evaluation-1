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
        HommeService hommeService = context.getBean(HommeService.class);
        FemmeService femmeService = context.getBean(FemmeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        try {
            // Nettoyer les données existantes
            nettoyerDonnees(mariageService, hommeService, femmeService);

            // Création de 5 hommes
            Homme h1 = new Homme("SAFI", "SAID", LocalDate.of(1960, 5, 15));
            Homme h2 = new Homme("ALAMI", "MOHAMED", LocalDate.of(1965, 8, 20));
            Homme h3 = new Homme("BENNANI", "AHMED", LocalDate.of(1970, 3, 10));
            Homme h4 = new Homme("CHRAIBI", "HASSAN", LocalDate.of(1975, 11, 5));
            Homme h5 = new Homme("DAOUD", "KARIM", LocalDate.of(1980, 7, 25));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            System.out.println("✓ 5 hommes créés");

            // Création de 10 femmes
            Femme f1 = new Femme("SALINA", "BAMI", LocalDate.of(1965, 2, 12));
            Femme f2 = new Femme("AMAL", "ALI", LocalDate.of(1970, 6, 18));
            Femme f3 = new Femme("MAHA", "ALLAOUI", LocalDate.of(1968, 9, 22));
            Femme f4 = new Femme("KARIMA", "ALAMI", LocalDate.of(1972, 4, 8));
            Femme f5 = new Femme("FATIMA", "BENNANI", LocalDate.of(1975, 12, 3));
            Femme f6 = new Femme("NADIA", "CHRAIBI", LocalDate.of(1978, 7, 15));
            Femme f7 = new Femme("SOUAD", "DAOUD", LocalDate.of(1980, 1, 28));
            Femme f8 = new Femme("LATIFA", "ELAMI", LocalDate.of(1982, 10, 11));
            Femme f9 = new Femme("HANAE", "FARES", LocalDate.of(1985, 3, 7));
            Femme f10 = new Femme("SANA", "GHARBI", LocalDate.of(1988, 8, 19));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            System.out.println("✓ 10 femmes créées");

            // Création des mariages
            // SAFI SAID a 4 mariages (3 en cours, 1 échoué)
            Mariage m1 = new Mariage(LocalDate.of(1990, 9, 8), h1, f1);
            m1.setNombreEnfants(4);

            Mariage m2 = new Mariage(LocalDate.of(1995, 9, 8), h1, f2);
            m2.setNombreEnfants(2);

            Mariage m3 = new Mariage(LocalDate.of(2000, 11, 4), h1, f3);
            m3.setNombreEnfants(3);

            Mariage m4 = new Mariage(LocalDate.of(1989, 9, 8), LocalDate.of(1990, 9, 3), 0, h1, f4);

            // ALAMI MOHAMED a 2 mariages
            Mariage m5 = new Mariage(LocalDate.of(1992, 5, 10), h2, f5);
            m5.setNombreEnfants(2);

            Mariage m6 = new Mariage(LocalDate.of(2005, 7, 15), h2, f6);
            m6.setNombreEnfants(1);

            // BENNANI AHMED a 4 mariages
            Mariage m7 = new Mariage(LocalDate.of(1995, 3, 20), h3, f7);
            m7.setNombreEnfants(3);

            Mariage m8 = new Mariage(LocalDate.of(2000, 8, 12), h3, f8);
            m8.setNombreEnfants(2);

            Mariage m9 = new Mariage(LocalDate.of(2005, 11, 30), h3, f9);
            m9.setNombreEnfants(1);

            Mariage m10 = new Mariage(LocalDate.of(2010, 6, 18), h3, f10);
            m10.setNombreEnfants(0);

            // CHRAIBI HASSAN a 1 mariage
            Mariage m11 = new Mariage(LocalDate.of(2008, 4, 22), h4, f1);
            m11.setNombreEnfants(2);

            // DAOUD KARIM a 1 mariage
            Mariage m12 = new Mariage(LocalDate.of(2012, 9, 14), h5, f2);
            m12.setNombreEnfants(1);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            mariageService.create(m12);
            System.out.println("✓ Mariages créés");

            // Tests des fonctionnalités
            System.out.println("\n=== TESTS DES FONCTIONNALITÉS ===");

            // 1. Afficher la liste des femmes
            System.out.println("\n--- Liste des femmes ---");
            List<Femme> femmes = femmeService.findAll();
            for (Femme femme : femmes) {
                System.out.printf("- %s (Née le: %s)%n", femme, femme.getDateNaissance());
            }

            // 2. Afficher la femme la plus âgée
            System.out.println("\n--- Femme la plus âgée ---");
            Femme femmePlusAgee = femmeService.findFemmeLaPlusAgee();
            if (femmePlusAgee != null) {
                System.out.printf("%s (Née le: %s)%n", femmePlusAgee, femmePlusAgee.getDateNaissance());
            }

            // 3. Afficher les épouses d'un homme donné
            System.out.println("\n--- Épouses de SAFI SAID entre 1990 et 2000 ---");
            List<Femme> epouses = hommeService.getEpousesByHommeBetweenDates(
                    h1.getId(), LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
            for (Femme epouse : epouses) {
                System.out.printf("- %s%n", epouse);
            }

            // 4. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("\n--- Nombre d'enfants de SALINA BAMI entre 1990-2000 ---");
            long nbEnfants = femmeService.getNombreEnfantsBetweenDates(
                    f1.getId(), LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
            System.out.printf("Nombre d'enfants: %d%n", nbEnfants);

            // 5. Afficher les femmes mariées deux fois ou plus
            System.out.println("\n--- Femmes mariées au moins deux fois ---");
            femmeService.afficherFemmesMarieesPlusieursFois();

            // 6. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n--- Hommes mariés à au moins 4 femmes entre 1990-2010 ---");
            long nbHommes = hommeService.countHommesMariesQuatreFemmesBetweenDates(
                    LocalDate.of(1990, 1, 1), LocalDate.of(2010, 12, 31));
            System.out.printf("Nombre d'hommes mariés à au moins 4 femmes: %d%n", nbHommes);

            // 7. Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n--- Détails des mariages de SAFI SAID ---");
            hommeService.afficherMariagesHomme(h1.getId());

            System.out.println("\n✅ Application de gestion de l'état civil exécutée avec succès !");

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }

    private static void nettoyerDonnees(MariageService ms, HommeService hs, FemmeService fs) {
        List<Mariage> mariages = ms.findAll();
        for (Mariage m : mariages) {
            ms.delete(m);
        }

        List<Homme> hommes = hs.findAll();
        for (Homme h : hommes) {
            hs.delete(h);
        }

        List<Femme> femmes = fs.findAll();
        for (Femme f : femmes) {
            fs.delete(f);
        }
    }
}