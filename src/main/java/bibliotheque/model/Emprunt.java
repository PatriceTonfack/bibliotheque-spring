package bibliotheque.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "emprunts" )
public class Emprunt {
	@Id
	@GeneratedValue( strategy = jakarta.persistence.GenerationType.IDENTITY )
	private Long id;

	@ManyToOne( fetch = jakarta.persistence.FetchType.LAZY )
	@JoinColumn( name = "livre_id", nullable = false )
	private Livre livre;

	@ManyToOne( fetch = jakarta.persistence.FetchType.LAZY )
	@JoinColumn( name = "utilisateur_id", nullable = false )
	private Utilisateur utilisateur;

	private java.time.LocalDate	dateEmprunt;
	private java.time.LocalDate	dateRetourPrevue;
	private java.time.LocalDate	dateRetourEffective;

	public Emprunt() {
	}

	public Emprunt( Livre livre, Utilisateur utilisateur, LocalDate dateEmprunt, LocalDate dateRetourPrevue ) {
		this.livre = livre;
		this.utilisateur = utilisateur;
		this.dateEmprunt = dateEmprunt;
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre( Livre livre ) {
		this.livre = livre;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur( Utilisateur utilisateur ) {
		this.utilisateur = utilisateur;
	}

	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt( LocalDate dateEmprunt ) {
		this.dateEmprunt = dateEmprunt;
	}

	public LocalDate getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public void setDateRetourPrevue( LocalDate dateRetourPrevue ) {
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public LocalDate getDateRetourEffective() {
		return dateRetourEffective;
	}

	public void setDateRetourEffective( LocalDate dateRetourEffective ) {
		this.dateRetourEffective = dateRetourEffective;
	}

}
