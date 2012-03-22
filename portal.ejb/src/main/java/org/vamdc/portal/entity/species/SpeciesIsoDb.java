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
@Table(name = "species_iso_db")
public class SpeciesIsoDb implements java.io.Serializable {

	private static final long serialVersionUID = -3485786330265247297L;
	private Integer id;
	private String name;
	private int isoIdI;
	private String isoId;
	private int speciesIdI;
	private int isoId_1;

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

	@Column(name = "isoID_i", nullable = false)
	public int getIsoIdI() {
		return this.isoIdI;
	}

	public void setIsoIdI(int isoIdI) {
		this.isoIdI = isoIdI;
	}

	@Column(name = "isoID", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getIsoId() {
		return this.isoId;
	}

	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}

	@Column(name = "speciesID_i", nullable = false)
	public int getSpeciesIdI() {
		return this.speciesIdI;
	}

	public void setSpeciesIdI(int speciesIdI) {
		this.speciesIdI = speciesIdI;
	}

	@Column(name = "iso_id", nullable = false)
	public int getIsoId_1() {
		return this.isoId_1;
	}

	public void setIsoId_1(int isoId_1) {
		this.isoId_1 = isoId_1;
	}

}
