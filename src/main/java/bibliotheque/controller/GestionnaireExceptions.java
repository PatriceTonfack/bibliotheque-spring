package bibliotheque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bibliotheque.exception.RessourceNonTrouveeException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice : intercepte les exceptions levees par N'IMPORTE QUEL controller de l'appli.
// Evite de repeter des try/catch dans chaque methode de controller -> code plus propre (DRY).
@RestControllerAdvice
public class GestionnaireExceptions {

	@ExceptionHandler( RessourceNonTrouveeException.class )
	public ResponseEntity<Map<String, Object>> gererRessourceNonTrouvee( RessourceNonTrouveeException ex ) {
		return construireReponse( HttpStatus.NOT_FOUND, ex.getMessage() );
	}

	@ExceptionHandler( IllegalStateException.class )
	public ResponseEntity<Map<String, Object>> gererEtatInvalide( IllegalStateException ex ) {
		return construireReponse( HttpStatus.CONFLICT, ex.getMessage() ); // 409 CONFLICT
	}

	private ResponseEntity<Map<String, Object>> construireReponse( HttpStatus status, String message ) {
		Map<String, Object> corps = new HashMap<>();
		corps.put( "timestamp", LocalDateTime.now() );
		corps.put( "status", status.value() );
		corps.put( "erreur", status.getReasonPhrase() );
		corps.put( "message", message );
		return ResponseEntity.status( status ).body( corps );
	}
}
