package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Piatto;
import it.uniroma3.catering.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) { 
		// Il save è di tipo transactional
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) { 
		// Il save è di tipo transactional
		piattoRepository.delete(piatto);
	}
	
	@Transactional
	public void deleteById(Long id) {
		piattoRepository.deleteById(id);
	}
	
	public Piatto findById (Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public List<Piatto> findAll() {
		List<Piatto> piatti = new ArrayList<Piatto>();
		for (Piatto p : piattoRepository.findAll()) {
			piatti.add(p);
		}
		return piatti;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Piatto piatto) {
		return this.findAll().contains(piatto);
	}
}
