package fact.it.afstandedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class RitAfstand {
    private List<Afstand> afstand;
    private String totaleAfstand;
    private String nummerplaat;
    private String bedrijf;
//    private String ritId;

    public RitAfstand(Vrachtwagen vrachtwagen, List<Rit> ritten) {
        afstand = new ArrayList<>();
        ritten.forEach(rit -> {
            afstand.add(new Afstand(rit.getRitId(),
                    rit.getRitlengte()));
        });
        setAfstand(afstand);
        setNummerplaat(vrachtwagen.getNummerplaat());
        setBedrijf(vrachtwagen.getBedrijf());
    }

    public RitAfstand(Vrachtwagen vrachtwagen, Rit rit) {
        afstand = new ArrayList<>();
        afstand.add(new Afstand(rit.getRitId(), rit.getRitlengte()));
        setAfstand(afstand);
        setNummerplaat(vrachtwagen.getNummerplaat());
        setBedrijf(vrachtwagen.getBedrijf());
    }

    public List<Afstand> getAfstand() {
        return afstand;
    }

    public void setAfstand(List<Afstand> afstand) {
        this.afstand = afstand;
    }

    public String getTotaleAfstand() {
        return totaleAfstand;
    }

    public void setTotaleAfstand(String totaleAfstand) {
        this.totaleAfstand = totaleAfstand;
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

//    public String getRitId() {
//        return ritId;
//    }
//
//    public void setRitId(String ritId) {
//        this.ritId = ritId;
//    }

}
