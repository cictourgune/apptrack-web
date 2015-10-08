package org.tourgune.egistour.controller.api.secure;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tourgune.egistour.bean.Visor;
import org.tourgune.egistour.facade.VisorFacade;

@Controller
@RequestMapping("api/visores")
public class VisoresSecureAPI {
	@Resource
	private VisorFacade visorFacade;
	
	@RequestMapping(value = "/updateloc", method = RequestMethod.GET)
	public @ResponseBody
	String updateApplication(
		
			@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "zoom", required = false) Integer zoom) {

		double latitud = Double.parseDouble(lat.substring(1, lat.length() - 1));
		double longitud = Double
				.parseDouble(lng.substring(1, lng.length() - 1));

		
		Visor visor = visorFacade.getVisor(SecurityContextHolder
				.getContext().getAuthentication().getName());
		
		return visorFacade.updateLocation(visor.getIdvisor(), latitud,
				longitud, zoom);
	}
	
	@RequestMapping(value = "/visor", method = RequestMethod.GET)
	public @ResponseBody
	Visor getVisor() {
		
		Visor visor = visorFacade.getVisor(SecurityContextHolder
				.getContext().getAuthentication().getName());
		return visor;
	}

}
