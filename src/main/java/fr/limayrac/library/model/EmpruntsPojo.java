package fr.limayrac.library.model;

import lombok.Data;

import java.util.Date;


@Data
public class EmpruntsPojo {

    private String dateEmprunt;

    private Boolean rendu;

    private int nbJours;

    private Long IdLivre;

    private Long IdUser;

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Boolean getRendu() {
        return rendu;
    }

    public void setRendu(Boolean rendu) {
        this.rendu = rendu;
    }

    public int getnbJours() {
        return nbJours;
    }

    public void setnbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    public Long getIdLivre() {
        return IdLivre;
    }

    public void setIdLivre(Long idLivre) {
        IdLivre = idLivre;
    }

    public Long getIdUser() {
        return IdUser;
    }

    public void setIdUser(Long idUser) {
        IdUser = idUser;
    }
}
