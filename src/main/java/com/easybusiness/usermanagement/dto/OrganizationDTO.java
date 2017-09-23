package com.easybusiness.usermanagement.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "ORGANIZATION")
public class OrganizationDTO {

    @Id
    @Column(name = "ORG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    
    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "ORG_LOCATION")
    private String orgLocation;


    public OrganizationDTO() {
	super();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLocation() {
        return orgLocation;
    }

    public void setOrgLocation(String orgLocation) {
        this.orgLocation = orgLocation;
    }

    @Override
    public String toString() {
	return "Organization [id=" + id + ", orgName=" + orgName + ", orgLocation=" + orgLocation + "]";
    }

   

   


}
