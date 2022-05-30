package it.uniroma3.catering.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.service.BuffetService;
import it.uniroma3.catering.validator.BuffetValidator;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	*/
	
	// METODO POST PER INSERIRE UN NUOVO BUFFET
	
	@PostMapping("/buffet")
	public String addBuffet(@Valid @ModelAttribute(value="buffet") Buffet buffet, 
			BindingResult bindingResult, Model model) {
		
		// Creo una entità Buffet dai dati di input della stringa HTTP usando i metodi Get
		// I risultati della validazione sono riportati in BindingResult
		/* 
		 * Se non ci sono errori inserisce la ricorrenza di Buffet 
		 * tramite la save del service 
		 * */
		
		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.buffetValidator.validate(buffet, bindingResult);
		
		if (!bindingResult.hasErrors()) { 
			
			// Se non ci sono errori
			this.buffetService.save(buffet); // Salvo un oggetto Buffet
			model.addAttribute("buffet", buffet);
			
			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla form di visualizzazione dati inseriti
			return "buffet.html"; 
		}
		else {
			model.addAttribute("buffet", buffet);
			// se ci sono errori si rimanda alla form di inserimento
			return "buffetForm.html"; 
		}
	}
	// METODI PER DELETE
	
	@GetMapping("/confermaDeleteBuffet/{id}")
	public String confermaDeleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		return "confermaDeleteBuffet.html";
	}
	
	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		this.buffetService.deleteById(id);
		return "buffetDelete.html";
	}
	
	// METODI GET
	
	// richiede un singolo buffet tramite id
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) { // id è una variabile associata al path
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet.html"; // ritorna la form con i dati dell'entità richiesta
	}
	
	// richiede tutti i buffets, non c'è id
	@GetMapping("/buffets")
	public String getBuffets(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "buffets.html";
	}
	
	@GetMapping("/buffetForm")
	public String buffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "buffetForm.html";
	}
}