package com.groupX.appX.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

	@Email(message = "Username needs to be an email")
	@NotBlank(message = "username is required")
	@Column(length = 100,unique = true)
	private String username;

	@NotBlank(message = "Password field is required")
	private String password;

	@Transient
	private String confirmPassword;

	@NotBlank(message = "Please enter your name")
	private String name;

	@NotBlank(message = "Please enter your surname")
	private String surname;

	private Long age;

	private String userType;

	@OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Task> tasks = new ArrayList<>();

	public User(
			@Email(message = "Username needs to be an email") @NotBlank(message = "username is required") String username,
			@NotBlank(message = "Password field is required") String password, String confirmPassword, String name,
			String surname, Long age, String userType, List<Task> tasks) {
		super();
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.userType = userType;
		this.tasks = tasks;
	}

	public User() {

	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

}
