package bibliotheque.service;

import java.util.List;
import bibliotheque.model.Livre;

import org.springframework.stereotype.Service;
import bibliotheque.repository.LivreRepository;

@Service
public class LivreService {

	private final LivreRepository livreRepository;

	public LivreService( LivreRepository livreRepository ) {
		this.livreRepository = livreRepository;
	}

	public List<Livre> listerTous() {
		return livreRepository.findAll();
	}

	public Livre trouverPArId( Long id ) {
		return livreRepository.findById( id ).orElse( null );
	}

	public List<Livre> listerDisponibles() {
		return livreRepository.findByDisponibleTrue();
	}

	public List<Livre> rechercherParAuteur( String auteur ) {
		return livreRepository.findByAuteurContainingIgnoreCase( auteur );
	}

	public List<Livre> rechercherParTitre( String motCle ) {
		return livreRepository.rechercherParTitre( motCle );
	}

	public Livre creer( Livre livre ) {
		livre.setDisponible( true );
		return livreRepository.save( livre );
	}

	public Livre mettreAJour( Long id, Livre livreMisAJour ) {
		Livre livreExistant = livreRepository.findById( id ).orElse( null );
		if ( livreExistant != null ) {
			livreExistant.setTitre( livreMisAJour.getTitre() );
			livreExistant.setAuteur( livreMisAJour.getAuteur() );
			livreExistant.setIsbn( livreMisAJour.getIsbn() );
			livreExistant.setDisponible( livreMisAJour.isDisponible() );
			return livreRepository.save( livreExistant );
		}
		return null;
	}

	public void supprimer( Long id ) {
		livreRepository.deleteById( id );
	}
}
