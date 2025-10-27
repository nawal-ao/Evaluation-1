package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    private double budget;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe; // Chef de projet

    @OneToMany(mappedBy = "projet")
    private List<Tache> taches;

    public Projet() {}

    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin, double budget, Employe employe) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
        this.employe = employe;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }

    @Override
    public String toString() {
        return "Projet{id=" + id + ", nom='" + nom + "', dateDebut=" + dateDebut +
                ", dateFin=" + dateFin + ", budget=" + budget + "}";
    }
}