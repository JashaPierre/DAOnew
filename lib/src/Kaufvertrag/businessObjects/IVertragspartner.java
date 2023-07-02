package businessObjects;

public interface IVertragspartner {
   String getAusweisNr();
   void setAusweisNr(String ausweisNr);
   String getVorname();
   void setVorname(String vorname);
   String getNachname();
   void setNachname(String nachname);
   IAdresse getAdresse();
   void setAdresse(IAdresse adresse);
}
