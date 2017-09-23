package com.easybusiness.usermanagement.dto;

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
@Table(name="ROLE_ACTION_MAP_MASTER")
public class RoleActionMapDTO {
    
    @Id
    @Column(name = "MAPPING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "ROLE_ID")
    private RoleDTO role;
    
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
    
    @Column(name = "IS_CREATE_ALLOWED")
    private long createAllowed;
    
    @Column(name = "IS_UPDATE_ALLOWED")
    private long updateAllowed;
    
    @Column(name = "IS_VIEW_ALLOWED")
    private long viewAllowed;
    
    @Column(name = "IS_DELETE_ALLOWED")
    private long deleteAllowed;
    
    @Column(name = "IS_DOWNLOAD_ALLOWED")
    private long downloadAllowed;
    
    @Column(name = "IS_UPLOAD_ALLOWED")
    private long uploadAllowed;
    
    @Column(name = "IS_PRINT_ALLOWED")
    private long printAllowed;
    
    @Column(name = "IS_SELECT_ALLOWED")
    private long selectAllowed;
    
    

    public RoleActionMapDTO() {
	super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
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

    public long getCreateAllowed() {
        return createAllowed;
    }

    public void setCreateAllowed(long createAllowed) {
        this.createAllowed = createAllowed;
    }

    public long getUpdateAllowed() {
        return updateAllowed;
    }

    public void setUpdateAllowed(long updateAllowed) {
        this.updateAllowed = updateAllowed;
    }

    public long getViewAllowed() {
        return viewAllowed;
    }

    public void setViewAllowed(long viewAllowed) {
        this.viewAllowed = viewAllowed;
    }

    public long getDeleteAllowed() {
        return deleteAllowed;
    }

    public void setDeleteAllowed(long deleteAllowed) {
        this.deleteAllowed = deleteAllowed;
    }

    public long getDownloadAllowed() {
        return downloadAllowed;
    }

    public void setDownloadAllowed(long downloadAllowed) {
        this.downloadAllowed = downloadAllowed;
    }

    public long getUploadAllowed() {
        return uploadAllowed;
    }

    public void setUploadAllowed(long uploadAllowed) {
        this.uploadAllowed = uploadAllowed;
    }

    public long getPrintAllowed() {
        return printAllowed;
    }

    public void setPrintAllowed(long printAllowed) {
        this.printAllowed = printAllowed;
    }

    public long getSelectAllowed() {
        return selectAllowed;
    }

    public void setSelectAllowed(long selectAllowed) {
        this.selectAllowed = selectAllowed;
    }

    @Override
    public String toString() {
	return "RoleActionMap [id=" + id + ", role=" + role + ", isEnable=" + isEnable + ", fromDate=" + fromDate
		+ ", toDate=" + toDate + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
		+ ", createAllowed=" + createAllowed + ", updateAllowed=" + updateAllowed + ", viewAllowed="
		+ viewAllowed + ", deleteAllowed=" + deleteAllowed + ", downloadAllowed=" + downloadAllowed
		+ ", uploadAllowed=" + uploadAllowed + ", printAllowed=" + printAllowed + ", selectAllowed="
		+ selectAllowed + "]";
    }
    
   
    


}
