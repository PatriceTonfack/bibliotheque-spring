package bibliotheque.service;

import bibliotheque.exception.RessourceNonTrouveeException;
import bibliotheque.model.Utilisateur;
import bibliotheque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

	private final UtilisateurRepository utilisateurRepository;

	public UtilisateurService( UtilisateurRepository utilisateurRepository ) {
		this.utilisateurRepository = utilisateurRepository;
	}

	public List<Utilisateur> listerTous() {
		return utilisateurRepository.findAll();
	}

	public Utilisateur trouverParId( Long id ) {
		return utilisateurRepository.findById( id )
				.orElseThrow( () -> new RessourceNonTrouveeException( "Utilisateur introuvable avec id : " + id ) );
	}

	public Utilisateur creer( Utilisateur utilisateur ) {
		return utilisateurRepository.save( utilisateur );
	}
}
