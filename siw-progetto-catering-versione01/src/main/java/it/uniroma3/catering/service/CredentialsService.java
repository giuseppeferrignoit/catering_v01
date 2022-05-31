package it.uniroma3.catering.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.catering.model.Credenziali;
import it.uniroma3.catering.repository.CredentialsRepository;


@Service
public class CredentialsService {
	
    @Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credenziali getCredentials(Long id) {
		Optional<Credenziali> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credenziali getCredentials(String username) {
		Optional<Credenziali> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}
		
    @Transactional
    public Credenziali saveCredentials(Credenziali credentials) {
        credentials.setRole(Credenziali.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
}
