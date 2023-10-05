package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.Adresse;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VertragspartnerDaoSqlite implements IDao<IVertragspartner, String> { //calls connectManager

    //Das Vertragspartner Object bräuchte dringend auch ein ID feld.
    //Hier eingebaut, weil es im Plan keine genauen vorgaben zu dieser Klasse gibt.
    public Map<IVertragspartner, String> idStore = new HashMap<>();
    @Override
    public IVertragspartner create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String vorname = ui.returnInput(
                "Wie lautet der Vorname des Vertragspartners?"
        );
        String nachname = ui.returnInput(
                "Wie lautet der Nachname des Vertragspartners?"
        );
        return new Vertragspartner(vorname, nachname);
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement;
        String sql;
        try {
            int adresseId = 0;
            if (objectToInsert.getAdresse() != null) {
                sql =
                    "INSERT INTO Adresse (Strasse, HausNr, PLZ, Ort)" +
                    "VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, objectToInsert.getAdresse().getStrasse());
                preparedStatement.setString(2, objectToInsert.getAdresse().getHausNr());
                preparedStatement.setString(3, objectToInsert.getAdresse().getPlz());
                preparedStatement.setString(4, objectToInsert.getAdresse().getOrt());
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    adresseId = generatedKeys.getInt(1);
                }
            }

            sql =
                "INSERT INTO Vertragspartner (Vorname, Nachname, AusweisNr, Adresse)" +
                "VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, objectToInsert.getVorname());
            preparedStatement.setString(2, objectToInsert.getNachname());
            preparedStatement.setString(3, objectToInsert.getAusweisNr());
            if (adresseId != 0)
                preparedStatement.setInt(4, adresseId);
            else
                preparedStatement.setNull(4, Types.INTEGER);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IVertragspartner read(String id) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        try{
            String sql =
                "SELECT Vertragspartner.Vorname, Vertragspartner.Nachname, Vertragspartner.AusweisNr, " +
                "Adresse.Strasse, Adresse.HausNr, Adresse.PLZ, Adresse.Ort " +
                "FROM Vertragspartner " +
                "LEFT JOIN Adresse " +
                "ON Vertragspartner.Adresse = Adresse.id " +
                "WHERE Vertragspartner.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            return parseSQLToPartner(rs);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IVertragspartner> readAll() throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        List<IVertragspartner> parterList = new ArrayList<>();
        idStore.clear();
        try {
            String sql = "SELECT id FROM Vertragspartner";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                IVertragspartner partner = read(rs.getString("id"));
                parterList.add(partner);
                idStore.put(partner, rs.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return parterList;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        ConsoleManager ui = ConsoleManager.getInstance();
        String sql;
        PreparedStatement statement;

        try {
            //GET ID
            String id = idStore.get(objectToUpdate);
            /*sql =
                "SELECT Vertragspartner.id " +
                "FROM Vertragspartner " +
                "LEFT JOIN Adresse " +
                "ON Vertragspartner.Adresse = Adresse.id " +
                "WHERE Vertragspartner.Vorname=? AND Vertragspartner.Nachname=? AND Vertragspartner.AusweisNr=? AND " +
                "Adresse.Strasse=? AND Adresse.HausNr=? AND Adresse.PLZ=? AND Adresse.Ort=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, objectToUpdate.getVorname());
            statement.setString(2, objectToUpdate.getNachname());
            statement.setString(3, objectToUpdate.getAusweisNr());

            if(objectToUpdate.getAdresse() != null) {
                statement.setString(4, objectToUpdate.getAdresse().getStrasse());
                statement.setString(5, objectToUpdate.getAdresse().getHausNr());
                statement.setString(6, objectToUpdate.getAdresse().getPlz());
                statement.setString(7, objectToUpdate.getAdresse().getOrt());
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getString(1);
            }*/

            ui.updateVertragspartnerUI(objectToUpdate);

            //Updaten des Vertragspartners in der Database
            if(id != null && !id.isEmpty()) {
                sql =
                    "UPDATE Vertragspartner " +
                    "LEFT JOIN Adresse ON Vertragspartner.Adresse = Adresse.id " +
                    "SET Vertragspartner.Vorname=?, Vertragspartner.Nachname=?, Vertragspartner.AusweisNr=?, " +
                    "Adresse.Strasse=?, Adresse.HausNr=?, Adresse.PLZ=?, Adresse.Ort=? " +
                    "WHERE Vertragspartner.id=?";

                statement = connection.prepareStatement(sql);
                statement.setString(8, id);
                statement.setString(1, objectToUpdate.getVorname());
                statement.setString(2, objectToUpdate.getNachname());
                statement.setString(3, objectToUpdate.getAusweisNr());

                if (objectToUpdate.getAdresse() != null) {
                    statement.setString(4, objectToUpdate.getAdresse().getStrasse());
                    statement.setString(5, objectToUpdate.getAdresse().getHausNr());
                    statement.setString(6, objectToUpdate.getAdresse().getPlz());
                    statement.setString(7, objectToUpdate.getAdresse().getOrt());
                }
                statement.executeUpdate();
            }
            else {
                System.err.println("ID für: "+ objectToUpdate +" konnte nicht gefunden werden.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(String id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql =
                "DELETE FROM Adresse " +
                "WHERE id = (SELECT Adresse FROM Vertragspartner WHERE id = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();

            sql =
                 "DELETE FROM Vertragspartner " +
                 "WHERE id = ?; ";

            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();

//            String resetQuery = "DELETE FROM sqlite_sequence " +
//                                "WHERE name is 'Vertragspartner' or name is 'Adresse'";
//            statement.executeUpdate(resetQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IVertragspartner parseSQLToPartner(ResultSet rs){
        IVertragspartner partner = null;
        Adresse adresse = null;

        String vorname = "";
        String nachname = "";
        String ausweisNr = "";
        String adresse_id = "";

        String strasse = "";
        String hausNr = "";
        String plz = "";
        String ort = "";

        try {
            while (rs.next()) {
                vorname = rs.getString(1);
                nachname = rs.getString(2);
                ausweisNr = rs.getString(3);
                adresse_id = rs.getString(4);

                if(adresse_id != null && !adresse_id.isBlank()){
                    strasse = rs.getString(1);
                    hausNr = rs.getString(2);
                    plz = rs.getString(3);
                    ort = rs.getString(4);
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!vorname.isBlank() && !nachname.isBlank()) {
            partner = new Vertragspartner(vorname, nachname);
        }
        if(partner != null && !ausweisNr.isBlank())
            partner.setAusweisNr(ausweisNr);

        if(partner != null && !ausweisNr.isBlank()) {
            adresse = new Adresse(strasse, hausNr, plz, ort);
            partner.setAdresse(adresse);
        }

        return partner;
    }
}