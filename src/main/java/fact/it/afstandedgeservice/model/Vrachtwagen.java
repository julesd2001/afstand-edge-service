package fact.it.afstandedgeservice.model;

public class Vrachtwagen {
    private String id;
    private String nummerplaat, merk, model, bouwjaar, bedrijf;

    public Vrachtwagen() {
    }

    public Vrachtwagen(String id, String nummerplaat, String merk, String model, String bouwjaar, String bedrijf) {
        this.id = id;
        this.nummerplaat = nummerplaat;
        this.merk = merk;
        this.model = model;
        this.bouwjaar = bouwjaar;
        this.bedrijf = bedrijf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNummerplaat() {
        return nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
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

    public String getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }
}
