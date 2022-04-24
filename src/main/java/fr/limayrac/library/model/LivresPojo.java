package fr.limayrac.library.model;

import lombok.Data;

@Data
public class LivresPojo {

    private String titre;

    private String auteur;

    private String description;

    private String nbPages;

    private String etat;

    private String dateParution;

    private Long nbLivres;

    public LivresPojo( String titre, String auteur, String description, String nbPages, String etat, String dateParution, Long nbLivres ){
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.nbPages = nbPages;
        this.etat = etat;
        this.dateParution = dateParution;
        this.nbLivres = nbLivres;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNbPages() {
        return nbPages;
    }

    public void setNbPages(String nbPages) {
        this.nbPages = nbPages;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDateParution() {
        return dateParution;
    }

    public void setDateParution(String dateParution) {
        this.dateParution = dateParution;
    }

    public Long getNbLivres() {
        return nbLivres;
    }

    public void setNbLivres(Long nbLivres) {
        this.nbLivres = nbLivres;
    }
}
