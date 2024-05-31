package app.study.register;

import app.util.ConnectionDataBase;
import app.util.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudyDao implements Dao<Study> {

    @Override
    public void save(Study study) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO study("
                   + "study_matter) "
                   + "VALUES(?)";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, study.getMatter());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (pstmt != null){
                pstmt.close();
            }
        }
    }

    @Override
    public List<Study> consultAll() throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet result = null;
        List<Study> studyList = new ArrayList<>();

        String sql = "SELECT * FROM study";

        try {
            connection = ConnectionDataBase.getConnection();
            stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()){
                Study study = new Study();
                study.setId(result.getLong("study_id"));
                study.setMatter(result.getString("study_matter"));
                studyList.add(study);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (result != null) {
                result.close();
            }
        }

        return studyList;
    }

    @Override
    public void update(Study study) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE study "
                   + "SET "
                   + "study_matter = ? "
                   + "WHERE study_id = ?; ";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, study.getMatter());
            pstmt.setLong(2, study.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (pstmt != null){
                pstmt.close();
            }
        }
    }

    @Override
    public void delete(Study study) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM study "
                   + "WHERE study_id = ?; ";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, study.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (pstmt != null){
                pstmt.close();
            }
        }
    }
}
