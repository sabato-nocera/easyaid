package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import model.bean.SupportoEsameBean;
import model.bean.UserBean;
import model.dao.IstudenteDao;
import model.dao.IsupportoEsameDao;
import model.dao.StudenteDao;
import model.dao.SupportoEsameDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;




/**
 * Test di integrazione per la funzionalità: inserimento richiesta supporto.
 *
 * @author Mariagiovanna Bianco
 */

class InserimentoRichiestaSupportoIntegrationTest {
    private InserimentoRichiestaSupportoServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new InserimentoRichiestaSupportoServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void inserimentoSupportotest() throws ServletException, IOException, SQLException,
            ClassNotFoundException {
        IsupportoEsameDao supportoesameDao = new SupportoEsameDao();
        servlet.setsupportoesameDao(supportoesameDao);

        UserBean bean = new UserBean();
        bean.setNome("Paolo");
        bean.setCognome("Rossi");
        bean.setEmail("paolorossi10@studenti.unisa.it");
        bean.setPassword("Paolo#Rossi10");
        bean.setRuolo("S");
        request.getSession().setAttribute("utente", bean);
        String data = "2021-12-11";
        String ora = "16:00";
        int oreRichieste = 2;
        String modalitaEsame = "Scritto";
        String eventualiAusili = "Tempo Aggiuntivo";
        String tipoAssistenza = "Tutor Lettore";
        String luogo = "Edificio F3 Aula 18";
        String dipartimento = "Informatica";
        String insegnamento = "Programmazione I";
        String docente = "Distasi";
        request.setParameter("dipartimento", dipartimento);
        request.setParameter("insegnamento", insegnamento);
        request.setParameter("data", data);
        request.setParameter("ora", ora);
        request.setParameter("ore_richieste", String.valueOf(oreRichieste));
        request.setParameter("docente", docente);
        request.setParameter("luogo", luogo);
        request.setParameter("modalita_esame", modalitaEsame);
        request.setParameter("tipo_di_assistenza", tipoAssistenza);
        request.setParameter("eventuali_ausili", eventualiAusili);
        SupportoEsameBean supportoEsameBean = new SupportoEsameBean();
        supportoEsameBean.setData(data);
        supportoEsameBean.setDipartimento(dipartimento);
        supportoEsameBean.setDocente(docente);
        supportoEsameBean.setInsegnamento(insegnamento);
        supportoEsameBean.setLuogo(luogo);
        supportoEsameBean.setEventualiAusili(eventualiAusili);
        supportoEsameBean.setOra(ora);
        supportoEsameBean.setOreRichieste(oreRichieste);
        supportoEsameBean.setStatus(0);
        supportoEsameBean.setStudenteEmail(bean.getEmail());
        supportoEsameBean.setEventualiAusili(eventualiAusili);
        supportoEsameBean.setTipoAssistenza(tipoAssistenza);
        supportoEsameBean.setModalitaEsame(modalitaEsame);
        servlet.doGet(request, response);
        assertEquals(
                "Richiesta di servizio di supporto esame inserita con successo!",
                Objects.requireNonNull(request.getSession()).getAttribute("alertMsg"));

        List<SupportoEsameBean> list = supportoesameDao.doRetrieveAllByStudente(bean.getEmail());
        supportoEsameBean = list.get(list.size() - 1);
        supportoesameDao.deleteSupporto(supportoEsameBean);

        IstudenteDao studenteDao = new StudenteDao();
        studenteDao.updateOreDisponibili(-oreRichieste, bean.getEmail());
    }

}