package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReimbursementDAO {

    static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    /**
     * Should retrieve a Reimbursement from the DB with the corresponding id or an empty optional if there is no match.
     */
    public static Reimbursement getById(int id) {
        String sql = "select u.id as user_id, u.firstName, u.lastName, r.id as r_id, r.author, r.resolver \n" + "from employee u left join author r on r.author = u.id where r.id =?";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Reimbursement r = new Reimbursement(
                        rs.getInt("r_id"),
                        Status.valueOf(rs.getString("status")),
                        UserDAO.getByUsername(rs.getString("author")),
                        UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justify")
                );
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Should retrieve a List of Reimbursements from the DB with the corresponding Status or an empty List if there are no matches.
     */
    public List<Reimbursement> getByStatus(Status status) {
        return Collections.emptyList();
    }

    /**
     * <ul>
     *     <li>Should Update an existing Reimbursement record in the DB with the provided information.</li>
     *     <li>Should throw an exception if the update is unsuccessful.</li>
     *     <li>Should return a Reimbursement object with updated information.</li>
     * </ul>
     */
    public Reimbursement update(Reimbursement unprocessedReimbursement) {
        String sql = "insert into reimbursement_requests (id, status, author, resolver, cost, date, time, location, description, justify) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getStatus().toString());
            ps.setString(2, unprocessedReimbursement.getAuthor().getUsername());
            ps.setString(3, unprocessedReimbursement.getResolver().getUsername());
            ps.setDouble(4, unprocessedReimbursement.getAmount());
            ps.setString(5, unprocessedReimbursement.getDate());
            ps.setString(6, unprocessedReimbursement.getTime());
            ps.setString(7, unprocessedReimbursement.getLocation());
            ps.setString(8, unprocessedReimbursement.getDescription());
            ps.setString(9, unprocessedReimbursement.getJustify());


            ps.executeUpdate();
            return unprocessedReimbursement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unprocessedReimbursement;
    }

    public static void updateAllowance(Reimbursement newAllowance) {

        String sql = "update users set reimbursement_allowed = ? where id = ?";

        try (Connection conn = cu.getConnection()) {

            User author = UserDAO.getByUsername(newAllowance.getAuthor().getUsername());

            double newAllowanceInt = getAllowance(author) - newAllowance.getAmount();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newAllowanceInt);
            ps.setInt(2, author.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getAllowance(User author) {
        String sql = "select * from users where id = ?";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, author.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("reimbursement_allowed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Reimbursement createReimbursement(Reimbursement unprocessedReimbursement) {
        String sql = "insert into reimbursement_requests (id, status, author, resolver, cost, date, time, location, description, justify) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getStatus().toString());
            ps.setString(2, unprocessedReimbursement.getAuthor().getUsername());
            ps.setString(3, unprocessedReimbursement.getResolver().getUsername());
            ps.setDouble(4, unprocessedReimbursement.getAmount());
            ps.setString(5, unprocessedReimbursement.getDate());
            ps.setString(6, unprocessedReimbursement.getTime());
            ps.setString(7, unprocessedReimbursement.getLocation());
            ps.setString(8, unprocessedReimbursement.getDescription());
            ps.setString(9, unprocessedReimbursement.getJustify());

            ps.executeUpdate();
            return unprocessedReimbursement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unprocessedReimbursement;
    }

    public static List<Reimbursement> getAll() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String sql = "select * from reimbursement_requests";
        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("r_id"),
                        Status.valueOf(rs.getString("status")),
                        UserDAO.getByUsername(rs.getString("author")),
                        UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justify")
                );
                reimbursements.add(r);
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();


    }

    public static List<Reimbursement> getByUser(String author) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String sql = "select * from reimbursement_requests where author = ?";
        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, author);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("r_id"),
                        Status.valueOf(rs.getString("status")),
                        UserDAO.getByUsername(rs.getString("author")),
                        UserDAO.getByUsername(rs.getString("resolver")),
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("justify")
                );
                reimbursements.add(r);
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}