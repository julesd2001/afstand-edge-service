package fact.it.afstandedgeservice.controller;

import fact.it.afstandedgeservice.model.Rit;
import fact.it.afstandedgeservice.model.RitAfstand;
import fact.it.afstandedgeservice.model.Vrachtwagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VrachtwagenRitAfstandController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${vrachtwagenservice.baseurl}")
    private String vrachtwagenServiceBaseUrl;

    @Value("${ritservice.baseurl}")
    private String ritServiceBaseUrl;

    @GetMapping("/totaleafstand/nummerplaat/{nummerplaat}")
    public RitAfstand getTotaleAfstandByNummerplaat(@PathVariable String nummerplaat){
        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class, nummerplaat);

        ResponseEntity<List<Rit>> responseEntityRitten =
                restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/{nummerplaat}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rit>>() {
                        }, nummerplaat);

        return new RitAfstand(vrachtwagen,responseEntityRitten.getBody());
    }

    @GetMapping("/totaleafstand/bedrijf/{bedrijf}")
    public List<RitAfstand> getTotaleAfstandByBedrijf(@PathVariable String bedrijf){
        List<RitAfstand> returnList = new ArrayList<>();
        ResponseEntity<List<Vrachtwagen>> responseEntityVrachtwagens =
                restTemplate.exchange("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/bedrijf/{bedrijf}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Vrachtwagen>>() {
                        }, bedrijf);

        List<Vrachtwagen> vrachtwagens = responseEntityVrachtwagens.getBody();
        for (Vrachtwagen vrachtwagen:
             vrachtwagens) {
            ResponseEntity<List<Rit>> responseEntityRitten =
                    restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/{nummerplaat}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Rit>>() {
                            }, vrachtwagen.getNummerplaat());
            returnList.add(new RitAfstand(vrachtwagen, responseEntityRitten.getBody()));
        }

        return returnList;
    }

    @GetMapping("/totaleafstand/nummerplaat/{nummerplaat}/vertrekpunt/{vertrekpunt}")
    public RitAfstand getTotaleAfstandByNummerplaatAndVertrekpunt(@PathVariable String nummerplaat, @PathVariable String vertrekpunt){
        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class, nummerplaat);

        ResponseEntity<List<Rit>> responseEntityRitten =
                restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/" + nummerplaat + "/vertrekpunt/" + vertrekpunt,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rit>>() {
                        });

        return new RitAfstand(vrachtwagen, responseEntityRitten.getBody());
    }

    @GetMapping("/totaleafstand/nummerplaat/{nummerplaat}/bestemming/{bestemming}")
    public RitAfstand getTotaleAfstandByNummerplaatAndBestemming(@PathVariable String nummerplaat, @PathVariable String bestemming){
        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class, nummerplaat);

        ResponseEntity<List<Rit>> responseEntityRitten =
                restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/" + nummerplaat + "/bestemming/" + bestemming,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rit>>() {
                        });

        return new RitAfstand(vrachtwagen, responseEntityRitten.getBody());
    }

    @PostMapping("/totaleafstand")
    public RitAfstand addRit(@RequestParam int ritlengte, @RequestParam String vertrekpunt, @RequestParam String bestemming, @RequestParam int begingewicht,
                             @RequestParam String ritId, @RequestParam String cargoId, @RequestParam String nummerplaat){

        Rit rit =
                restTemplate.postForObject("http://" + ritServiceBaseUrl + "/ritten",
                        new Rit(ritlengte,vertrekpunt,bestemming,begingewicht,ritId,cargoId,nummerplaat), Rit.class);

        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class,nummerplaat);

        return new RitAfstand(vrachtwagen, rit);
    }

    @PutMapping("/totaleafstand")
    public RitAfstand updateRit(@RequestParam int ritlengte, @RequestParam String vertrekpunt, @RequestParam String bestemming, @RequestParam int begingewicht,
                                @RequestParam String ritId, @RequestParam String cargoId, @RequestParam String nummerplaat){

        Rit rit =
                restTemplate.getForObject("http://" + ritServiceBaseUrl + "/ritten/{ritId}",
                        Rit.class, ritId);
        rit.setRitlengte(ritlengte);
        rit.setVertrekpunt(vertrekpunt);
        rit.setBestemming(bestemming);
        rit.setBestemming(bestemming);
        rit.setBegingewicht(begingewicht);
        rit.setCargoId(cargoId);
        rit.setNummerplaat(nummerplaat);
        ResponseEntity<Rit> responseEntityRit =
                restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten",
                        HttpMethod.PUT, new HttpEntity<>(rit), Rit.class);

        Rit retrievedRit = responseEntityRit.getBody();

        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class,nummerplaat);

        return new RitAfstand(vrachtwagen, retrievedRit);
    }

    @DeleteMapping("totaleafstand/{ritId}")
    public ResponseEntity deleteRit(@PathVariable int ritId){

        restTemplate.delete("http://" + ritServiceBaseUrl + "/ritten/{ritId}", ritId);

        return ResponseEntity.ok().build();
    }

}
