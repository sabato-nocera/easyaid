package control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import javax.servlet.ServletException;
import model.bean.LezioneBean;
import model.bean.StudenteBean;
import model.bean.TutorBean;
import model.bean.UserBean;
import model.dao.CommentoDao;
import model.dao.IcommentoDao;
import model.dao.IstudenteDao;
import model.dao.ItutorDao;
import model.dao.IuserDao;
import model.dao.StudenteDao;
import model.dao.TutorDao;
import model.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


/**
 * Testing per l'inserimento di un commento.
 *
 * @author Mariagiovanna Bianco
 */
class CommentoTest {
  private CommentoServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  @BeforeEach
  void setUp() {
    servlet = new CommentoServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
  }

  @Test
  void testInserimentoCommentoTutor1() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    TutorBean tutorBean = new TutorBean();

    userBean.setEmail("lorenzorossi1@studenti.unisa.it");
    userBean.setPassword("Lorenzo#rossi1");
    userBean.setRuolo("T");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = null;
    request.setParameter("commento", msg);

    IcommentoDao commentoDao = mock(CommentoDao.class);
    ItutorDao tutorDao = mock(TutorDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoT(tutorDao);
    try {
      when(tutorDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(tutorBean);
      when(commentoDao.insertCommentoTutor(lezione.getId(), msg, tutorBean.getEmailTutor()))
          .thenReturn(false);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);

    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoTutor2() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);

    UserBean userBean = new UserBean();
    TutorBean tutorBean = new TutorBean();

    userBean.setEmail("lorenzorossi1@studenti.unisa.it");
    userBean.setPassword("Lorenzo#rossi1");
    userBean.setRuolo("T");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg =
        "Ta5u3NJ38OOADdAD1A2uzHu0Lw8OD7e0v0e0BpT814Y63H3ye7NUbP03pq"
            + "h48nykpup7M1ah0daYYi5028Laxm8vVgUpUi6M5GEBDwvBZsIB8gs7pCeDkz3596e5FtzN1J"
            + "O7Fl7C4YQ870IF8q01w6QWHNYfBuqU9RqeM83AH7414AUpLgc8IQ62Nf3633bgPd7"
            + "WKHrZ2BVFT6rPRBVWkXG5Cm1GYMW1UCephHi0O76hP9Z4fc2TfKNhDMWw2hsCXsynp"
            + "bFjmk6vSL41tSVW03I0LZSjM2D8Q6K6fpRH0Ajy36nZaAS6IY9ZsHECrsV2Z2I51Fh5W"
            + "KEUwYx1tiOc79U28NVA9t9cr6psAMdsOu5syHkenamjUTafSTVC4u5PQWC7denLZO99i5lkzy"
            + "uoNwaR71TJt0KtgL3jFdt565NjencLofL5rDRP1GYsAitGLG572jTKFKTCbTa";
    request.setParameter("commento", msg);

    IcommentoDao commentoDao = mock(CommentoDao.class);
    ItutorDao tutorDao = mock(TutorDao.class);

    servlet.setDaoC(commentoDao);
    servlet.setDaoT(tutorDao);
    try {
      when(tutorDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(tutorBean);
      when(commentoDao.insertCommentoTutor(lezione.getId(), msg, tutorBean.getEmailTutor()))
          .thenThrow(new SQLException());
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);

    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoTutor3() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    TutorBean tutorBean = new TutorBean();

    userBean.setEmail("lorenzorossi1@studenti.unisa.it");
    userBean.setPassword("Lorenzo#rossi1");
    userBean.setRuolo("T");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = "Ok,Ci sarò!";
    request.setParameter("commento", msg);

    IcommentoDao commentoDao = mock(CommentoDao.class);
    ItutorDao tutorDao = mock(TutorDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoT(tutorDao);
    try {
      when(tutorDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(tutorBean);
      when(commentoDao.insertCommentoTutor(lezione.getId(), msg, tutorBean.getEmailTutor()))
          .thenReturn(true);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);

    assertEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoTutor4() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    servlet.doGet(request, response);
    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoTutor5() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    TutorBean tutorBean = new TutorBean();

    userBean.setEmail("lorenzorossi1@studenti.unisa.it");
    userBean.setPassword("Lorenzo#rossi1");
    userBean.setRuolo("T");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = "Ok,Ci sarò!";
    request.setParameter("commento", msg);

    IcommentoDao commentoDao = mock(CommentoDao.class);
    ItutorDao tutorDao = mock(TutorDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoT(tutorDao);
    try {
      when(tutorDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(null);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);

    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoStudente1() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    StudenteBean studenteBean = new StudenteBean();
    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = null;
    request.setParameter("commento", msg);
    IcommentoDao commentoDao = mock(CommentoDao.class);
    IstudenteDao studenteDao = mock(StudenteDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoS(studenteDao);
    try {
      when(studenteDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(studenteBean);
      when(commentoDao.insertCommentoStudente(lezione.getId(), msg, studenteBean.getEmail()))
          .thenReturn(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);
    assertEquals(
        "Operazione non andata a buon fine",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoStudente2() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    StudenteBean studenteBean = new StudenteBean();

    userBean.setEmail("abaglio9@studenti.unisa.it");
    userBean.setPassword("Aldo#Baglio45");
    userBean.setRuolo("S");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg =
        "Ta5u3NJ38OOADdAD1A2uzHu0Lw8OD7e0v0e0BpT814Y63H3ye7NUbP03pq"
            + "h48nykpup7M1ah0daYYi5028Laxm8vVgUpUi6M5GEBDwvBZsIB8gs7pCeDkz3596e5FtzN1J"
            + "O7Fl7C4YQ870IF8q01w6QWHNYfBuqU9RqeM83AH7414AUpLgc8IQ62Nf3633bgPd7"
            + "WKHrZ2BVFT6rPRBVWkXG5Cm1GYMW1UCephHi0O76hP9Z4fc2TfKNhDMWw2hsCXsynp"
            + "bFjmk6vSL41tSVW03I0LZSjM2D8Q6K6fpRH0Ajy36nZaAS6IY9ZsHECrsV2Z2I51Fh5W"
            + "KEUwYx1tiOc79U28NVA9t9cr6psAMdsOu5syHkenamjUTafSTVC4u5PQWC7denLZO99i5lkzy"
            + "uoNwaR71TJt0KtgL3jFdt565NjencLofL5rDRP1GYsAitGLG572jTKFKTCbTa";
    request.setParameter("commento", msg);
    IcommentoDao commentoDao = mock(CommentoDao.class);
    IstudenteDao studenteDao = mock(StudenteDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoS(studenteDao);
    try {
      when(studenteDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(studenteBean);
      when(commentoDao.insertCommentoStudente(lezione.getId(), msg, studenteBean.getEmail()))
          .thenReturn(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);
    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoStudente3() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    StudenteBean studenteBean = new StudenteBean();

    userBean.setEmail("abaglio9@studenti.unisa.it");
    userBean.setPassword("Aldo#Baglio45");
    userBean.setRuolo("S");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = "Ok,Ci sarò!";
    request.setParameter("commento", msg);

    IcommentoDao commentoDao = mock(CommentoDao.class);
    IstudenteDao studenteDao = mock(StudenteDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoS(studenteDao);
    try {
      when(studenteDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(studenteBean);
      when(commentoDao.insertCommentoStudente(lezione.getId(), msg, studenteBean.getEmail()))
          .thenReturn(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);

    assertEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoStudente4() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    StudenteBean studenteBean = new StudenteBean();

    userBean.setEmail("abaglio9@studenti.unisa.it");
    userBean.setPassword("Aldo#Baglio45");
    userBean.setRuolo("S");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(4);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg =
        "Ta5u3NJ38OOADdAD1A2uzHu0Lw8OD7e0v0e0BpT814Y63H3ye7NUbP03pq"
            + "h48nykpup7M1ah0daYYi5028Laxm8vVgUpUi6M5GEBDwvBZsIB8gs7pCeDkz3596e5FtzN1J"
            + "O7Fl7C4YQ870IF8q01w6QWHNYfBuqU9RqeM83AH7414AUpLgc8IQ62Nf3633bgPd7"
            + "WKHrZ2BVFT6rPRBVWkXG5Cm1GYMW1UCephHi0O76hP9Z4fc2TfKNhDMWw2hsCXsynp"
            + "bFjmk6vSL41tSVW03I0LZSjM2D8Q6K6fpRH0Ajy36nZaAS6IY9ZsHECrsV2Z2I51Fh5W"
            + "KEUwYx1tiOc79U28NVA9t9cr6psAMdsOu5syHkenamjUTafSTVC4u5PQWC7denLZO99i5lkzy"
            + "uoNwaR71TJt0KtgL3jFdt565NjencLofL5rDRP1GYsAitGLG572jTKFKTCbTa";
    request.setParameter("commento", msg);
    IcommentoDao commentoDao = mock(CommentoDao.class);
    IstudenteDao studenteDao = mock(StudenteDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoS(studenteDao);
    try {
      when(studenteDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(null);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);
    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }

  @Test
  void testInserimentoCommentoStudente5() throws ServletException, IOException {
    MockitoAnnotations.initMocks(this);
    IuserDao userDao = mock(UserDao.class);
    UserBean userBean = new UserBean();
    StudenteBean studenteBean = new StudenteBean();

    userBean.setEmail("abaglio9@studenti.unisa.it");
    userBean.setPassword("Aldo#Baglio45");
    userBean.setRuolo("S");

    LezioneBean lezione = new LezioneBean();
    lezione.setId(0);
    request.getSession().setAttribute("utente", userBean);
    request.getSession().setAttribute("lezione", lezione);
    String msg = "";
    request.setParameter("commento", msg);
    IcommentoDao commentoDao = mock(CommentoDao.class);
    IstudenteDao studenteDao = mock(StudenteDao.class);
    servlet.setDaoC(commentoDao);
    servlet.setDaoS(studenteDao);
    try {
      when(studenteDao.doRetrieveByEmail(userBean.getEmail())).thenReturn(null);
      when(commentoDao.insertCommentoStudente(lezione.getId(), msg, studenteBean.getEmail()))
          .thenReturn(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    servlet.doGet(request, response);
    assertNotEquals(
        "Commento inserito con successo",
        Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));
  }
}
