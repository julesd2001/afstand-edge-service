package fact.it.afstandedgeservice.model;

public class Rit {
   private int id;
   private String ritId;
   private int ritlengte;
   private String vertrekpunt;
   private String bestemming;
   private String nummerplaat;

   public Rit() {
   }

   public Rit(int id, String ritId, int ritlengte, String vertrekpunt, String bestemming, String nummerplaat) {
      this.id = id;
      this.ritId = ritId;
      this.ritlengte = ritlengte;
      this.vertrekpunt = vertrekpunt;
      this.bestemming = bestemming;
      this.nummerplaat = nummerplaat;
   }

   public String getNummerplaat() {
      return nummerplaat;
   }

   public void setNummerplaat(String nummerplaat) {
      this.nummerplaat = nummerplaat;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
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

   public String getVertrekpunt() {
      return vertrekpunt;
   }

   public void setVertrekpunt(String vertrekpunt) {
      this.vertrekpunt = vertrekpunt;
   }

   public String getBestemming() {
      return bestemming;
   }

   public void setBestemming(String bestemming) {
      this.bestemming = bestemming;
   }
}
