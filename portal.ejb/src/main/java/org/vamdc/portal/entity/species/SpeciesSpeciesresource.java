package org.vamdc.portal.entity.species;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "species_speciesresource", catalog = "vamdc_species")
public class SpeciesSpeciesresource implements java.io.Serializable {

	private static final long serialVersionUID = 8608228042119590247L;
	private Integer id;
	private String url;
	private int speciesId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "url", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "species_id", nullable = false)
	public int getSpeciesId() {
		return this.speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

}
