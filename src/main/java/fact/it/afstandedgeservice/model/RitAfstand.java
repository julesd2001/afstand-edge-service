package fact.it.afstandedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class RitAfstand {
    private String afstand;
    private String nummerplaat;
    private String bedrijf;
    private String ritId;

    public RitAfstand(Rit rit, Vrachtwagen vrachtwagen) {
        setAfstand(afstand);
        vrachtwagen.setNummerplaat(vrachtwagen.getNummerplaat());
        vrachtwagen.setBedrijf(vrachtwagen.getBedrijf());
        rit.setRitId(rit.getRitId());
    }


    public String getNummerplaat() {
        return nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }

    public String getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getRitId() {
        return ritId;
    }

    public void setRitId(String ritId) {
        this.ritId = ritId;
    }

    public String getAfstand() {
        return afstand;
    }

    public void setAfstand(String afstand) {
        this.afstand = afstand;
    }
}
