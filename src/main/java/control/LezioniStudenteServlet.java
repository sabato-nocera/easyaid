package control;

import model.bean.*;
import model.dao.LezioneDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LezioniStudenteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        TutoratoDidatticoBean bean= (TutoratoDidatticoBean) session.getAttribute("tutorato");
        LezioneDAO dao= new LezioneDAO();
        if(bean!=null){
            try {
                ArrayList<LezioneBean> lista= (ArrayList<LezioneBean>) dao.doRetrieveLezione(bean.getId());
                session.setAttribute("listaLezioni", lista);
                response.sendRedirect("viewLezioniStudente.jsp");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
