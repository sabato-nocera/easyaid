package control;

import model.bean.SupportoEsameBean;
import model.bean.TutorBean;
import model.bean.TutoratoDidatticoBean;
import model.bean.UserBean;
import model.dao.SupportoEsameDAO;
import model.dao.TutorDAO;
import model.dao.TutoratoDidatticoDAO;
import model.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Martina Giugliano
 * Servlet che permette di visualizzare una singola richiesta di servizio
 */
@WebServlet("/SingolaRichiestaServlet")
public class SingolaRichiestaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SingolaRichiestaServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        int idTutorato = 0;
        int idSupporto = 0;
        idTutorato = Integer.parseInt(request.getParameter("idTutorato"));
        idSupporto = Integer.parseInt(request.getParameter("idSupporto"));
        if(idTutorato==0){
            TutoratoDidatticoDAO dao = new TutoratoDidatticoDAO();
            try {
                TutoratoDidatticoBean tutorato = dao.doRetriveById(idTutorato);
                session.setAttribute("tutorato",tutorato);
                response.sendRedirect("view/viewSingolaRichiesta");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(idSupporto==0){
            SupportoEsameDAO dao = new SupportoEsameDAO();
            try {
                SupportoEsameBean supporto = dao.doRetriveById(idSupporto);
                session.setAttribute("supporto",supporto);
                response.sendRedirect("view/viewSingolaRichiesta");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            session.setAttribute("alertMsg","Impossibile aprire il richiesta");
            response.sendRedirect("view/viewRichiesteServizio.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}