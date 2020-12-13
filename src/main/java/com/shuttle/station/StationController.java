package com.shuttle.station;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StationController {

    private final StationService stationService;

    @GetMapping("/station")
    public String stationForm(Model model) {
        model.addAttribute("stationList", stationService.findAllStation());
        return "/station/list";
    }

}
