package app.study.register;

import app.pane.study.topic.register.Topic;
import app.pane.study.topic.register.TopicDao;
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

    public void saveOrUpdateTopics(Study study) throws Exception {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet result = null;

        String query1 = "UPDATE topic "
                      + "SET "
                      + "topic_title = ?, "
                      + "topic_text = ? "
                      + "WHERE topic_id = ?; ";

        String query2 = "INSERT INTO topic("
                      + "topic_title, "
                      + "topic_text) "
                      + "VALUES(?, ?); ";

        String query3 = "INSERT INTO study_topic "
                      + "(study_id, "
                      + "topic_id) "
                      + "VALUES(?,?); ";

        String query4 = "DELETE FROM study_topic "
                      + "WHERE topic_id = ? AND study_id = ?; ";

        try {
            connection = ConnectionDataBase.getConnection();
            connection.setAutoCommit(false);

            for (Topic topic: study.getListTopics()) {
                if (topic.getId() > 0L) {
                    pstmt = connection.prepareStatement(query1);
                    pstmt.setString(1, topic.getTitle());
                    pstmt.setString(2, topic.getText());
                    pstmt.setLong(3, topic.getId());
                    pstmt.executeUpdate();
                }
            }

            for (Topic topic: study.getListTopics()) {
                if(topic.getId().equals(0L) || topic.getId() == null) {
                    pstmt = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, topic.getTitle());
                    pstmt.setString(2, topic.getText());
                    pstmt.execute();
                    result = pstmt.getGeneratedKeys();
                    if(result.next()) {
                        Long topicIdInsert = result.getLong(1);
                        topic.setId(topicIdInsert);
                    }
                }
            }

            pstmt = connection.prepareStatement(query3);
            List<Topic> listTopicCurrent = consultListTopic(study.getId());
            for (Topic topic : study.getListTopics()) {
                boolean existId = false;
                for (Topic topicCurrent : listTopicCurrent) {
                    if (topic.getId().equals(topicCurrent.getId())) {
                        existId = true;
                        break;
                    }
                }
                if (!existId) {
                    pstmt.setLong(1, study.getId());
                    pstmt.setLong(2, topic.getId());
                    pstmt.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (result != null) {
                result.close();
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
                study.setListTopics(consultListTopic(result.getLong("study_id")));
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

    private List<Topic> consultListTopic(Long idStudy) throws Exception {
        Connection connection = null;
        ResultSet result = null;
        PreparedStatement pstmt = null;
        List<Topic> topicList = new ArrayList<>();

        String sql = "SELECT "
                   + "study_topic.topic_id, "
                   + "topic.topic_title, "
                   + "topic.topic_text "
                   + "FROM "
                   + "study_topic "
                   + "LEFT JOIN topic "
                   + "ON study_topic.topic_id = topic.topic_id "
                   + "WHERE study_topic.study_id = ? "
                   + "ORDER BY topic.topic_title ASC; ";

        try {
            connection = ConnectionDataBase.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, idStudy);
            result = pstmt.executeQuery();
            while(result.next()) {
                Topic topic = new Topic();
                topic.setId(result.getLong("topic_id"));
                topic.setTitle(result.getString("topic_title"));
                topic.setText(result.getString("topic_text"));
                topicList.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if (pstmt != null)
                    pstmt.close();
                if (result != null)
                    result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return topicList;
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
