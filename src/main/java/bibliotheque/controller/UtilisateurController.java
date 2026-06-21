package bibliotheque.controller;

import bibliotheque.model.Utilisateur;
import bibliotheque.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/utilisateurs" )
public class UtilisateurController {

	private final UtilisateurService utilisateurService;

	public UtilisateurController( UtilisateurService utilisateurService ) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping
	public List<Utilisateur> lister() {
		return utilisateurService.listerTous();
	}

	@GetMapping( "/{id}" )
	public ResponseEntity<Utilisateur> trouverParId( @PathVariable Long id ) {
		return ResponseEntity.ok( utilisateurService.trouverParId( id ) );
	}

	@PostMapping
	public ResponseEntity<Utilisateur> creer( @Valid @RequestBody Utilisateur utilisateur ) {
		Utilisateur cree = utilisateurService.creer( utilisateur );
		return ResponseEntity.status( HttpStatus.CREATED ).body( cree );
	}
}
