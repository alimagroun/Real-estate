package com.magroun.realestate.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  
  @NotBlank
  @Size(max = 50)
  private String name;
  
  @NotBlank
  @Size(max = 20)
  @Column(name = "contact_number")
  private String contactNumber;
  
  @Column(name = "reset_cote")
  private Integer resetCode;
  
  @Column(name = "reset_cote_time")
  private LocalDateTime resetCodeTime;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }



  public User(String username,String email,
		String name, String contactNumber,
		String password) {
	super();
	this.username = username;
	this.email = email;
	this.name = name;
	this.contactNumber = contactNumber;
	this.password = password;
}



public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

public Integer getResetCode() {
	return resetCode;
}

public void setResetCode(Integer resetCode) {
	this.resetCode = resetCode;
}

public LocalDateTime getResetCodeTime() {
	return resetCodeTime;
}

public void setResetCodeTime(LocalDateTime resetCodeTime) {
	this.resetCodeTime = resetCodeTime;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getContactNumber() {
	return contactNumber;
}

public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}
  
}
