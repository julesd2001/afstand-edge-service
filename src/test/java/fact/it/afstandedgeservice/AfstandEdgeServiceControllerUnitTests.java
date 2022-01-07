package fact.it.afstandedgeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.afstandedgeservice.model.Rit;
import fact.it.afstandedgeservice.model.RitAfstand;
import fact.it.afstandedgeservice.model.Vrachtwagen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AfstandEdgeServiceControllerUnitTests {
    @Value("${vrachtwagenservice.baseurl}")
    private String vrachtwagenServiceBaseUrl;

    @Value("${ritservice.baseurl}")
    private String ritServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Vrachtwagen v1 = new Vrachtwagen("1", "1-UAE-451", "Volvo", "FH16", "2021", "Ordina");
    private Vrachtwagen v2 = new Vrachtwagen("2", "1-RAE-432", "Renault", "Vrachtwagen", "2020", "Ordina");
    private Vrachtwagen v3 = new Vrachtwagen("3", "1-POI-345", "DAF", "Vrachtwagen", "2019", "Thomas More");

    private Rit rit1 = new Rit(10,"Kerkstraat 1","Teststraat 1",500,"1","1", "1-UAE-451");
    private Rit rit2 = new Rit(15, "Catersdreef 19", "Beddenstraat 2", 5, "2", "2", "1-UAE-451");
    private Rit rit3 = new Rit(20, "Korte Gasthuisstraat 8", "Bisschoppenhoflaan 571", 20, "3", "3", "1-RAE-432");

    private List<Vrachtwagen> allVrachtwagensOfBedrijfOrdina = Arrays.asList(v1, v2);

    private List<Rit> allRittenFromV1 = Arrays.asList(rit1, rit2);
    private List<Rit> allRittenFromV2 = Arrays.asList(rit3);


    @BeforeEach
    public void initializeMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetTotaleAfstandByNummerplaat_thenReturnTotaleAfstandJson() throws Exception {
        // GET vrachtwagen 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1))
                );

        // GET all ritten of Vrachtwagen 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRittenFromV1))
                );

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}", "1-UAE-451"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.afstand[0].ritId", is("1")))
                .andExpect(jsonPath("$.afstand[0].ritlengte", is(10)))
                .andExpect(jsonPath("$.afstand[1].ritId", is("2")))
                .andExpect(jsonPath("$.afstand[1].ritlengte", is(15)))
                .andExpect(jsonPath("$.totaleAfstand", is(25)))
                .andExpect(jsonPath("$.nummerplaat", is("1-UAE-451")))
                .andExpect(jsonPath("$.bedrijf", is("Ordina")))
                .andExpect(jsonPath("$.merk", is("Volvo")))
                .andExpect(jsonPath("$.model", is("FH16")))
                .andExpect(jsonPath("$.bouwjaar", is("2021")));
    }

    @Test
    public void whenGetTotaleAfstandByBedrijf_thenReturnTotaleAfstandJson() throws Exception {

        // GET all vrachtwagens from Bedrijf Ordina
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/bedrijf/Ordina")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allVrachtwagensOfBedrijfOrdina)));

        //GET rit of nummerplaat 1-UAE-451
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRittenFromV1)));

        //GET rit of nummerplaat 1-RAE-432
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-RAE-432")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRittenFromV2)));

        mockMvc.perform(get("/totaleafstand/bedrijf/{bedrijf}", "Ordina"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].afstand[0].ritId", is("1")))
                .andExpect(jsonPath("$[0].afstand[0].ritlengte", is(10)))
                .andExpect(jsonPath("$[0].afstand[1].ritId", is("2")))
                .andExpect(jsonPath("$[0].afstand[1].ritlengte", is(15)))
                .andExpect(jsonPath("$[0].totaleAfstand", is(25)))
                .andExpect(jsonPath("$[0].nummerplaat", is("1-UAE-451")))
                .andExpect(jsonPath("$[0].bedrijf", is("Ordina")))
                .andExpect(jsonPath("$[0].merk", is("Volvo")))
                .andExpect(jsonPath("$[0].model", is("FH16")))
                .andExpect(jsonPath("$[0].bouwjaar", is("2021")))
                .andExpect(jsonPath("$[1].afstand[0].ritId", is("3")))
                .andExpect(jsonPath("$[1].afstand[0].ritlengte", is(20)))
                .andExpect(jsonPath("$[1].totaleAfstand", is(20)))
                .andExpect(jsonPath("$[1].nummerplaat", is("1-RAE-432")))
                .andExpect(jsonPath("$[1].bedrijf", is("Ordina")))
                .andExpect(jsonPath("$[1].merk", is("Renault")))
                .andExpect(jsonPath("$[1].model", is("Vrachtwagen")))
                .andExpect(jsonPath("$[1].bouwjaar", is("2020")));
    }

    @Test
    public void whenGetTotaleAfstandByNummerplaatAndVertrekpunt_thenReturnTotaleAfstandJson() throws Exception {

        //GET Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-RAE-432")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v2)));

        //GET rit from Vertrekpunt Korte Gasthuisstraat 8 of Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-RAE-432/vertrekpunt/Korte%20Gasthuisstraat%208")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRittenFromV2)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}/vertrekpunt/{vertrekpunt}", "1-RAE-432", "Korte Gasthuisstraat 8"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.afstand[0].ritId", is("3")))
                .andExpect(jsonPath("$.afstand[0].ritlengte", is(20)))
                .andExpect(jsonPath("$.totaleAfstand", is(20)))
                .andExpect(jsonPath("$.nummerplaat", is("1-RAE-432")))
                .andExpect(jsonPath("$.bedrijf", is("Ordina")))
                .andExpect(jsonPath("$.merk", is("Renault")))
                .andExpect(jsonPath("$.model", is("Vrachtwagen")))
                .andExpect(jsonPath("$.bouwjaar", is("2020")));
    }

    @Test
    public void whenGetTotaleAfstandByNummerplaatAndBestemming_thenReturnTotaleAfstandJson() throws Exception {

        //GET Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-RAE-432")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v2)));

        //GET rit from Bestemming Bisschoppenhoflaan 571 of Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-RAE-432/bestemming/Bisschoppenhoflaan%20571")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRittenFromV2)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}/bestemming/{bestemming}", "1-RAE-432", "Bisschoppenhoflaan 571"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.afstand[0].ritId", is("3")))
                .andExpect(jsonPath("$.afstand[0].ritlengte", is(20)))
                .andExpect(jsonPath("$.totaleAfstand", is(20)))
                .andExpect(jsonPath("$.nummerplaat", is("1-RAE-432")))
                .andExpect(jsonPath("$.bedrijf", is("Ordina")))
                .andExpect(jsonPath("$.merk", is("Renault")))
                .andExpect(jsonPath("$.model", is("Vrachtwagen")))
                .andExpect(jsonPath("$.bouwjaar", is("2020")));
    }

    @Test
    public void whenAddRit_thenReturnRitJson() throws Exception {

        Rit rit4 = new Rit(50, "Karel de Grootlaan 12", "Hoogstraat 8", 5, "4", "4", "1-RAE-432");

        // POST rit for Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit4))
                );

        // GET Vrachtwagen 2
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-RAE-432")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v2))
                );

        mockMvc.perform(post("/totaleafstand")
                .param("ritlengte", ""+rit4.getRitlengte())
                .param("vertrekpunt", rit4.getVertrekpunt())
                .param("bestemming", rit4.getBestemming())
                .param("begingewicht", ""+rit4.getBegingewicht())
                .param("ritId", rit4.getRitId())
                .param("cargoId", rit4.getCargoId())
                .param("nummerplaat", rit4.getNummerplaat())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.afstand[0].ritId", is("4")))
                .andExpect(jsonPath("$.afstand[0].ritlengte", is(50)))
                .andExpect(jsonPath("$.totaleAfstand", is(50)))
                .andExpect(jsonPath("$.nummerplaat", is("1-RAE-432")))
                .andExpect(jsonPath("$.bedrijf", is("Ordina")))
                .andExpect(jsonPath("$.merk", is("Renault")))
                .andExpect(jsonPath("$.model", is("Vrachtwagen")))
                .andExpect(jsonPath("$.bouwjaar", is("2020")));
    }

    @Test
    public void whenUpdateRit_thenReturnRitJson() throws Exception {
        Rit updatedRit1 = new Rit(35,"Kerkstraat 1","Teststraat 25",1000,"1","1", "1-UAE-451");

        // GET rit 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit1))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedRit1))
                );

        // GET vrachtwagen 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1))
                );

        mockMvc.perform(put("/totaleafstand")
                        .param("ritlengte", ""+updatedRit1.getRitlengte())
                        .param("vertrekpunt", updatedRit1.getVertrekpunt())
                        .param("bestemming", updatedRit1.getBestemming())
                        .param("begingewicht", ""+updatedRit1.getBegingewicht())
                        .param("ritId", updatedRit1.getRitId())
                        .param("cargoId", updatedRit1.getCargoId())
                        .param("nummerplaat", updatedRit1.getNummerplaat())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.afstand[0].ritId", is("1")))
                .andExpect(jsonPath("$.afstand[0].ritlengte", is(35)))
                .andExpect(jsonPath("$.totaleAfstand", is(35)))
                .andExpect(jsonPath("$.nummerplaat", is("1-UAE-451")))
                .andExpect(jsonPath("$.bedrijf", is("Ordina")))
                .andExpect(jsonPath("$.merk", is("Volvo")))
                .andExpect(jsonPath("$.model", is("FH16")))
                .andExpect(jsonPath("$.bouwjaar", is("2021")));
    }

    @Test
    public void whenDeleteRit_thenReturnStatusOk() throws Exception {

        //DELETE rit from ritId 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/1")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/totaleafstand/{ritId}", "1"))
                .andExpect(status().isOk());
    }





}
