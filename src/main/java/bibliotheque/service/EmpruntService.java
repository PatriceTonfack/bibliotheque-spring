package bibliotheque.service;

import org.springframework.stereotype.Service;

import bibliotheque.model.Emprunt;
import bibliotheque.model.Livre;
import bibliotheque.model.Utilisateur;
import bibliotheque.repository.EmpruntRepository;
import bibliotheque.repository.LivreRepository;
import bibliotheque.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class EmpruntService {
	private final EmpruntRepository		empruntRepository;
	private final LivreRepository		livreRepository;
	private final UtilisateurRepository	utilisateurRepository;

	public EmpruntService( EmpruntRepository empruntRepository, LivreRepository livreRepository,
			UtilisateurRepository utilisateurRepository ) {
		this.empruntRepository = empruntRepository;
		this.livreRepository = livreRepository;
		this.utilisateurRepository = utilisateurRepository;
	}

	@Transactional
	public Emprunt emprunterLivre( Long livreId, Long utilisateurId ) {
		// Logique pour emprunter un livre
		Livre livre = livreRepository.findById( livreId )
				.orElseThrow( () -> new RuntimeException( "Livre non trouvé" ) );
		if ( !livre.isDisponible() ) {
			throw new RuntimeException( "Livre non disponible" );
		}

		Utilisateur utilisateur = utilisateurRepository.findById( utilisateurId )
				.orElseThrow( () -> new RuntimeException( "Utilisateur non trouvé" ) );
		if ( empruntRepository.findEmpruntsEnCoursParUtilisateur( utilisateurId ).size() >= 5 ) {
			throw new RuntimeException( "L'utilisateur a déjà 5 emprunts en cours" );
		}

		livre.setDisponible( false );
		livreRepository.save( livre );

		// Créer un nouvel emprunt
		Emprunt emprunt = new Emprunt( livre, utilisateur, java.time.LocalDate.now(),
				java.time.LocalDate.now().plusDays( 14 ) );
		return empruntRepository.save( emprunt );
	}

	@Transactional
	public Emprunt retournerLivre( Long empruntId ) {
		// Logique pour retourner un livre
		Emprunt emprunt = empruntRepository.findById( empruntId )
				.orElseThrow( () -> new RuntimeException( "Emprunt non trouvé" ) );
		if ( emprunt.getDateRetourEffective() != null ) {
			throw new RuntimeException( "Livre déjà retourné" );
		}

		emprunt.setDateRetourEffective( java.time.LocalDate.now() );
		empruntRepository.save( emprunt );

		Livre livre = emprunt.getLivre();
		livre.setDisponible( true );
		livreRepository.save( livre );

		return emprunt;
	}

	public List<Emprunt> listerEmpruntsEnCoursParUtilisateur( Long utilisateurId ) {
		return empruntRepository.findEmpruntsEnCoursParUtilisateur( utilisateurId );
	}

	public List<Emprunt> listerHistoriqueParLivre( Long livreId ) {
		return empruntRepository.findHistoriqueParLivre( livreId );
	}

	public List<Object[]> statistiquesParUtilisateur() {
		return empruntRepository.compterEmpruntsParUtilisateur();
	}

}
