package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tache")
@NamedQueries({
        @NamedQuery(
                name = "Tache.findByPrixSuperieur",
                query = "FROM Tache t WHERE t.prix > :prixMin"
        ),
        @NamedQuery(
                name = "Tache.findByDatesRealisation",
                query = "FROM Tache t WHERE t.dateDebutReelle BETWEEN :dateDebut AND :dateFin " +
                        "AND t.dateFinReelle BETWEEN :dateDebut AND :dateFin"
        )
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String description;
    private double prix;

    @Column(name = "date_debut_planifie")
    private LocalDate dateDebutPlanifie;

    @Column(name = "date_fin_planifie")
    private LocalDate dateFinPlanifie;

    @Column(name = "date_debut_reelle")
    private LocalDate dateDebutReelle;

    @Column(name = "date_fin_reelle")
    private LocalDate dateFinReelle;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    public Tache() {}

    public Tache(String nom, String description, double prix, LocalDate dateDebutPlanifie,
                 LocalDate dateFinPlanifie, Projet projet) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.dateDebutPlanifie = dateDebutPlanifie;
        this.dateFinPlanifie = dateFinPlanifie;
        this.projet = projet;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public LocalDate getDateDebutPlanifie() { return dateDebutPlanifie; }
    public void setDateDebutPlanifie(LocalDate dateDebutPlanifie) { this.dateDebutPlanifie = dateDebutPlanifie; }

    public LocalDate getDateFinPlanifie() { return dateFinPlanifie; }
    public void setDateFinPlanifie(LocalDate dateFinPlanifie) { this.dateFinPlanifie = dateFinPlanifie; }

    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }

    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(LocalDate dateFinReelle) { this.dateFinReelle = dateFinReelle; }

    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }

    @Override
    public String toString() {
        return "Tache{id=" + id + ", nom='" + nom + "', prix=" + prix +
                ", debutPlanifie=" + dateDebutPlanifie + ", finPlanifie=" + dateFinPlanifie + "}";
    }
}