package com.easybusiness.usermanagement.dto;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;

@Entity
@Table(name = "USER_DETAILS")
public class UserImageDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id()
    @Column(name = "MAPPING_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty
    @JsonSerialize(using = ByteArraySerializer.class)
    @Lob
    @Column(name = "USER_IMG")
    
    private byte[] userImg;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "USER_ID")
    private UserDTO user;
    
    public UserImageDTO() {
	super();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    
    @Lob
    public byte[] getUserImg() {
        return userImg;
    }

    public void setUserImg(byte[] userImg) {
        this.userImg = userImg;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
	return "UserImage [id=" + id + ", userImg=" + userImg + ", user=" + user + "]";
    }

   

}
