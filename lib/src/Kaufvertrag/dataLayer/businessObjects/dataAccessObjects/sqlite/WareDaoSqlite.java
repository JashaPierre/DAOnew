package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.Ware;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.ConsoleManager;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WareDaoSqlite implements IDao<IWare, Long> {
    @Override
    public IWare create() {
        ConsoleManager ui = ConsoleManager.getInstance();
        String bezeichnung = ui.returnInput("Geben Sie eine Bezeichnung der Ware ein");
        double preis;
        while (true){
            String preisString = ui.returnInput("Geben Sie einen Preis der Ware ein",
                    "^\\d+(,\\d{1,2})?$",
                    "Keine gültige Eingabe für einen Preis.");
            try{
                preis = Double.parseDouble(preisString);
                break;
            }catch (NumberFormatException e){
                System.out.println("Keine gültige Eingabe für einen Preis.");
            }
        }
        return new Ware(bezeichnung, preis);
    }

    @Override
    public void create(IWare objectToInsert) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement;
        String sql;
        try {

            int id = 0;
            sql = "INSERT INTO Ware (Bezeichnung, Beschreibung, Preis)" +
                    "VALUES (?, ?, ?) RETURNING id";
            statement = connection.prepareStatement(sql);
            statement.setString(1, objectToInsert.getBezeichnung());
            statement.setString(2, objectToInsert.getBeschreibung());
            statement.setDouble(3, objectToInsert.getPreis());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }

            if (!objectToInsert.getBesonderheiten().isEmpty()) {
                sql = "INSERT INTO Besonderheiten (WarenID, Besonderheit)" +
                        "VALUES (?, ?)";
                statement = connection.prepareStatement(sql);

                for(var besonderheit : objectToInsert.getBesonderheiten()) {
                    if(id != 0)
                        statement.setLong(1, id);
                    statement.setString(2, besonderheit);
                    statement.executeUpdate();
                }
            }

            if (!objectToInsert.getMaengel().isEmpty()) {
                sql = "INSERT INTO Maengel (WarenID, Mangel)" +
                        "VALUES (?, ?)";
                statement = connection.prepareStatement(sql);

                for(var mangel : objectToInsert.getMaengel()) {
                    if(id != 0)
                        statement.setLong(1, id);
                    statement.setString(2, mangel);
                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IWare read(Long id) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        try{
            String sql =
                    "SELECT Ware.id, Ware.Bezeichnung, Ware.Beschreibung, Ware.Preis, " +
                    "B.Besonderheit, M.Mangel " +
                    "FROM Ware " +
                    "LEFT JOIN Besonderheiten B " +
                    "ON Ware.id = B.WarenID " +
                    "LEFT JOIN Maengel M " +
                    "on Ware.id = M.WarenID " +
                    "WHERE Ware.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return parseSQLtoWare(rs);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IWare> readAll() throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        List<IWare> warenListe = new ArrayList<>();
        try {
            String sql = "SELECT id FROM Ware";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                IWare ware = read(rs.getLong("id"));
                warenListe.add(ware);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warenListe;
    }

    @Override
    public void update(IWare objectToUpdate) throws DaoException {
        Connection connection = ConnectionManager.getConnection();
        ConsoleManager ui = ConsoleManager.getInstance();

        String sql;
        PreparedStatement statement;

        try{
            long id = objectToUpdate.getId();
            ui.updateWareUI(objectToUpdate);

            if(id != 0){
                sql =
                    "UPDATE Ware " +
                    "SET Bezeichnung = ?, Beschreibung = ?, Preis = ? " +
                    "WHERE id = ?;";

                statement = connection.prepareStatement(sql);
                statement.setLong(4, id);
                statement.setString(1, objectToUpdate.getBezeichnung());
                statement.setString(2, objectToUpdate.getBeschreibung());
                statement.setDouble(3, objectToUpdate.getPreis());
                statement.executeUpdate();

                sql =
                        "UPDATE Besonderheiten " +
                        "SET Besonderheit = ? " +
                        "WHERE WarenID = ?;";

                statement = connection.prepareStatement(sql);
                statement.setLong(2, id);
                for(var besonderheit : objectToUpdate.getBesonderheiten()){
                    statement.setString(1, besonderheit);
                }
                statement.executeUpdate();

                sql =
                        "UPDATE Maengel " +
                        "SET Mangel = ? " +
                        "WHERE WarenID = ?;";

                statement.setLong(2, id);
                statement = connection.prepareStatement(sql);
                for(var mangel : objectToUpdate.getMaengel()){
                    statement.setString(1, mangel);
                }
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            //Besondeheiten
            String sql =
                "DELETE FROM Besonderheiten " +
                "WHERE WarenID = (SELECT id FROM Ware WHERE id = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

            //Besondeheiten
            sql =
                "DELETE FROM Maengel " +
                "WHERE WarenID = (SELECT id FROM Ware WHERE id = ?);";

            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

            sql =
                    "DELETE FROM Ware " +
                    "WHERE id = ?; ";

            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

//            String resetQuery = "DELETE FROM sqlite_sequence " +
//                                "WHERE name is 'Vertragspartner' or name is 'Adresse'";
//            statement.executeUpdate(resetQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IWare parseSQLtoWare(ResultSet rs){
        Ware ware = null;

        Long id = null;
        String bezeichnung = "";
        String beschreibung = "";
        double preis = 0;

        List<String> besonderheiten = new ArrayList<>();
        List<String> maengel = new ArrayList<>();

        try {
            while (rs.next()) {
                id = rs.getLong(1);
                bezeichnung = rs.getString(2);
                beschreibung = rs.getString(3);
                preis = rs.getDouble(4);

                String besonderheit = rs.getString(5);
                if(besonderheit != null){
                    besonderheiten.add(besonderheit);
                }
                String mangel = rs.getString(6);
                if(mangel != null){
                    maengel.add(mangel);
                }
            }

            if(!bezeichnung.isBlank() && preis != 0) {
                ware = new Ware(bezeichnung, preis);
            }
            if(ware != null && id != 0){
                ware.setId(id);
            }
            if(ware != null && !beschreibung.isBlank()){
                ware.setBeschreibung(beschreibung);
            }
            if(ware != null && !besonderheiten.isEmpty()){
                ware.getBesonderheiten().addAll(besonderheiten);
            }
            if(ware != null && !maengel.isEmpty()){
                ware.getMaengel().addAll(maengel);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ware;
    }
}
