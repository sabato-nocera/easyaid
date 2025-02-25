package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.bean.SupportoEsameBean;
import model.bean.TutoratoDidatticoBean;
import model.bean.UserBean;
import model.dao.IsupportoEsameDao;
import model.dao.ItutorDao;
import model.dao.ItutoratoDidatticoDao;
import model.dao.SupportoEsameDao;
import model.dao.TutorDao;
import model.dao.TutoratoDidatticoDao;
import other.MyLogger;

/**
 * Servlet che permette di approvare una richiesta di servizio.
 *
 * @author Martina Giugliano
 *
 */

@WebServlet("/ApprovazioneRichiesta")
public class ApprovazioneRichiestaServlet extends HttpServlet {
  private static final MyLogger log = MyLogger.getInstance();
  private static final String myClass = "ApprovazioneRichiestaServlet";
  private ItutoratoDidatticoDao tutoratodao = new TutoratoDidatticoDao();
  private IsupportoEsameDao supportodao = new SupportoEsameDao();
  private ItutorDao tutordao = new TutorDao();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    super.doPost(req, resp);
  }

  public void setTutoratodao(ItutoratoDidatticoDao tutoratodao) {
    this.tutoratodao = tutoratodao;
  }

  public void setSupportodao(IsupportoEsameDao supportodao) {
    this.supportodao = supportodao;
  }

  public void setTutordao(ItutorDao tutordao) {
    this.tutordao = tutordao;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    log.info(myClass, "Collegamento alla Servlet...");
    HttpSession session = req.getSession();
    UserBean prof = (UserBean) session.getAttribute("utente");
    TutoratoDidatticoBean tutorato = (TutoratoDidatticoBean) session.getAttribute("tutorato");
    SupportoEsameBean supporto = (SupportoEsameBean) session.getAttribute("supporto");
    if (tutorato != null) {
      try {
        tutoratodao.approvaRichiesta(tutorato.getId(), prof.getEmail());
        tutordao.updateOreSvolte(tutorato.getOreRichieste(), tutorato.getTutorEmail());
        session.setAttribute("alertMsg", "Richiesta approvata con successo");
        resp.sendRedirect("view/HomePage.jsp");
      } catch (SQLException e) {
        log.error(myClass, "Catturata eccezione nella Servlet", e);
        e.printStackTrace();
      }
    } else if (supporto != null) {
      try {
        supportodao.approvaRichiesta(supporto.getId(), prof.getEmail());
        tutordao.updateOreSvolte(supporto.getOreRichieste(), supporto.getTutorEmail());
        session.setAttribute("alertMsg", "Richiesta approvata con successo");
        resp.sendRedirect("view/HomePage.jsp");
      } catch (SQLException e) {
        log.error(myClass, "Catturata eccezione nella Servlet", e);
        e.printStackTrace();
      }

    } else {
      session.setAttribute("alertMsg", "L'operazione non è andata a buon fine");
      resp.sendRedirect("view/HomePage.jsp");
    }
  }
}
