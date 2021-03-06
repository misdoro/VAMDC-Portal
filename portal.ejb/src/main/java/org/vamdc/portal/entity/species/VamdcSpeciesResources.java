package org.vamdc.portal.entity.species;

// Generated 18.09.2012 16:57:53 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * VamdcSpeciesResources generated by hbm2java
 */
@Entity
@Table(name = "vamdc_species_resources", catalog = "vamdc_species", uniqueConstraints = @UniqueConstraint(columnNames = {
		"species_id", "url" }))
public class VamdcSpeciesResources implements java.io.Serializable {

	private Integer id;
	private VamdcSpecies vamdcSpecies;
	private String url;
	private String description;
	private int searchPriority;
	private Date created;

	public VamdcSpeciesResources() {
	}

	public VamdcSpeciesResources(VamdcSpecies vamdcSpecies, String url,
			String description, int searchPriority, Date created) {
		this.vamdcSpecies = vamdcSpecies;
		this.url = url;
		this.description = description;
		this.searchPriority = searchPriority;
		this.created = created;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "species_id", nullable = false)
	@NotNull
	public VamdcSpecies getVamdcSpecies() {
		return this.vamdcSpecies;
	}

	public void setVamdcSpecies(VamdcSpecies vamdcSpecies) {
		this.vamdcSpecies = vamdcSpecies;
	}

	@Column(name = "url", nullable = false)
	@NotNull
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "description", nullable = false, length = 150)
	@NotNull
	@Length(max = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "search_priority", nullable = false)
	public int getSearchPriority() {
		return this.searchPriority;
	}

	public void setSearchPriority(int searchPriority) {
		this.searchPriority = searchPriority;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	@NotNull
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
