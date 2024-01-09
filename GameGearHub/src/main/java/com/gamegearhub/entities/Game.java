package com.gamegearhub.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model for game
 * @author mdsharif
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Game extends RepresentationModel<Game> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gameId;
	private String gameDescription;
	private Integer gameRating;
	private String gameTitle;
	private String gameDate;
	private String genre;
	private Integer quantity;
	private Long gamePrice;

//	@ElementCollection
//	private List<String> imageNames;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "game_images", joinColumns = { @JoinColumn(name = "game_id") }, inverseJoinColumns = {
			@JoinColumn(name = "id") })
	private Set<GameImage> gameImages = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> pincode;


}
