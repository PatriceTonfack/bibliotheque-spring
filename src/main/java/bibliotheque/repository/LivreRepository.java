package bibliotheque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bibliotheque.model.Emprunt;
import bibliotheque.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

	// --- Query method : Spring Data genere le SQL a partir du NOM de la methode
	// ---
	// "findByDisponibleTrue" -> SELECT * FROM livres WHERE disponible = true
	List<Livre> findByDisponibleTrue();

	// "findByAuteurContainingIgnoreCase" -> SELECT * FROM livres WHERE
	// LOWER(auteur) LIKE LOWER('%...%')
	List<Livre> findByAuteurContainingIgnoreCase( String auteur );

	Optional<Livre> findByIsbn( String isbn );

	// --- Requete JPQL explicite (quand le nom de methode devient trop complexe)
	// ---
	// JPQL ressemble a du SQL mais manipule des ENTITES JAVA, pas des tables
	// directement
	@Query( "SELECT l FROM Livre l WHERE l.titre LIKE %:motCle%" )
	List<Livre> rechercherParTitre( @Param( "motCle" ) String motCle );
}
