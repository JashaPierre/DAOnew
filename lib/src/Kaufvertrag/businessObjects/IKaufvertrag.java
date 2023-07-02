package businessObjects;
public interface IKaufvertrag {
    IVertragspartner getVerkaeufer();
    void setVerkaeufer(IVertragspartner verkaeufer);
    IVertragspartner getKaeufer();
    void setKaeufer(IVertragspartner kaeufer );
    IWare getWare();
    void setWare(IWare ware);
    String getZahlungsModalitaeten();
    void setZahlungsModalitaeten(String zahlungsModalitaeten);
}
