package it.uniroma3.catering.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.catering.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {
	
	public Piatto findByNome(String nomePiatto);


}
