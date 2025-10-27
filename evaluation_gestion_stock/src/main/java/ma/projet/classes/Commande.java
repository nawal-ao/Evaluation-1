package ma.projet.classes;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;



@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommandeProduit> ligneCommandes;

    // Constructeurs
    public Commande() {}

    public Commande(LocalDate date) {
        this.date = date;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<LigneCommandeProduit> getLigneCommandes() { return ligneCommandes; }
    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}
