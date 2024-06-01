package app.pane.study.topic.register;

import app.util.ConnectionDataBase;
import app.util.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TopicDao implements Dao<Topic> {

    @Override
    public void save(Topic topic) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO topic("
                + "topic_title) "
                + "VALUES(?)";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, topic.getTitle());
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
    public List<Topic> consultAll() throws Exception {
        return null;
    }

    @Override
    public void update(Topic topic) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE topic "
                   + "SET "
                   + "topic_title = ? "
                   + "WHERE topic_id = ?; ";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, topic.getTitle());
            pstmt.setLong(2, topic.getId());
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
    public void delete(Topic topic) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM topic "
                   + "WHERE topic_id = ?; ";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, topic.getId());
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
