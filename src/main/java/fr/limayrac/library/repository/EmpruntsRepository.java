package fr.limayrac.library.repository;

import fr.limayrac.library.model.Emprunts;
import fr.limayrac.library.model.Livres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpruntsRepository extends JpaRepository<Emprunts, Long> {

    @Query("SELECT e FROM Emprunts e WHERE e.user.id= :idUser AND e.livre.id= :idLivre AND e.rendu=0")
    public Emprunts findIdUserAndIdLivre(Long idUser, Long idLivre);
}
