package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * JDBC -DRiverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {


    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,member.getMemberId());
            pstmt.setInt(2,member.getMoney());

            pstmt.executeUpdate();

            return member;

        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        } finally {
            close(conn,pstmt,null);
        }
    }

    /* finally에서 따로 메소드로 뺀 이유는 pstmt가 에러나면 conn도 안닫히기때문에*/
    private void close(Connection conn, Statement pstmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("rs error",e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("pstmt error",e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("conn error",e);
            }
        }

    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
