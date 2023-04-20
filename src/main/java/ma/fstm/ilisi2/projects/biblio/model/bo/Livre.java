/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi2.projects.biblio.model.bo;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author EliteBook 840 G5
 */
@Entity
@Table(name="LIVRE"
        ,schema="HR"
)
public class Livre implements Serializable {
    private String isbn;
    private String titre;
    private String auteur;
    private Integer nbrexemp;
    private Set<Exemplaire> exemplaires = new HashSet<>(0);

    public Livre() {
    }

    public Livre(String isbn, String titre, String auteur, Integer nbrexemp) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.nbrexemp = nbrexemp;
    }
    public Livre(String isbn, String titre, String auteur, Integer nbrexemp, Set<Exemplaire> exemplaires) {
        this.isbn = isbn;
        this.titre = titre;
        this.nbrexemp = nbrexemp;
        this.auteur = auteur;
        this.exemplaires = exemplaires;
    }

    @Id
    @Column(name="ISBN", unique=true, nullable=false, length=20)
    public String getIsbn() {
        return isbn;
    }

    @Column(name="TITRE", nullable=false, length=20)
    public String getTitre() {
        return titre;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Column(name="AUTEUR", length=20)
    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @Column(name="NBREXEMP", precision=22, scale=0)
    public Integer getNbrexemp() {
        return nbrexemp;
    }

    public void setNbrexemp(Integer nbrexemp) {
        this.nbrexemp = nbrexemp;
    }

    @Override
    public String toString() {
        return "Livre{" + "isbn=" + isbn + ", Titre=" + titre + '}';
    }

    @OneToMany(fetch=FetchType.EAGER, mappedBy="livre")
    public Set<Exemplaire> getExemplaires() {
        return this.exemplaires;
    }

    public void setExemplaires(Set exemplaires) {
        this.exemplaires = exemplaires;
    }

}