package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.bean.LezioneBean;
import model.bean.UserBean;
import model.dao.IlezioneDao;
import model.dao.ItutoratoDidatticoDao;
import model.dao.LezioneDao;
import model.dao.TutoratoDidatticoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


/**
 * Test di integrazione per la funzionalità: conferma lezione.
 *
 * @author Serena Liguori
 */
class ConfermaLezioneIntegrationTest {

    private ConfermaLezioneServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new ConfermaLezioneServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testConfermaLezione1() throws ServletException, IOException, SQLException {

        UserBean user = new UserBean();
        user.setNome("Lorenzo");
        user.setCognome("Rossi");
        user.setEmail("lorenzorossi1@studenti.unisa.it");
        user.setRuolo("T");

        request.getSession().setAttribute("utente", user);
        LezioneBean lezione = new LezioneBean();
        lezione.setId(11);
        lezione.setTutorato(18);
        request.getSession().setAttribute("lezione", lezione);

        IlezioneDao lezioneDao = new LezioneDao();
        ItutoratoDidatticoDao tutoratoDao = new TutoratoDidatticoDao();
        servlet.setlezioneDao(lezioneDao);
        servlet.settutoratodidatticoDao(tutoratoDao);
        servlet.doGet(request, response);

        assertEquals("view/LezioniTutorPage.jsp", response.getRedirectedUrl());
    }

    @Test
    void testConfermaLezione2() throws ServletException, IOException, SQLException {
        UserBean user = new UserBean();
        user.setNome("Lorenzo");
        user.setCognome("Rossi");
        user.setEmail("lorenzorossi1@studenti.unisa.it");
        user.setRuolo("T");
        request.getSession().setAttribute("utente", user);

        LezioneBean lezione = new LezioneBean();
        lezione.setId(11);
        lezione.setTutorato(18);
        request.getSession().setAttribute("lezione", lezione);

        IlezioneDao lezioneDao = new LezioneDao();
        ItutoratoDidatticoDao tutoratoDao = new TutoratoDidatticoDao();
        servlet.setlezioneDao(lezioneDao);
        servlet.settutoratodidatticoDao(tutoratoDao);

        servlet.doGet(request, response);

        assertEquals("view/LezioniTutorPage.jsp", response.getRedirectedUrl());
    }
}
