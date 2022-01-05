package fact.it.afstandedgeservice.controller;

import fact.it.afstandedgeservice.model.Rit;
import fact.it.afstandedgeservice.model.RitAfstand;
import fact.it.afstandedgeservice.model.Vrachtwagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VrachtwagenRitAfstandController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${VRACHTWAGEN_SERVICE_BASEURL:localhost:8052}")
    private String vrachtwagenServiceBaseUrl;

    @Value("${RIT_SERVICE_BASEURL:localhost:8051}")
    private String ritServiceBaseUrl;

    @GetMapping("/vrachtwagens/nummerplaat/{nummerplaat}/ritten")
    public List<RitAfstand> getRitAfstandForVrachtwagenPerBedrijfPerVrachtwagen(@PathVariable String nummerplaat) {
        List<RitAfstand> returnList = new ArrayList<>();
        ResponseEntity<List<Vrachtwagen>> responseEntityVrachtwagens =
            restTemplate.exchange("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vrachtwagen>>() {
            }, nummerplaat);

        List<Vrachtwagen> vrachtwagens = responseEntityVrachtwagens.getBody();
        for (Vrachtwagen vrachtwagen : vrachtwagens) {
            Rit rit = restTemplate.getForObject("http://" + ritServiceBaseUrl + "/ritten/{nummerplaat}", Rit.class, vrachtwagen.getNummerplaat());

            returnList.add(new RitAfstand(rit, vrachtwagen));
        }

        return returnList;
    }



    //totaal afstand voor vrachtwagens van een bepaald bedrijf
    //kijken hoeveel elk bedrijf heeft gereden, en per nummerplaat/vrachtwagen






}
