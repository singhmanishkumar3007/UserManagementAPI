package com.easybusiness.usermanagement.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACADEMICS")
public class UserAcademicsDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id()
    @Column(name = "MAPPING_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_ID")
    private UserDTO user;

    @Column(name = "HIGHEST_DEGREE")
    private String highestDegree;

    @Column(name = "PASS_OUT_YEAR")
    private String passOutYear;

    @Column(name = "COLLEGE")
    private String college;

    @Column(name = "UNIVERSITY")
    private String university;

    @Column(name = "MARKS")
    private String marks;
    
    @Column(name = "CERTIFICATE_NAME")
    private String certificateName;
    
    @Column(name = "CERTIFIED_BY")
    private String certifiedBy;
    
    @Column(name = "CERTIFICATION_YEAR")
    private String certificationYear;

    public UserAcademicsDTO() {
	super();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getHighestDegree() {
	return highestDegree;
    }

    public void setHighestDegree(String highestDegree) {
	this.highestDegree = highestDegree;
    }

    public String getPassOutYear() {
	return passOutYear;
    }

    public void setPassOutYear(String passOutYear) {
	this.passOutYear = passOutYear;
    }

    public String getCollege() {
	return college;
    }

    public void setCollege(String college) {
	this.college = college;
    }

    public String getUniversity() {
	return university;
    }

    public void setUniversity(String university) {
	this.university = university;
    }

    public String getMarks() {
	return marks;
    }

    public void setMarks(String marks) {
	this.marks = marks;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertifiedBy() {
        return certifiedBy;
    }

    public void setCertifiedBy(String certifiedBy) {
        this.certifiedBy = certifiedBy;
    }

    public String getCertificationYear() {
        return certificationYear;
    }

    public void setCertificationYear(String certificationYear) {
        this.certificationYear = certificationYear;
    }

    @Override
    public String toString() {
	return "UserAcademicsDTO [id=" + id + ", user=" + user + ", highestDegree=" + highestDegree + ", passOutYear="
		+ passOutYear + ", college=" + college + ", university=" + university + ", marks=" + marks
		+ ", certificateName=" + certificateName + ", certifiedBy=" + certifiedBy + ", certificationYear="
		+ certificationYear + "]";
    }


}
