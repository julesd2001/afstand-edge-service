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

    @Value("${vrachtwagenservice.baseurl}")
    private String vrachtwagenServiceBaseUrl;

    @Value("${ritservice.baseurl}")
    private String ritServiceBaseUrl;

//    @GetMapping("/vrachtwagens/nummerplaat/{nummerplaat}/ritten")
//    public List<RitAfstand> getRitAfstandForVrachtwagenByNummerplaat(@PathVariable String nummerplaat) {
//
//        List<RitAfstand> returnList = new ArrayList();
//
//        ResponseEntity<List<Vrachtwagen>> responseEntityVrachtwagens =
//            restTemplate.exchange("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
//                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Vrachtwagen>>() {
//                    }, nummerplaat);
//
//        List<Vrachtwagen> vrachtwagens = responseEntityVrachtwagens.getBody();
//
//        for (Vrachtwagen vrachtwagen :
//                vrachtwagens) {
//            Rit rit =
//                    restTemplate.getForObject("http://" + ritServiceBaseUrl + "/ritten/{nummerplaat}",
//                            Rit.class, vrachtwagen.getNummerplaat());
//
//            returnList.add(new RitAfstand(rit, vrachtwagen));
//        }
//
//        return returnList;
//    }

    @GetMapping("/ritten/nummerplaat/{nummerplaat}")
    public RitAfstand getRittenByNummerplaat(@PathVariable String nummerplaat){
        Vrachtwagen vrachtwagen =
                restTemplate.getForObject("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/{nummerplaat}",
                        Vrachtwagen.class, nummerplaat);

        ResponseEntity<List<Rit>> responseEntityRitten =
                restTemplate.exchange("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/{nummerplaat}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rit>>() {
                        }, nummerplaat);

        return new RitAfstand(vrachtwagen,responseEntityRitten.getBody());
    }



    //totaal afstand voor vrachtwagens van een bepaald bedrijf
    //kijken hoeveel elk bedrijf heeft gereden, en per nummerplaat/vrachtwagen






}
