package mtech.irs.ekgs.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtech.irs.ekgs.app.AppSettings;
import mtech.irs.ekgs.app.AppStatus;
import mtech.irs.ekgs.util.AppContextUtils;

@RestController
@RequestMapping("/app")
public class AppController {
	
	@GetMapping("/status")
	public AppStatus getStatus() {
		return new AppStatus().load();
	}

	@GetMapping("/settings")
	public AppSettings getSettings() {
		return AppContextUtils.getBean(AppSettings.class);
	}
	
	@PostMapping("/settings")
	public AppSettings mergeSettings(@RequestBody AppSettings newSettings) {
		AppSettings settings = AppContextUtils.getBean(AppSettings.class);
		settings.merge(newSettings);
		return settings;
		
	}
}
