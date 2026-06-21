package bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table( name = "utilisateurs" )
public class Utilisateur {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@NotBlank( message = "Le nom est obligatoire" )
	private String nom;

	@Email( message = "Email invalide" )
	@Column( unique = true )
	private String email;

	public Utilisateur() {
	}

	public Utilisateur( String nom, String email ) {
		this.nom = nom;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom( String nom ) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String toString() {
		return "Utilisateur{" +
				"id=" + id +
				", nom='" + nom + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	public boolean equals( Object o ) {
		if ( this == o )
			return true;
		if ( !( o instanceof Utilisateur ) )
			return false;

		Utilisateur that = (Utilisateur) o;

		if ( !id.equals( that.id ) )
			return false;
		if ( !nom.equals( that.nom ) )
			return false;
		return email.equals( that.email );
	}

	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + nom.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}

	public boolean isValid() {
		return nom != null && !nom.isEmpty() && email != null && !email.isEmpty();
	}

	public boolean isEmailValid() {
		return email != null && email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$" );
	}

	public boolean isNomValid() {
		return nom != null && !nom.isEmpty();
	}

	public boolean isIdValid() {
		return id != null && id > 0;
	}

	public String toJson() {
		return "{" +
				"\"id\":" + id + "," +
				"\"nom\":\"" + nom + "\"," +
				"\"email\":\"" + email + "\"" +
				"}";
	}

	public String toJsonWithoutId() {
		return "{" +
				"\"nom\":\"" + nom + "\"," +
				"\"email\":\"" + email + "\"" +
				"}";
	}

	public String toJsonWithoutIdAndEmail() {
		return "{" +
				"\"nom\":\"" + nom + "\"" +
				"}";
	}

	public String toJsonWithoutIdAndNom() {
		return "{" +
				"\"email\":\"" + email + "\"" +
				"}";
	}

	public String toJsonWithoutNomAndEmail() {
		return "{" +
				"\"id\":" + id +
				"}";
	}

}
