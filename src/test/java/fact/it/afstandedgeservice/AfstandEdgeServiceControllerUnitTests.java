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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.net.URI;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private Vrachtwagen v1 = new Vrachtwagen("1", "Volvo", "FH16", "2021", "1-UAE-451", "Ordina");
    private Vrachtwagen v2 = new Vrachtwagen("2", "Renault", "Vrachtwagen", "2020", "1-RAE-432", "Ordina");
    private Vrachtwagen v3 = new Vrachtwagen("3", "DAF", "Vrachtwagen", "2019", "1-POI-345", "Thomas More");

    private Rit rit1 = new Rit(10,"Kerkstraat 1","Teststraat 1",500,"1","1", "1-UAE-451");
    private Rit rit2 = new Rit(15, "Catersdreef 19", "Beddenstraat 2", 5, "2", "2", "1-RAE-432");
    private Rit rit3 = new Rit(20, "Korte Gasthuisstraat 8", "Bisschoppenhoflaan 571", 20, "3", "3", "1-POI-345");

    private RitAfstand ra1 = new RitAfstand(v1, rit1);
    private RitAfstand ra2 = new RitAfstand(v2, rit2);
    private RitAfstand ra3 = new RitAfstand(v3, rit3);


    @BeforeEach
    public void initializeMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetTotaleAfstandByNummerplaat_thenReturnTotaleAfstandJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1)));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit1)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}", "1-UAE-451"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetTotaleAfstandByBedrijf_thenReturnTotaleAfstandJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/bedrijf/ordina")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit1)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}", "1-UAE-451"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetTotaleAfstandByVertrekpuntAndNummerplaat_thenReturnTotaleAfstandJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451/vertrekpunt/Kerkstraat%201")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit1)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}", "1-UAE-451"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetTotaleAfstandByBestemmingAndNummerplaat_thenReturnTotaleAfstandJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens/nummerplaat/1-UAE-451")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(v1)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaat/1-UAE-451/bestemming/Teststraat%201")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit1)));

        mockMvc.perform(get("/totaleafstand/nummerplaat/{nummerplaat}", "1-UAE-451"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddRitAfstand_thenReturnRitAfstandJson() throws Exception {
        RitAfstand ra4 = new RitAfstand(v2, rit3);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + vrachtwagenServiceBaseUrl + "/vrachtwagens")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(ra4)));
        //?
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + ritServiceBaseUrl + "/ritten/nummerplaten/" + v2.getNummerplaat())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(rit2)));
    }

    @Test
    public void whenUpdateRitAfstand_thenReturnRitAfstandJson() throws Exception {

    }







}
