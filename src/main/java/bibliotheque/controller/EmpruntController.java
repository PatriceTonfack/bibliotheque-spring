package bibliotheque.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import bibliotheque.model.Emprunt;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;
import bibliotheque.repository.EmpruntRepository;
import bibliotheque.repository.LivreRepository;
import bibliotheque.repository.UtilisateurRepository;
import bibliotheque.exception.RessourceNonTrouveeException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import bibliotheque.service.EmpruntService;

@RestController
@RequestMapping( "/api/emprunts" )
public class EmpruntController {

	private final EmpruntService empruntService;

	public EmpruntController( EmpruntService empruntService ) {
		this.empruntService = empruntService;
	}

	// POST /api/emprunts/emprunter
	// Body JSON attendu : { "livreId": 1, "utilisateurId": 2 }
	// On utilise une action explicite ("emprunter") plutot qu'un simple POST
	// /api/emprunts
	// car c'est une operation metier avec des effets de bord (modifie aussi
	// l'entite Livre).
	@PostMapping( "/emprunter" )
	public ResponseEntity<Emprunt> emprunter( @RequestBody Map<String, Long> requete ) {
		Long	livreId			= requete.get( "livreId" );
		Long	utilisateurId	= requete.get( "utilisateurId" );
		Emprunt	emprunt			= empruntService.emprunterLivre( livreId, utilisateurId );
		return ResponseEntity.status( HttpStatus.CREATED ).body( emprunt );
	}

	// PUT /api/emprunts/5/rendre
	@PutMapping( "/{id}/rendre" )
	public ResponseEntity<Emprunt> rendre( @PathVariable Long id ) {
		Emprunt emprunt = empruntService.retournerLivre( id );
		return ResponseEntity.ok( emprunt );
	}

	// GET /api/emprunts/utilisateur/2/en-cours
	@GetMapping( "/utilisateur/{utilisateurId}/en-cours" )
	public List<Emprunt> empruntsEnCours( @PathVariable Long utilisateurId ) {
		return empruntService.listerEmpruntsEnCoursParUtilisateur( utilisateurId );
	}

	// GET /api/emprunts/livre/1/historique
	@GetMapping( "/livre/{livreId}/historique" )
	public List<Emprunt> historique( @PathVariable Long livreId ) {
		return empruntService.listerHistoriqueParLivre( livreId );
	}

	// GET /api/emprunts/statistiques
	@GetMapping( "/statistiques" )
	public List<Object[]> statistiques() {
		return empruntService.statistiquesParUtilisateur();
	}
}
