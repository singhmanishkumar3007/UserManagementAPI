package com.easybusiness.usermanagement.dto;

import java.sql.Date;
import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_DETAILS")
public class UserDTO {

    @Id()
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USER_LOGIN_ID")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DOB")
    private Date dateOfBirth;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ALTR_EMAIL")
    private String alternateEmail;

    @Column(name = "MOBILE")
    private Long mobile;

    @Column(name = "END_DATE")
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "ORGANIZATION")
    private OrganizationDTO organization;

    @OneToOne
    @JoinColumn(name = "DEPARTMENT")
    private DepartmentDTO department;

    @OneToOne
    @JoinColumn(name = "DESIGNATION")
    private DesignationDTO designation;

    @Column(name = "TYPE_OF_EMP")
    private String typeOfEmployment;

    @Column(name = "IS_ENABLE")
    private Long isEnabled;

    @Column(name = "USER_IMG")
    private byte[] userImg;

    @Column(name = "MOD_BY")
    private String modifiedBy;

    @Column(name = "MOD_DT")
    private Date modifiedOn;

    @Column(name = "PERM_ADDR")
    private String permAddr;

    @Column(name = "STATE")
    private String state;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "FATHER")
    private String fatherName;

    @Column(name = "SPOUSE")
    private String spouseName;

    @Column(name = "PASSPORT")
    private String passport;

    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCATION_ID")
    private LocationMasterDTO location;

    @Column(name = "UNIT_ID")
    private Long unitId;

    public UserDTO() {
	super();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getAlternateEmail() {
	return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
	this.alternateEmail = alternateEmail;
    }

    public Long getMobile() {
	return mobile;
    }

    public void setMobile(Long mobile) {
	this.mobile = mobile;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public OrganizationDTO getOrganization() {
	return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
	this.organization = organization;
    }

    public DepartmentDTO getDepartment() {
	return department;
    }

    public void setDepartment(DepartmentDTO department) {
	this.department = department;
    }

    public DesignationDTO getDesignation() {
	return designation;
    }

    public void setDesignation(DesignationDTO designation) {
	this.designation = designation;
    }

    public String getTypeOfEmployment() {
	return typeOfEmployment;
    }

    public void setTypeOfEmployment(String typeOfEmployment) {
	this.typeOfEmployment = typeOfEmployment;
    }

    public Long getIsEnabled() {
	return isEnabled;
    }

    public void setIsEnabled(Long isEnabled) {
	this.isEnabled = isEnabled;
    }

    public byte[] getUserImg() {
	return userImg;
    }

    public void setUserImg(byte[] userImg) {
	this.userImg = userImg;
    }

    public String getModifiedBy() {
	return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
	return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
	this.modifiedOn = modifiedOn;
    }

    public String getPermAddr() {
	return permAddr;
    }

    public void setPermAddr(String permAddr) {
	this.permAddr = permAddr;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getFatherName() {
	return fatherName;
    }

    public void setFatherName(String fatherName) {
	this.fatherName = fatherName;
    }

    public String getSpouseName() {
	return spouseName;
    }

    public void setSpouseName(String spouseName) {
	this.spouseName = spouseName;
    }

    public String getPassport() {
	return passport;
    }

    public void setPassport(String passport) {
	this.passport = passport;
    }

    public LocationMasterDTO getLocation() {
        return location;
    }

    public void setLocation(LocationMasterDTO location) {
        this.location = location;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
	return "UserDTO [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", fromDate=" + fromDate
		+ ", lastName=" + lastName + ", password=" + password + ", gender=" + gender + ", dateOfBirth="
		+ dateOfBirth + ", email=" + email + ", alternateEmail=" + alternateEmail + ", mobile=" + mobile
		+ ", endDate=" + endDate + ", organization=" + organization + ", department=" + department
		+ ", designation=" + designation + ", typeOfEmployment=" + typeOfEmployment + ", isEnabled=" + isEnabled
		+ ", userImg=" + Arrays.toString(userImg) + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
		+ ", permAddr=" + permAddr + ", state=" + state + ", city=" + city + ", country=" + country + ", zip="
		+ zip + ", fatherName=" + fatherName + ", spouseName=" + spouseName + ", passport=" + passport
		+ ", location=" + location + ", unitId=" + unitId + "]";
    }


}
