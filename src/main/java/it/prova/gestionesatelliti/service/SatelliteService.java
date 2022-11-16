package it.prova.gestionesatelliti.service;

import java.util.Date;
import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);
	
	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuovi(Long id);
	
	public List<Satellite> findByExample(Satellite example);
	
	public 	List<Satellite> FindSatellitiDueAnni(Date dataOggi);
	
	public 	List<Satellite> FindSatellitiOffDateRientroNull();
	
	public List<Satellite> FindSatellitiDieciAnniOrbita(Date dataOggi);
	
	public List<Satellite> ListAllDaDisabilitare();
	
}
