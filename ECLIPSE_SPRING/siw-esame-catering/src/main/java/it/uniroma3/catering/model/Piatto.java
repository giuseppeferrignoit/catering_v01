package it.uniroma3.catering.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
@ NamedQuery (name =  "findAllPiatti", query = "SELECT p FROM Piatto p")
public class Piatto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(unique=true)
	private String nome;
	
	@NotBlank
	private String descrizione;
	
	@ManyToOne
	private Buffet buffet;

	@OneToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, 
			mappedBy="piatto")
	private List<Ingrediente> ingredienti;
	
	//-----------------------------

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}
	
	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}
}
