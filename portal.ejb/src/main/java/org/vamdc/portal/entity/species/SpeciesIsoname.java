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
@Table(name = "species_isoname")
public class SpeciesIsoname implements java.io.Serializable {

	private static final long serialVersionUID = 3087857114633876868L;
	
	private Integer id;
	private String name;
	private int isoId;
	private String ref;

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

	@Column(name = "iso_id", nullable = false)
	public int getIsoId() {
		return this.isoId;
	}

	public void setIsoId(int isoId) {
		this.isoId = isoId;
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
