package com.example.demo.controller;

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

import com.example.demo.model.Persona;
import com.example.demo.service.PersonaService;
import com.example.demo.validator.PersonaValidator;

@Controller
public class PersonaController {
	
	@Autowired
	private PersonaService ps;
	
	@Autowired
	private PersonaValidator pv;
	
	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	*/
	
	@PostMapping("/persona")
	public String addPersona(@Valid @ModelAttribute("persona") Persona persona, 
			BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			this.ps.save(persona);
			model.addAttribute("persona", ps.findById(persona.getId()));
			return "persona.html";
		}
		else
			return "personaForm.html";
	}

	// richiede tutte le persone, non c'è id
	@GetMapping("/persone")
	public String getPersone(Model model) {
		List<Persona> persone = ps.findAll();
		model.addAttribute("persone", persone);
		return "persone.html";
	}
	
	// richiede tutte le persone, non c'è id
	@GetMapping("/persona/{id}")
	public String getPersona(@PathVariable("id")Long id, Model model) {
		Persona persona = ps.findById(id);
		model.addAttribute("persona", persona);
		return "persona.html";
	}
	
	@GetMapping("/personaForm/{id}")
	public String getPersona(Model model) {
		model.addAttribute("persona", new Persona());
		return "personaForm.html";
	}
}
