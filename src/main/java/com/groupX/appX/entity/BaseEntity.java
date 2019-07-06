package com.groupX.appX.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	@PrePersist
	public void prePersist() {
		this.createDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PreUpdate
	public void preUpdate() {
		this.updateDate = new Date();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}

/*
 * 
 * @PrePersist Executed before the entity manager persist operation is actually
 * executed or cascaded. This call is synchronous with the persist operation.
 * 
 * @PreUpdate Executed before the database UPDATE operation.
 * 
 */