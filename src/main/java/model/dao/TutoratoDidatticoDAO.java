package model.dao;

import model.bean.TutoratoDidatticoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutoratoDidatticoDAO {


    public synchronized List<TutoratoDidatticoBean> doRetriveAll() throws SQLException,ClassNotFoundException{
        Connection conn=null;
        String query="SELECT * FROM tutorato_didattico";
        List<TutoratoDidatticoBean> list=new ArrayList<>();


        PreparedStatement stmt=null;

        try {
            conn = ConnectionPool.conn();
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            TutoratoDidatticoBean bean = new TutoratoDidatticoBean();
            while (rs.next()) {
                bean.setId(rs.getInt("idtutorato_didattico"));
                bean.setDateDisponibili(rs.getString("date_disponibili"));
                bean.setOreDisponibili(rs.getString("ore_disponibili"));
                bean.setOreRichieste(rs.getInt("ore_richieste"));
                bean.setCommento(rs.getString("commento"));
                bean.setStatus(rs.getInt("status"));
                bean.setInsegnamento(rs.getString("insegnamento"));
                bean.setDipartimento(rs.getString("dipartimento"));
                bean.setStudenteEmail(rs.getString("studente_email"));
                bean.setTutorEmail(rs.getString("tutor_email"));
                bean.setProfEmail(rs.getString("prof_refe_email"));
                list.add(bean);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally{
            stmt.close();
            if(conn!=null)
                conn.close();
        }

        return list;

    }


    public synchronized List<TutoratoDidatticoBean> doRetrieveAllByStudente(String emailStudente)  throws SQLException,ClassNotFoundException
    {
        Connection conn=null;
        String query="SELECT * FROM tutorato_didattico WHERE studente_email=?";
        PreparedStatement stmt=null;
        List<TutoratoDidatticoBean> list=new ArrayList<>();
        TutoratoDidatticoBean bean = new TutoratoDidatticoBean();
        try
        {
            conn=ConnectionPool.conn();
            stmt=conn.prepareStatement(query);
            stmt.setString(1,emailStudente);
            ResultSet rs=stmt.executeQuery();
            while(rs.next())
            {
                bean.setId(rs.getInt("idtutorato_didattico"));
                bean.setDateDisponibili(rs.getString("date_disponibili"));
                bean.setOreDisponibili(rs.getString("ore_disponibili"));
                bean.setOreRichieste(rs.getInt("ore_richieste"));
                bean.setCommento(rs.getString("commento"));
                bean.setStatus(rs.getInt("status"));
                bean.setInsegnamento(rs.getString("insegnamento"));
                bean.setDipartimento(rs.getString("dipartimento"));
                bean.setStudenteEmail(rs.getString("studente_email"));
                bean.setTutorEmail(rs.getString("tutor_email"));
                bean.setProfEmail(rs.getString("prof_refe_email"));
                list.add(bean);
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally{
            stmt.close();
            if(conn!=null)
                conn.close();
        }

        return list;
    }
}
