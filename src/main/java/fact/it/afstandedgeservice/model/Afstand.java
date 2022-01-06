package fact.it.afstandedgeservice.model;

public class Afstand {
    private String ritId;
    private int ritlengte;

    public Afstand(String ritId, int ritlengte) {
        this.ritId = ritId;
        this.ritlengte = ritlengte;
    }

    public String getRitId() {
        return ritId;
    }

    public void setRitId(String ritId) {
        this.ritId = ritId;
    }

    public int getRitlengte() {
        return ritlengte;
    }

    public void setRitlengte(int ritlengte) {
        this.ritlengte = ritlengte;
    }
}
