package bibliotheque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table( name = "livres" )
public class Livre {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@NotBlank( message = "Le titre est obligatoire" )
	@Column( nullable = false )
	private String titre;

	@NotBlank( message = "L'auteur est obligatoire" )
	private String auteur;

	@Column( unique = true )
	private String isbn;

	private boolean disponible = true;

	// --- Constructeurs ---
	public Livre() {
	}

	public Livre( String titre, String auteur, String isbn ) {
		this.titre = titre;
		this.auteur = auteur;
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre( String titre ) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur( String auteur ) {
		this.auteur = auteur;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn( String isbn ) {
		this.isbn = isbn;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible( boolean disponible ) {
		this.disponible = disponible;
	}

	public String toString() {
		return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", isbn=" + isbn + ", disponible="
				+ disponible + "]";
	}

	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Livre other = (Livre) obj;
		if ( id == null ) {
			if ( other.id != null )
				return false;
		} else if ( !id.equals( other.id ) )
			return false;
		return true;
	}

	public int hashCode() {
		final int	prime	= 31;
		int			result	= 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	public String toJson() {
		return "{" +
				"\"id\":" + id + "," +
				"\"titre\":\"" + titre + "\"," +
				"\"auteur\":\"" + auteur + "\"," +
				"\"isbn\":\"" + isbn + "\"," +
				"\"disponible\":" + disponible +
				"}";
	}

	public static Livre fromJson( String json ) {
		String[]	parts		= json.replace( "{", "" ).replace( "}", "" ).split( "," );
		String		titre		= null;
		String		auteur		= null;
		String		isbn		= null;
		boolean		disponible	= true;

		for ( String part : parts ) {
			String[]	keyValue	= part.split( ":" );
			String		key			= keyValue[0].trim().replace( "\"", "" );
			String		value		= keyValue[1].trim().replace( "\"", "" );

			switch ( key ) {
			case "titre":
				titre = value;
				break;
			case "auteur":
				auteur = value;
				break;
			case "isbn":
				isbn = value;
				break;
			case "disponible":
				disponible = Boolean.parseBoolean( value );
				break;
			}
		}

		Livre livre = new Livre( titre, auteur, isbn );
		livre.setDisponible( disponible );
		return livre;
	}

}
