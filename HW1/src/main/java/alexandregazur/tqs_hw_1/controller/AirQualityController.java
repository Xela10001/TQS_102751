package alexandregazur.tqs_hw_1.controller;

import alexandregazur.tqs_hw_1.model.Request;
import alexandregazur.tqs_hw_1.service.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AirQualityController {
	
	@Autowired
	private AirQualityService airQualityService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PostMapping("/search")
	public String showAirQuality(@RequestParam("query") String query, @RequestParam("inlineRadioOptions") int option, Model model) {
		model.addAttribute("request", airQualityService.getForecastAirQuality(query, option));
		String day;
		if(option == 0)
			day = "Today";
		else if (option == 1)
			day = "Tomorrow";
		else
			day = "in " + option + " Days";
		model.addAttribute("day", day);
		return "air-quality";
	}
	
	
	
}
