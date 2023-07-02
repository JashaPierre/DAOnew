package Kaufvertrag.Interfaces;

    public interface IAdresse {
        String getStrasse();
        void setStrasse(String strasse);
        String getHausNr();
        void setHausNr(String hausNr);
        String getPlz();
        void setPlz(String plz);
        String getOrt();
        void setOrt(String ort);
        String toString();
    }
