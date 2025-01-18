package com.smanis.coffee.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Singleton service which encapsulates and deals with everything JDBC
 * Connection and SQL-related.
 *
 * @author semanis
 */
public final class DataService {

    private static DataService INSTANCE;
    private Connection conn = null;
    private static String dbLogin;
    private static String dbPassword;

    /**
     * Singleton's can't be manually constructed, so constructor is private.s
     */
    private DataService() {
    }

    /**
     * Primary method for getting the Singleton instance of the DataService.
     *
     * @return The Singleton instance of DataService.
     */
    public static DataService getInstance() {
        // IF the local, private copy is null, create the service and cache it
        // locally for all future calls.
        if (INSTANCE == null) {
            INSTANCE = new DataService();
        }

        return INSTANCE;
    }

    /**
     * Persists the database credentials so that they're later available for use
     * in the {@link #getConnection() getConnection} method.
     *
     * @param username
     * @param password
     */
    public void setCredentials(String username, String password) {
        DataService.dbLogin = username;
        DataService.dbPassword = password;
    }

    /**
     * Gets a Connection to the database, noting that the Connection object is
     * also cached as a Singleton in this class.
     *
     * @return A JDBC Connection object.
     *
     * @throws Exception If an error occurs when creating a Connection.
     */
    public Connection getConnection() throws Exception {
        if (this.conn == null) {
            this.conn = DriverManager.getConnection("jdbc:mariadb://192.168.0.254:3306/Coffee", DataService.dbLogin, DataService.dbPassword);
        }

        return this.conn;
    }

    public void deleteBean(String beanId) throws Exception {
        String sql = this.getSql("deleteBeanById");

        PreparedStatement ps = this.getConnection().prepareStatement(sql);
        ps.setString(1, beanId);
        ps.execute();
        ps.close();
    }

    public void deleteRoastLog(String roastLogId) throws Exception {
        String sql = this.getSql("deleteRoastLogById");

        PreparedStatement ps = this.getConnection().prepareStatement(sql);
        ps.setString(1, roastLogId);
        ps.execute();
        ps.close();
    }

    public ResultSet doQuery(String queryName) throws Exception {
        Statement st = this.getConnection().createStatement();
        String query = this.getSql(queryName);
        ResultSet rs = st.executeQuery(query);
        st.close();

        return rs;
    }

    public ResultSet getBeanById(String beanId) throws Exception {
        String sql = this.getSql("getBeanById");

        PreparedStatement ps = this.getConnection().prepareStatement(sql);
        ps.setString(1, beanId);

        ResultSet rs = ps.executeQuery();
        ps.close();

        return rs;
    }

    /**
     * Returns the total number of Beans and the total number of In Stock beans.
     
     * @return A ResultSet.
     */
    public ResultSet getBeanCounts() throws Exception {
        String sql = this.getSql("getBeanCounts");
        Statement st = this.getConnection().createStatement();
        
        ResultSet rs = st.executeQuery(sql);
        st.close();
        
        return rs;
        
    }
    
    // Used to the populate the Bean combobox in the RoastLost editing view.
    public ResultSet getBeanIdsAndNames() throws Exception {
        Statement st = this.getConnection().createStatement();
        String query = this.getSql("getBeanIdsAndNames");
        ResultSet rs = st.executeQuery(query);
        st.close();

        return rs;
    }

    public ResultSet getBeans() throws Exception {
        Statement st = this.getConnection().createStatement();
        String query = this.getSql("getBeans");
        ResultSet rs = st.executeQuery(query);
        st.close();

        return rs;
    }

    public ResultSet getRoastLogById(String roastLogId) throws Exception {
        String query = this.getSql("getRoastLogById");
        PreparedStatement ps = this.getConnection().prepareStatement(query);
        ps.setString(1, roastLogId);
        ResultSet rs = ps.executeQuery();
        ps.close();

        return rs;
    }

    public ResultSet getRoastLogs() throws Exception {
        Statement st = this.getConnection().createStatement();
        String query = this.getSql("getRoastLogs");
        ResultSet rs = st.executeQuery(query);
        st.close();

        return rs;
    }

    public ResultSet getRoastLogsByBeanId(String beanId) throws Exception {
        String query = this.getSql("getRoastLogsByBeanId");
        PreparedStatement ps = this.getConnection().prepareStatement(query);
        ps.setString(1, beanId);
        ResultSet rs = ps.executeQuery();
        ps.close();

        return rs;
    }

    public String insertBean(HashMap map) throws Exception {
        String query = this.getSql("insertBean");
        PreparedStatement ps = this.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, (String) map.get("Name"));
        ps.setString(2, (String) map.get("Vendor"));
        ps.setString(3, (String) map.get("ProcessMethod"));
        ps.setFloat(4, (float) map.get("Price"));
        ps.setInt(5, (int)map.get("WeightInPounds"));
        ps.setString(6, (String) map.get("Origin"));
        ps.setString(7, (String) map.get("Variety"));
        ps.setString(8, (String) map.get("Altitude"));
        ps.setFloat(9, (float) map.get("DensityGrams"));
        ps.setInt(10, (int) map.get("Anaerobic"));
        ps.setInt(11, (int) map.get("InStock"));
        ps.setString(12, (String) map.get("GrindSetting"));
        ps.setString(13, (String) map.get("Comments"));

        int insertCount = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        
        String newRecordId = null;
        
        if (rs.next()) {
            newRecordId = rs.getString(0);
        }
        
        ps.close();
        
        return newRecordId;        
    }
                
                
    public void updateBean(HashMap map) throws Exception {
        String query = this.getSql("updateBean");
        PreparedStatement ps = this.getConnection().prepareStatement(query);

        ps.setString(1, (String) map.get("Name"));
        ps.setString(2, (String) map.get("Vendor"));
        ps.setString(3, (String) map.get("ProcessMethod"));
        ps.setFloat(4, (float) map.get("Price"));

        Integer weight = (Integer)map.get("WeightInPounds");
        if (weight == null) {
            weight = Integer.valueOf(0);
        }
        ps.setInt(5, weight);
        
        ps.setString(6, (String) map.get("Origin"));
        ps.setString(7, (String) map.get("Variety"));
        ps.setString(8, (String) map.get("Altitude"));
        ps.setFloat(9, (float) map.get("DensityGrams"));
        ps.setInt(10, (int) map.get("Anaerobic"));
        ps.setInt(11, (int) map.get("InStock"));
        ps.setString(12, (String) map.get("GrindSetting"));
        ps.setString(13, (String) map.get("Comments"));
        ps.setString(14, (String)map.get("Id"));

        int updateCount = ps.executeUpdate();

        ps.close();
    }

    public void insertRoastLog(HashMap map) throws Exception {
        String query = this.getSql("insertRoastLog");
        PreparedStatement ps = this.getConnection().prepareStatement(query);
        ps.setString(1, (String) map.get("BeanId"));
        ps.setString(2, (String) map.get("RoastStart"));
        ps.setString(3, (String) map.get("DryTime"));
        ps.setString(4, (String) map.get("FirstCrackStart"));
        ps.setString(5, (String) map.get("FirstCrackEnd"));
        ps.setString(6, (String) map.get("EndRoast"));
        ps.setFloat(7, (float) map.get("GreenWeight"));
        ps.setFloat(8, (float) map.get("RoastedWeight"));
        ps.setString(9, (String) map.get("ChargeTemp"));
        ps.setString(10, (String) map.get("RoastNotes"));
        ps.setString(11, (String) map.get("TastingNotes"));

        int updateCount = ps.executeUpdate();

        ps.close();
    }

    public void updateRoastLog(HashMap map) throws Exception {
        String query = this.getSql("updateRoastLog");
        PreparedStatement ps = this.getConnection().prepareStatement(query);
        ps.setString(1, (String) map.get("BeanId"));
        ps.setString(2, (String) map.get("RoastStart"));
        ps.setString(3, (String) map.get("DryTime"));
        ps.setString(4, (String) map.get("FirstCrackStart"));
        ps.setString(5, (String) map.get("FirstCrackEnd"));
        ps.setString(6, (String) map.get("EndRoast"));
        ps.setFloat(7, (float) map.get("GreenWeight"));
        ps.setFloat(8, (float) map.get("RoastedWeight"));
        ps.setString(9, (String) map.get("ChargeTemp"));
        ps.setString(10, (String) map.get("RoastNotes"));
        ps.setString(11, (String) map.get("TastingNotes"));
        ps.setString(12, (String) map.get("Id"));

        int updateCount = ps.executeUpdate();

        ps.close();
    }

    public String getSql(String queryName) throws Exception {
        String query = "";

        switch (queryName) {
            case "deleteBeanById":
                query = "DELETE "
                        + "FROM Beans "
                        + "WHERE Id = ?";

                break;
            case "deleteRoastLogById":
                query = "DELETE "
                        + "FROM RoastLog "
                        + "WHERE Id = ?";

                break;

            case "getBeanCounts":
                query = "SELECT COUNT(Id) as TotalBeans, COUNT(CASE WHEN InStock = 1 THEN 1 END) as TotalInStock FROM Beans";
                break;

            case "getBeanIdsAndNames":
                query = "SELECT " + ""
                        + "Id, Name, Density, InStock "
                        + "FROM Beans "
                        + "ORDER BY Name";
                break;

            case "getBeanById":
                query = "SELECT "
                        + "Id, InStock, Name, Density, GrindSetting, Altitude, ProcessMethod, Price, WeightInPounds, PricePerPound, Vendor, Origin, Variety, "
                        + "DensityGrams, Anaerobic, Comments "
                        + "FROM Beans "
                        + "WHERE Id = ?";
                break;

            case "getBeans":
                query = "SELECT "
                        + "Id, InStock, Name, Density, GrindSetting, Altitude, ProcessMethod, Price, WeightInPounds, PricePerPound, Vendor, Origin, Variety, "
                        + "DensityGrams, Anaerobic, Comments "
                        + "FROM Beans "
                        + "ORDER BY Name";
                break;

            case "getRoastLogs":
                query = "SELECT "
                        + "r.Id, r.BeanId, b.Name, r.RoastStart, r.RoastLevel, b.Density, r.TotalRoastTime, r.TotalDryTime, "
                        + "r.TotalFirstCrackTime,r.GreenWeight, r.RoastedWeight, r.MoistureLossWeight, r.MoistureLossPercentage, "
                        + "r.ChargeTemp, r.RoastNotes, r.TastingNotes, r.TotalDryTime, r.TotalFirstCrackTime, "
                        + "r.TotalDevelopmentTime, r.TotalRoastTime "
                        + "FROM RoastLog r, Beans b "
                        + "WHERE r.beanId = b.id "
                        + "ORDER BY b.Name ASC, r.roastStart DESC";
                break;

            case "getRoastLogsByBeanId":
                query = "SELECT r.Id, r.BeanId, r.RoastStart, r.RoastLevel, r.GreenWeight, r.RoastedWeight, "
                        + "r.MoistureLossPercentage, r.TotalRoastTime, r.TotalDryTime, r.TotalBrowningTime, "
                        + "r.TotalFirstCrackTime, r.TotalDevelopmentTime, r.RoastNotes, r.TastingNotes  "
                        + "FROM RoastLog r, Beans b "
                        + "WHERE r.beanId = b.id AND r.beanId = ? "
                        + "ORDER BY b.Name ASC, r.roastStart DESC";
                break;

            case "getRoastLogById":
                query = "SELECT r.Id, r.BeanId, r.RoastStart, r.DryTime, r.FirstCrackStart, r.FirstCrackEnd, r.EndRoast, "
                        + "r.GreenWeight, r.RoastedWeight, r.ChargeTemp, b.Density, r.RoastNotes, r.TastingNotes "
                        + "FROM RoastLog r, Beans b "
                        + "WHERE r.Id = ?";
                break;

            case "insertBean":
                query = "INSERT INTO Beans "
                        + "SET " 
                        + "Name = ?, Vendor = ?, ProcessMethod = ?, Price = ?, WeightInPounds = ?, "
                        + "Origin = ?, Variety = ?, Altitude = ?, DensityGrams  = ?, "
                        + "Anaerobic = ?, InStock = ?, GrindSetting = ?, Comments = ?";
                break;

            case "updateBean":
                query = "UPDATE Beans "
                        + "SET Name = ?, Vendor = ?, ProcessMethod = ?, Price = ?, WeightInPounds = ?, "
                        + "Origin = ?, Variety = ?, Altitude = ?, DensityGrams  = ?, "
                        + "Anaerobic = ?, InStock = ?, GrindSetting = ?, Comments = ? " 
                        + "Where Id = ?";
                break;

            case "insertRoastLog":
                query = "INSERT INTO RoastLog "
                        + "SET BeanId = ?, RoastStart = ?, DryTime = ?, FirstCrackStart = ?, FirstCrackEnd = ?, "
                        + "EndRoast = ?, GreenWeight = ?, RoastedWeight = ?, ChargeTemp = ?, RoastNotes = ?, TastingNotes = ?";
                break;

            case "updateRoastLog":
                query = "UPDATE RoastLog "
                        + "SET "
                        + "BeanId = ?, RoastStart = ?, DryTime = ?, FirstCrackStart = ?, FirstCrackEnd = ?, "
                        + "EndRoast = ?, GreenWeight = ?, RoastedWeight = ?, ChargeTemp = ?, RoastNotes = ?, "
                        + "TastingNotes = ? "
                        + "WHERE Id = ?";
                break;

        }

        if (queryName.isBlank()) {
            throw new Exception("SQL Query " + queryName + " not found.");
        }

        return query;
    }
}
