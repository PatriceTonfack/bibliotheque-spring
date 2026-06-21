package bibliotheque.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibliotheque.model.Livre;
import bibliotheque.service.LivreService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping( "api/livres" )
public class LivreController {

	private final LivreService livreService;

	public LivreController( LivreService livreService ) {
		this.livreService = livreService;
	}

	@GetMapping
	public String lister( @RequestParam( required = false ) Boolean disponible,
			@RequestParam( required = false ) String auteur,
			@RequestParam( required = false ) String titre ) {

		if ( disponible.equals( "true" ) ) {
			return livreService.listerDisponibles().toString();
		} else if ( auteur != null ) {
			return livreService.rechercherParAuteur( auteur ).toString();
		} else if ( titre != null ) {
			return livreService.rechercherParTitre( titre ).toString();
		}

		return livreService.listerTous().toString();
	}

	@GetMapping( "/{id}" )
	public ResponseEntity trouverParId( @PathVariable Long idUtilisateur ) {
		return ResponseEntity.ok( livreService.trouverPArId( idUtilisateur ) );
	}

	@PostMapping( "/{id}" )
	public ResponseEntity<Livre> creer( @RequestBody Livre livre ) {
		Livre nouveauLivre = livreService.creer( livre );
		return ResponseEntity.status( HttpStatus.CREATED ).body( nouveauLivre );
	}

	// PUT /api/livres/5
	@PutMapping( "/{id}" )
	public ResponseEntity<Livre> mettreAJour( @PathVariable Long id, @Valid @RequestBody Livre livre ) {
		Livre maj = livreService.mettreAJour( id, livre );
		return ResponseEntity.ok( maj );
	}

	// DELETE /api/livres/5
	@DeleteMapping( "/{id}" )
	public ResponseEntity<Void> supprimer( @PathVariable Long id ) {
		livreService.supprimer( id );
		// Convention REST : une suppression reussie renvoie 204 NO CONTENT (pas de
		// corps de reponse)
		return ResponseEntity.noContent().build();
	}

}
