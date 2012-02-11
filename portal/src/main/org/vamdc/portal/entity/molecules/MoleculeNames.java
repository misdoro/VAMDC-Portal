package org.vamdc.portal.entity.molecules;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.validator.Length;

@Entity
@NamedQueries({
    @NamedQuery(name = "MoleculeNames.findAll", query = "SELECT m FROM MoleculeNames m"),
    @NamedQuery(name = "MoleculeNames.findById", query = "SELECT m FROM MoleculeNames m WHERE m.id = :id"),
    @NamedQuery(name = "MoleculeNames.findByMolecName", query = "SELECT m FROM MoleculeNames m WHERE m.molecName = :molecName"),
    @NamedQuery(name = "MoleculeNames.findByMolecNameWildcard", query = "SELECT m FROM MoleculeNames m WHERE m.molecName LIKE :molecName"),
    @NamedQuery(name = "MoleculeNames.findByInChIKeyStem", query = "SELECT m FROM MoleculeNames m WHERE m.inChIkeyStem = :inChIkeyStem"),
    @NamedQuery(name = "MoleculeNames.findByMolecID", query = "SELECT m FROM MoleculeNames m WHERE m.molecId = :molecId")})
public class MoleculeNames implements Serializable {


	private static final long serialVersionUID = -4051495954491982028L;
	private Integer id;
	private String molecName;
	private String inChIkeyStem;
	private Integer molecId;

	@Id	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "molec_name", length = 256)
	@Length(max = 256)
	public String getMolecName() {
		return this.molecName;
	}

	public void setMolecName(String molecName) {
		this.molecName = molecName;
	}

	@Column(name = "InChIKeyStem", length = 14)
	@Length(max = 14)
	public String getInChIkeyStem() {
		return this.inChIkeyStem;
	}

	public void setInChIkeyStem(String inChIkeyStem) {
		this.inChIkeyStem = inChIkeyStem;
	}

	@Column(name = "molecID")
	public Integer getMolecId() {
		return this.molecId;
	}

	public void setMolecId(Integer molecId) {
		this.molecId = molecId;
	}

}
