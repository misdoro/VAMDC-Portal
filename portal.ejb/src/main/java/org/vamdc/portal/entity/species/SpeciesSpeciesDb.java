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
@Table(name = "species_species_db")
public class SpeciesSpeciesDb implements java.io.Serializable {

	private static final long serialVersionUID = 2254640169612224802L;
	private Integer id;
	private String name;
	private int speciesIdI;
	private String speciesId;
	private int speciesId_1;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 40)
	@NotNull
	@Length(max = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "speciesID_i", nullable = false)
	public int getSpeciesIdI() {
		return this.speciesIdI;
	}

	public void setSpeciesIdI(int speciesIdI) {
		this.speciesIdI = speciesIdI;
	}

	@Column(name = "speciesID", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getSpeciesId() {
		return this.speciesId;
	}

	public void setSpeciesId(String speciesId) {
		this.speciesId = speciesId;
	}

	@Column(name = "species_id", nullable = false)
	public int getSpeciesId_1() {
		return this.speciesId_1;
	}

	public void setSpeciesId_1(int speciesId_1) {
		this.speciesId_1 = speciesId_1;
	}

}
