package it.prova.gestionesatelliti.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService impiegatoService;
	
	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = impiegatoService.listAllElements();
		mv.addObject("impiegato_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = impiegatoService.findByExample(example);
		model.addAttribute("impiegato_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_impiegato_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_impiegato_attr") Satellite impiegato, BindingResult result,
			RedirectAttributes redirectAttrs, Model model) {

		
		
		if (!(impiegato.getDataLancio() == null || impiegato.getDataRientro() == null )
				&& (impiegato.getDataLancio().after(impiegato.getDataRientro())) ) {
			
				result.rejectValue("dataLancio","satellite.datalancio.magiore.datarientro.error");
				return "satellite/insert";
		}
		
		if (impiegato.getDataLancio() == null && impiegato.getDataRientro() == null && impiegato.getStato() != null) {
			
			
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/insert";
		}
		
		if (impiegato.getDataLancio() != null && impiegato.getDataLancio().after(new Date())
				&& impiegato.getStato() != null) {
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/insert";
		}
		
		if (impiegato.getDataLancio() != null && impiegato.getDataLancio().after(new Date())
				&& impiegato.getDataRientro() != null && impiegato.getDataRientro().after(new Date())
				&& impiegato.getStato() != null) {
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/insert";
		}
		
		if (impiegato.getDataRientro() != null && impiegato.getDataLancio() == null) {
			
			result.rejectValue("dataLancio","satellite.datalancio.non.inserita");
			return "satellite/insert";
		}
		
		if (impiegato.getDataLancio() != null && impiegato.getDataRientro() == null
				&& impiegato.getDataLancio().after(new Date())
				&& impiegato.getStato() != null) {
			
			result.rejectValue("stato","satellite.datalancio.inserita.futura.error");
			return "satellite/insert";
		}
		
		if (impiegato.getDataLancio() != null 
				&& impiegato.getDataLancio().before(new Date())
				&& impiegato.getStato() == null) {
			
			result.rejectValue("stato","satellite.datalancio.inserita.passata.error");
			return "satellite/insert";
		}
		
		
		

		
		
		if (result.hasErrors()) {
			return "satellite/insert";
		}
		impiegatoService.inserisciNuovo(impiegato);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idImpiegato}")
	public String show(@PathVariable(required = true) Long idImpiegato, Model model) {
		model.addAttribute("show_impiegato_attr", impiegatoService.caricaSingoloElemento(idImpiegato));
		return "satellite/show";
	}
	
	@GetMapping("/delete/{idImpiegato}")
	public String confermaDelete(@PathVariable(required = true) Long idImpiegato, Model model) {
		
		model.addAttribute("show_impiegato_attr", impiegatoService.caricaSingoloElemento(idImpiegato));
		return "satellite/delete";
	}
	

	@PostMapping("/confermaDelete")
	public String delete(@RequestParam(name = "idSatellite") Long idImpiegato,
			RedirectAttributes redirectAttrs) {
		
		
		
		
		
		impiegatoService.rimuovi(idImpiegato);
		

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/update/{idImpiegato}")
	public String confermaUpdate(@PathVariable(required = true) Long idImpiegato, Model model) {
		
		model.addAttribute("insert_impiegato_attr", impiegatoService.caricaSingoloElemento(idImpiegato));
		return "satellite/update";
	}
	
	@PostMapping("/updatee")
	public String update(@Valid @ModelAttribute("insert_impiegato_attr") Satellite impiegato, BindingResult result,
			RedirectAttributes redirectAttrs,
			@RequestParam(name = "idSatellite") Long idImpiegato,
			Model model) {

		impiegato.setId(idImpiegato);
		
		if (!(impiegato.getDataLancio() == null || impiegato.getDataRientro() == null )
				&& (impiegato.getDataLancio().after(impiegato.getDataRientro())) ) {
			
			result.rejectValue("dataLancio","satellite.datalancio.magiore.datarientro.error");
			return "satellite/update";
		}
		
		if (impiegato.getDataRientro() != null && impiegato.getDataLancio() == null) {
			
			result.rejectValue("dataLancio","satellite.datalancio.non.inserita");
			return "satellite/update";
		}
		
		if (impiegato.getDataLancio() != null && impiegato.getDataLancio().after(new Date())
				&& impiegato.getDataRientro() != null && impiegato.getDataRientro().after(new Date())
				&& impiegato.getStato() != null) {
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/update";
		}
		
		if (impiegato.getDataLancio() == null && impiegato.getDataRientro() == null && impiegato.getStato() != null) {
			
			
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/update";
		}
		
		if (impiegato.getDataLancio() != null && impiegato.getDataLancio().after(new Date())
				&& impiegato.getStato() != null) {
			
			result.rejectValue("stato","satellite.stato.non.inserito");
			return "satellite/update";
		}
		
		
		
		if (result.hasErrors()) {
			return "satellite/update";
		}

		impiegatoService.aggiorna(impiegato);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/listDueAnni")
	public ModelAndView listAllDueAnni() {
		ModelAndView mv = new ModelAndView();
		
		Date dataProva = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataProva);
		calendar.add(Calendar.YEAR, -2);
		Date dataMenoDue = calendar.getTime();
		List<Satellite> results = impiegatoService.FindSatellitiDueAnni(dataMenoDue);
		mv.addObject("impiegato_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}
	
	@GetMapping("/listDisattivati")
	public ModelAndView listAllDisattivati() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = impiegatoService.FindSatellitiOffDateRientroNull();
		mv.addObject("impiegato_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}
	
	@GetMapping("/listOrbita")
	public ModelAndView listAllDieciAnniOrbita() {
		ModelAndView mv = new ModelAndView();
		
		Date dataProva = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataProva);
		calendar.add(Calendar.YEAR, -10);
		Date dataMenoDue = calendar.getTime();
		List<Satellite> results = impiegatoService.FindSatellitiDueAnni(dataMenoDue);
		mv.addObject("impiegato_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}
	
	@PostMapping("/lancia")
	public String lancia(@RequestParam(name = "idSatellite") Long idImpiegato,
			RedirectAttributes redirectAttrs, Model model) {
		Satellite satellite = impiegatoService.caricaSingoloElemento(idImpiegato);
		satellite.setDataLancio(new Date());
		impiegatoService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@PostMapping("/rientro")
	public String rientro(@RequestParam(name = "idSatellite") Long idImpiegato,
			RedirectAttributes redirectAttrs, Model model) {
		Satellite satellite = impiegatoService.caricaSingoloElemento(idImpiegato);
		if (satellite.getDataLancio() != null
			&& satellite.getDataLancio().after(new Date())){
			redirectAttrs.addFlashAttribute("errorMessage", "Non si pu?? far rientrare un satellite ancora non lanciato");
			return "redirect:/satellite";
		}
		
		if (satellite.getDataLancio() != null) {
			satellite.setDataRientro(new Date());
			satellite.setStato(StatoSatellite.DISATTIVATO);
			impiegatoService.aggiorna(satellite);
		}

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/confermaDisabilita")
	public ModelAndView listAllDaDisabilitare() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = impiegatoService.ListAllDaDisabilitare();
		List<Satellite> listAll = impiegatoService.listAllElements();
		
		mv.addObject("impiegato_list_all", listAll);
		mv.addObject("impiegato_list_attribute", results);
		mv.setViewName("satellite/confermaDisabilita");
		return mv;
	}
	
	@PostMapping("/disabilita")
	public String disabilitaTutti(
			RedirectAttributes redirectAttrs, Model model) {
		List<Satellite> results = impiegatoService.ListAllDaDisabilitare();
		for (Satellite satellite : results) {
			satellite.setDataRientro(new Date());
			satellite.setStato(StatoSatellite.DISATTIVATO);
			impiegatoService.aggiorna(satellite);
		}
		
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/home";
	}
}
