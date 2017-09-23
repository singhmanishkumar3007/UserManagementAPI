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

import com.easybusiness.modelmanagement.entity.Menu;
import com.easybusiness.modelmanagement.entity.SubMenu;
import com.easybusiness.modelmanagement.entity.UserGroup;

@Entity
@Table(name = "USER_GRP_MENU_MASTER")
public class UserGroupMenuSubMenuDTO implements Serializable {

    private static final long serialVersionUID = 536872931329532182L;

    @Id
    @Column(name = "MAPPING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "MENU_ID")
    private MenuDTO menuItem;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "SUBMENU_ID")
    private SubMenuDTO subMenuItem;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_GRP_ID")
    private UserGroupDTO userGroup;

    @Column(name = "IS_ENABLE")
    private Long isEnable;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "END_DATE")
    private Date toDate;

    @Column(name = "MOD_BY")
    private String modifiedBy;

    @Column(name = "MOD_ON")
    private Date modifiedOn;

    public UserGroupMenuSubMenuDTO() {
	super();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }


    public MenuDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuDTO menuItem) {
        this.menuItem = menuItem;
    }

    public UserGroupDTO getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDTO userGroup) {
        this.userGroup = userGroup;
    }

    public void setSubMenuItem(SubMenuDTO subMenuItem) {
        this.subMenuItem = subMenuItem;
    }

    public Long getIsEnable() {
	return isEnable;
    }

    public void setIsEnable(Long isEnable) {
	this.isEnable = isEnable;
    }

    public Date getFromDate() {
	return fromDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
    }

    public Date getToDate() {
	return toDate;
    }

    public void setToDate(Date toDate) {
	this.toDate = toDate;
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


    public SubMenuDTO getSubMenuItem() {
        return subMenuItem;
    }

    @Override
    public String toString() {
	return "UserGroupMenuSubMenu [id=" + id + ", menuItem=" + menuItem + ", subMenuItem=" + subMenuItem
		+ ", userGroup=" + userGroup + ", isEnable=" + isEnable + ", fromDate=" + fromDate + ", toDate="
		+ toDate + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + "]";
    }

}
