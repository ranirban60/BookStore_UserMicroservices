package com.example.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="User")
public @ToString class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
	Long userId;
	String firstName;
	String lastNane;
	String address;
	@Column(name = "email", nullable = false)
	String email_address;
	LocalDate DOB;
	String password;
	
	 public UserModel(UserDto userdto) {
	        this.firstName = userdto.getFirstName();
	        this.lastNane = userdto.getLastName();
	        this.address = userdto.getAddress();
	        this.email_address = userdto.getEmail_address();
	        this.DOB = userdto.getDOB();
	        this.password = userdto.getPassword();
	    }
}
