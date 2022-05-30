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

import it.uniroma3.catering.model.Piatto;
import it.uniroma3.catering.service.PiattoService;
import it.uniroma3.catering.validator.PiattoValidator;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private PiattoValidator piattoValidator;
	
	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	*/
	
	// METODO POST PER INSERIRE UN NUOVO INGREDIENTE

	@PostMapping("/piatto")
	public String addPiatto(@Valid @ModelAttribute(value="piatto") Piatto piatto, 
			BindingResult bindingResult, Model model) {
		
		/* Se non ci sono errori inserisce la ricorrenza di ingrediente 
		 * tramite la save del service 
		 * */
		
		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.piattoValidator.validate(piatto, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			this.piattoService.save(piatto); // salvo un oggetto Piatto
			model.addAttribute("piatto", piatto);
			
			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla form di visualizzazione dati inseriti
			return "piatto.html"; 
		}
		else {
			model.addAttribute("piatto", piatto);
			// se ci sono errori si rimanda alla form di inserimento
			return "piattoForm.html"; 
		}
	}
	
	// METODI PER DELETE
	
		@GetMapping("/confermaDeletePiatto/{id}")
		public String confermaDeletePiatto(@PathVariable("id") Long id, Model model) {
			model.addAttribute("piatto", this.piattoService.findById(id));
			return "confermaDeletePiatto.html";
		}
		
		@GetMapping("/deletePiatto/{id}")
		public String deletePiatto(@PathVariable("id") Long id, Model model) {
			model.addAttribute("piatto", this.piattoService.findById(id));
			this.piattoService.deleteById(id);
			return "piattoDelete.html";
		}
	
	// METODI GET

	// richiede un singolo chef tramite id
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		// ritorna la form con i dati dell'entità richiesta
		return "piatto.html";
	}
	
	// richiede tutti i piatti, non c'è id
	@GetMapping("/piatti")
	public String getPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "piatti.html";
	}
	
	@GetMapping("/piattoForm")
	public String piattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		return "piattoForm.html";
	}
}