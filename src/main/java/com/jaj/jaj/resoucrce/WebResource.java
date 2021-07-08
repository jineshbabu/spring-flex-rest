package com.jaj.jaj.resoucrce;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebResource {


    @GetMapping("/about")
    public String getAboutPage() {
        return "WebFlux OAuth example";
    }
}
