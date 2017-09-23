package com.easybusiness.usermanagement.dto;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "USER_PROFESSION")
public class UserProfessionDTO implements Serializable {

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

    @Column(name = "TOTAL_EXP")
    private String totalExp;

    @Column(name = "RELEVANT_EXP")
    private String relevantExp;

    @Column(name = "PRIMARY_SKILL")
    private String primarySkill;

    @Column(name = "PRIMARY_SKILL_DURATION")
    private String primarySkillDuration;

    @Column(name = "PREV_ORGANIZATION")
    private String prevOrganization;

    @Column(name = "PREV_FROM_DATE")
    private Date prevFromDate;

    @Column(name = "PREV_TO_DATE")
    private Date prevToDate;

    @Column(name = "PREV_ONSITE_DURATION")
    private String prevOnsiteDuration;

    @Column(name = "PREV_TECHNOLOGY")
    private String prevTechnology;

    @Column(name = "PREV_ACHIEV")
    private String prevAchievement;

    @Column(name = "PREV_DESIGNATION")
    private String prevDesignation;

    public UserProfessionDTO() {
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

    public String getTotalExp() {
	return totalExp;
    }

    public void setTotalExp(String totalExp) {
	this.totalExp = totalExp;
    }

    public String getRelevantExp() {
	return relevantExp;
    }

    public void setRelevantExp(String relevantExp) {
	this.relevantExp = relevantExp;
    }

    public String getPrimarySkill() {
	return primarySkill;
    }

    public void setPrimarySkill(String primarySkill) {
	this.primarySkill = primarySkill;
    }

    public String getPrimarySkillDuration() {
	return primarySkillDuration;
    }

    public void setPrimarySkillDuration(String primarySkillDuration) {
	this.primarySkillDuration = primarySkillDuration;
    }

    public String getPrevOrganization() {
	return prevOrganization;
    }

    public void setPrevOrganization(String prevOrganization) {
	this.prevOrganization = prevOrganization;
    }

    public Date getPrevFromDate() {
	return prevFromDate;
    }

    public void setPrevFromDate(Date prevFromDate) {
	this.prevFromDate = prevFromDate;
    }

    public Date getPrevToDate() {
	return prevToDate;
    }

    public void setPrevToDate(Date prevToDate) {
	this.prevToDate = prevToDate;
    }

    public String getPrevOnsiteDuration() {
	return prevOnsiteDuration;
    }

    public void setPrevOnsiteDuration(String prevOnsiteDuration) {
	this.prevOnsiteDuration = prevOnsiteDuration;
    }

    public String getPrevTechnology() {
	return prevTechnology;
    }

    public void setPrevTechnology(String prevTechnology) {
	this.prevTechnology = prevTechnology;
    }

    public String getPrevAchievement() {
	return prevAchievement;
    }

    public void setPrevAchievement(String prevAchievement) {
	this.prevAchievement = prevAchievement;
    }

    public String getPrevDesignation() {
	return prevDesignation;
    }

    public void setPrevDesignation(String prevDesignation) {
	this.prevDesignation = prevDesignation;
    }

    @Override
    public String toString() {
	return "UserProfession [id=" + id + ", user=" + user + ", totalExp=" + totalExp + ", relevantExp=" + relevantExp
		+ ", primarySkill=" + primarySkill + ", primarySkillDuration=" + primarySkillDuration
		+ ", prevOrganization=" + prevOrganization + ", prevFromDate=" + prevFromDate + ", prevToDate="
		+ prevToDate + ", prevOnsiteDuration=" + prevOnsiteDuration + ", prevTechnology=" + prevTechnology
		+ ", prevAchievement=" + prevAchievement + ", prevDesignation=" + prevDesignation + "]";
    }

}
