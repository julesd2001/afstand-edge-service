package fact.it.afstandedgeservice.controller;

import fact.it.afstandedgeservice.model.RitAfstand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class VrachtwagenRitAfstandController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${vrachtwagenservice.baseurl}")
    private String vrachtwagenServiceBaseUrl;

    @Value("${ritservice.baseurl}")
    private String ritServiceBaseUrl;

    @GetMapping("/ritafstand/{vrachtwagenId}/rit/{ritId}")
    public RitAfstand getTotalRitAfstand(@PathVariable String vrachtwagenId, @PathVariable Integer ritId) {

    }



}
