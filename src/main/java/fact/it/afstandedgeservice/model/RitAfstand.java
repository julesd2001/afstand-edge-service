package fact.it.afstandedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class RitAfstand {
    private List<Afstand> afstand;
    private int totaleAfstand;
    private String nummerplaat;
    private String bedrijf;
    private String merk;
    private String model;
    private String bouwjaar;

    public RitAfstand(Vrachtwagen vrachtwagen, List<Rit> ritten) {
        afstand = new ArrayList<>();
        ritten.forEach(rit -> {
            afstand.add(new Afstand(rit.getRitId(),
                    rit.getRitlengte()));
        });
        setAfstand(afstand);
        for (Rit rit : ritten) {
                totaleAfstand += rit.getRitlengte();
        }
        setTotaleAfstand(totaleAfstand);
        setNummerplaat(vrachtwagen.getNummerplaat());
        setBedrijf(vrachtwagen.getBedrijf());
        setMerk(vrachtwagen.getMerk());
        setModel(vrachtwagen.getModel());
        setBouwjaar(vrachtwagen.getBouwjaar());
    }

    public RitAfstand(Vrachtwagen vrachtwagen, Rit rit) {
        afstand = new ArrayList<>();
        afstand.add(new Afstand(rit.getRitId(), rit.getRitlengte()));
        setAfstand(afstand);
        totaleAfstand += rit.getRitlengte();
        setTotaleAfstand(totaleAfstand);
        setNummerplaat(vrachtwagen.getNummerplaat());
        setBedrijf(vrachtwagen.getBedrijf());
        setMerk(vrachtwagen.getMerk());
        setModel(vrachtwagen.getModel());
        setBouwjaar(vrachtwagen.getBouwjaar());
    }

    public List<Afstand> getAfstand() {
        return afstand;
    }

    public void setAfstand(List<Afstand> afstand) {
        this.afstand = afstand;
    }

    public int getTotaleAfstand() {
        return totaleAfstand;
    }

    public void setTotaleAfstand(int totaleAfstand) {
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

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBouwjaar() {
        return bouwjaar;
    }

    public void setBouwjaar(String bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

}
