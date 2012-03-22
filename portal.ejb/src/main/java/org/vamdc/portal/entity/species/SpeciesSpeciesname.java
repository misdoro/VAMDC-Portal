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
@Table(name = "species_speciesname")
public class SpeciesSpeciesname implements java.io.Serializable {

	private static final long serialVersionUID = 994996545349236733L;
	private Integer id;
	private String name;
	private int speciesId;
	private String ref;

	public SpeciesSpeciesname() {
	}

	public SpeciesSpeciesname(String name, int speciesId) {
		this.name = name;
		this.speciesId = speciesId;
	}

	public SpeciesSpeciesname(String name, int speciesId, String ref) {
		this.name = name;
		this.speciesId = speciesId;
		this.ref = ref;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "species_id", nullable = false)
	public int getSpeciesId() {
		return this.speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

	@Column(name = "ref", length = 10)
	@Length(max = 10)
	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

}
