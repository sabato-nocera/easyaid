package control;

import model.bean.LezioneBean;
import model.bean.StudenteBean;
import model.bean.TutorBean;
import model.bean.UserBean;
import model.dao.LezioneDAO;
import model.dao.StudenteDAO;
import model.dao.TutorDAO;
import model.dao.TutoratoDidatticoDAO;
import other.MyLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/*
@author Serena Liguori
Servlet che permette di visualizzare le lezioni
 */
@WebServlet(name = "LezioniServlet", urlPatterns = "/LezioniServlet")
public class LezioniServlet extends HttpServlet {
  private static final MyLogger log = MyLogger.getInstance();
  private static final String myClass = "LezioniServlet";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    log.info(myClass, "Collegamento alla Servlet...");
    HttpSession session = request.getSession();
    UserBean user = (UserBean) session.getAttribute("utente");
    LezioneDAO lezioneDao = new LezioneDAO();
    StudenteDAO studenteDao = new StudenteDAO();
    TutoratoDidatticoDAO tutoratoDidatticoDAO = new TutoratoDidatticoDAO();
    TutorDAO tutorDao = new TutorDAO();
    if (user != null) {
      if (user.isStudente()) {
        StudenteBean bean = null;
        try {
          bean = studenteDao.doRetrieveByEmail(user.getEmail());
        } catch (SQLException e) {
          e.printStackTrace();
        }
        if (bean != null) {
          try {
            ArrayList<LezioneBean> lista =
                (ArrayList<LezioneBean>) lezioneDao.doRetrieveLezioneByStudente(bean.getEmail());
            session.setAttribute("listaLezioni", lista);
            response.sendRedirect("view/LezioniStudentePage.jsp");
          } catch (ClassNotFoundException | SQLException e) {
            log.error(myClass, "Catturata eccezione nella Servlet", e);
            e.printStackTrace();
          }
        }
      } else if (user.isTutor()) {
        TutorBean bean = null;
        try {
          bean = tutorDao.doRetrieveByEmail(user.getEmail());
        } catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
        }
        if (bean != null) {
          try {
            ArrayList<LezioneBean> lista =
                (ArrayList<LezioneBean>) lezioneDao.doRetrieveLezioneByTutor(bean.getEmailTutor());
            session.setAttribute("listaLezioni", lista);
            session.setAttribute(
                "richiesteTutorato",
                tutoratoDidatticoDAO.doRetrieveAllByTutor(bean.getEmailTutor()));
            response.sendRedirect("view/LezioniTutorPage.jsp");
          } catch (ClassNotFoundException | SQLException e) {
            log.error(myClass, "Catturata eccezione nella Servlet", e);
            e.printStackTrace();
          }
        }
      }
    } else {
      response.sendRedirect("view/LoginPage.jsp");
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
