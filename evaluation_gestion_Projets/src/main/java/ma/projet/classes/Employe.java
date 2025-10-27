package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    @OneToMany(mappedBy = "employe")
    private List<Projet> projetsGeres; // Projets dont il est chef

    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> tachesRealisees;

    public Employe() {}

    public Employe(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Projet> getProjetsGeres() { return projetsGeres; }
    public void setProjetsGeres(List<Projet> projetsGeres) { this.projetsGeres = projetsGeres; }

    public List<EmployeTache> getTachesRealisees() { return tachesRealisees; }
    public void setTachesRealisees(List<EmployeTache> tachesRealisees) { this.tachesRealisees = tachesRealisees; }

    @Override
    public String toString() {
        return "Employe{id=" + id + ", nom='" + nom + "', prenom='" + prenom +
                "', email='" + email + "', telephone='" + telephone + "'}";
    }
}