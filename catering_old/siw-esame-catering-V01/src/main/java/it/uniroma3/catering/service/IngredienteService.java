package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Ingrediente;
import it.uniroma3.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Transactional
	public void save(Ingrediente ingrediente) { 
		// Il save è di tipo transactional
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) { 
		// Il save è di tipo transactional
		ingredienteRepository.delete(ingrediente);
	}
	
	@Transactional
	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}
	
	public Ingrediente findById (Long id) {
		return ingredienteRepository.findById(id).get();
	}
	
	public List<Ingrediente> findAll() {
		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
		for (Ingrediente i : ingredienteRepository.findAll()) {
			ingredienti.add(i);
		}
		return ingredienti;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Ingrediente ingrediente) {
		return this.findAll().contains(ingrediente);
	}
}