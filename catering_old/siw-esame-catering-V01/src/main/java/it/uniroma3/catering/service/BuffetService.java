package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) { 
		// Il save è di tipo transactional
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) { 
		// Il save è di tipo transactional
		buffetRepository.delete(buffet);
	}
	
	@Transactional
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}
	
	public Buffet findById (Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll() {
		List<Buffet> buffets = new ArrayList<Buffet>();
		for (Buffet b : buffetRepository.findAll()) {
			buffets.add(b);
		}
		return buffets;
	}
	
	// Metodo che risponde ad una validazione del Validator
	public boolean alreadyExists(Buffet buffet) {
		return this.findAll().contains(buffet);
	}
}