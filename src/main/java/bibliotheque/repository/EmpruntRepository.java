package bibliotheque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bibliotheque.model.Emprunt;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

	@Query( "SELECT e FROM Emprunt e WHERE e.utilisateur.id = :utilisateurId AND e.dateRetourEffective IS NULL" )
	List<Emprunt> findEmpruntsEnCoursParUtilisateur( @Param( "utilisateurId" ) Long utilisateurId );

	// Historique complet des emprunts d'un livre donne (JOIN sur livre)
	@Query( "SELECT e FROM Emprunt e WHERE e.livre.id = :livreId ORDER BY e.dateEmprunt DESC" )
	List<Emprunt> findHistoriqueParLivre( @Param( "livreId" ) Long livreId );

	// Equivalent du JOIN + GROUP BY de ton document de revision :
	// compte le nombre d'emprunts par utilisateur
	@Query( "SELECT e.utilisateur.nom, COUNT(e) FROM Emprunt e GROUP BY e.utilisateur.nom" )
	List<Object[]> compterEmpruntsParUtilisateur();
	
}
