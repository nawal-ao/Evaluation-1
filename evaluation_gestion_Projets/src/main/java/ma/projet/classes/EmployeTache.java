package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;

    public EmployeTache() {}

    public EmployeTache(LocalDate dateDebut, LocalDate dateFin, Employe employe, Tache tache) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.employe = employe;
        this.tache = tache;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }

    @Override
    public String toString() {
        return "EmployeTache{id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin +
                ", employe=" + (employe != null ? employe.getNom() : "null") +
                ", tache=" + (tache != null ? tache.getNom() : "null") + "}";
    }
}