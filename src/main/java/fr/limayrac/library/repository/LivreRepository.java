package fr.limayrac.library.repository;

import fr.limayrac.library.model.Livres;
import fr.limayrac.library.model.LivresPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livres, Long> {

    @Query("SELECT new fr.limayrac.library.model.LivresPojo(titre, auteur , description, nbPages,  etat, dateParution, count(titre)) FROM Livres WHERE id NOT IN (SELECT e.livre  FROM Emprunts e) OR id NOT IN (SELECT e.livre.id from Emprunts e where e.rendu=false)  GROUP BY titre, etat")
    public List<LivresPojo> findAllByEtatAndTitle();

    @Query("SELECT l FROM Livres l WHERE l.id IN (SELECT e.livre.id from Emprunts e where e.rendu=false and e.user.id= :idUser)")
    public List<Livres> findLivreEmpByEtatAndTitle(Long idUser);

    @Query("SELECT l FROM Livres l WHERE l.etat= :etat AND l.titre= :titre AND (l.id NOT IN (SELECT e.livre  FROM Emprunts e) OR l.id NOT IN (SELECT e.livre.id from Emprunts e where e.rendu=false)) ")
    public List<Livres> findByEtatAndTitle(String titre, String etat);
}
