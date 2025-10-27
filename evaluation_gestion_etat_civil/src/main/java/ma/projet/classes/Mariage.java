package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mariage")
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "nombre_enfants")
    private int nombreEnfants;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {}

    public Mariage(LocalDate dateDebut, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.homme = homme;
        this.femme = femme;
        this.nombreEnfants = 0;
    }

    public Mariage(LocalDate dateDebut, LocalDate dateFin, int nombreEnfants, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreEnfants = nombreEnfants;
        this.homme = homme;
        this.femme = femme;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public int getNombreEnfants() { return nombreEnfants; }
    public void setNombreEnfants(int nombreEnfants) { this.nombreEnfants = nombreEnfants; }

    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }

    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }

    // Méthode utilitaire pour vérifier si le mariage est en cours
    public boolean estEnCours() {
        return dateFin == null;
    }

    @Override
    public String toString() {
        return "Mariage{" + homme + " - " + femme + " depuis " + dateDebut +
                (dateFin != null ? " jusqu'à " + dateFin : "") + ", enfants: " + nombreEnfants + "}";
    }
}