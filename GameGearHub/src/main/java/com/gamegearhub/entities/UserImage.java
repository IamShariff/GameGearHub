package com.gamegearhub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model for UserImage
 * @author mdsharif
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class UserImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;

	@Column(length = 50000000)
	private byte[] picByte;

	public UserImage(String name, String type, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}
}
