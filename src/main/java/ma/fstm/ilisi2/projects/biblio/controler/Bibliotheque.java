package ma.fstm.ilisi2.projects.biblio.controler;

import ma.fstm.ilisi2.projects.biblio.model.bo.Adherent;
import ma.fstm.ilisi2.projects.biblio.model.bo.Emprunt;
import ma.fstm.ilisi2.projects.biblio.model.bo.Livre;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOAdherent;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOEmprunt;
import ma.fstm.ilisi2.projects.biblio.model.service.ServiceAdherent;
import ma.fstm.ilisi2.projects.biblio.model.service.ServiceEmprunt;
import ma.fstm.ilisi2.projects.biblio.model.service.ServiceLivre;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "helloServlet", value = {"/Books", "/Adherent", "/Emprunt"})
public class Bibliotheque extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String path = request.getServletPath();
        String operation = request.getParameter("operation");

        if (path.equals("/Books")){

            request.getSession().removeAttribute("primkeylivre");
            request.getSession().removeAttribute("FKLivre");

            if(operation != null){
                switch (operation){
                    case "delete" :
                        String isbn = request.getParameter("id");
                        if(!ServiceLivre.remove(request.getParameter("id")))
                            request.getSession().setAttribute("FKLivre", isbn);
                        break;

                    case "search" :
                        ArrayList<Livre> livres = ServiceLivre.retrieve(request.getParameter("auteur"));
                        if (livres != null){
                            request.setAttribute("livres", livres);
                            try {
                                request.getRequestDispatcher("/BooksList.jsp").forward(request, response);
                            } catch (ServletException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;

                    case "logout" :
                        request.getSession().removeAttribute("connect");
                        break;
                }
            }

            ArrayList<Livre> livres = ServiceLivre.retrieve();
            request.setAttribute("livres", livres);

            try {
                request.getRequestDispatcher("/BooksList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

        }

        if (path.equals("/Adherent")){

            request.getSession().removeAttribute("FKAdherent");
            request.getSession().removeAttribute("primkeyAdherent");
            Adherent adherent;

            if(operation != null) {
                switch (operation) {
                    case "delete":
                        adherent = ServiceAdherent.remove(Integer.parseInt(request.getParameter("id")));
                        if (adherent != null)
                            request.getSession().setAttribute("FKAdherent", adherent.getCin());
                        break;

                    case "search" :
                        adherent = ServiceAdherent.retrieve(request.getParameter("cin"));
                        if (adherent != null) {
                            ArrayList<Adherent> adherents = new ArrayList<>();
                            adherents.add(adherent);
                            request.setAttribute("adherents", adherents);

                            try {
                                request.getRequestDispatcher("/AdherentsList.jsp").forward(request, response);
                            } catch (ServletException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                }
            }

            Vector<Adherent> adherents = ServiceAdherent.retrieve();
            request.setAttribute("adherents", adherents);
            try {
                request.getRequestDispatcher("/AdherentsList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

        }

        if (path.equals("/Emprunt")){

            request.getSession().removeAttribute("minExemp");

            if (operation != null) {
                switch (operation) {
                    case "update":
                        ServiceEmprunt.updateStatus(Integer.parseInt(request.getParameter("id")));
                        break;
                }
            }

            Vector<Emprunt> emprunts = ServiceEmprunt.retrieve();
            request.setAttribute("emprunts", emprunts);

            try {
                request.getRequestDispatcher("/EmpruntsList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String path = request.getServletPath();
        String operation = request.getParameter("operation");

        if (path.equals("/Books")) {

            request.getSession().removeAttribute("primkeylivre");
            request.getSession().removeAttribute("FKLivre");

            switch (operation) {
                case "create":
                    if (!ServiceLivre.insert(request.getParameter("isbn"), request.getParameter("title"), request.getParameter("auteur"), Integer.parseInt(request.getParameter("exemp"))))
                        request.getSession().setAttribute("primkeylivre", request.getParameter("isbn"));
                    break;

                case "update":
                    ServiceLivre.update(request.getParameter("isbn"), request.getParameter("title"), request.getParameter("auteur"), Integer.parseInt(request.getParameter("exemp")));
                    break;

                case "login":
                    if (!request.getParameter("username").equals("admin") || !request.getParameter("password").equals("admin")) {
                        request.getSession().setAttribute("logerr", true);
                        response.sendRedirect(request.getContextPath() + "/Login.jsp");
                        return;
                    }
                    request.getSession().removeAttribute("logerr");
                    request.getSession().setAttribute("connect", request.getParameter("username"));
                    break;
            }

            ArrayList<Livre> livres = ServiceLivre.retrieve();
            request.setAttribute("livres", livres);

            try {
                request.getRequestDispatcher("/BooksList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }

        if (path.equals("/Adherent")){

            request.getSession().removeAttribute("FKAdherent");
            request.getSession().removeAttribute("primkeyAdherent");

            switch (operation) {
                case "create":
                    if (!ServiceAdherent.insert(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("cin")))
                        request.getSession().setAttribute("primkeyAdherent", request.getParameter("cin"));
                    break;

                case "update":
                    if(!DAOAdherent.update(new Adherent(Integer.parseInt(request.getParameter("id")), request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("cin")))){
                        request.getSession().setAttribute("primkeyAdherent", request.getParameter("cin"));
                        break;
                    }
                    break;
            }

            Vector<Adherent> adherents = ServiceAdherent.retrieve();
            request.setAttribute("adherents", adherents);
            try {
                request.getRequestDispatcher("/AdherentsList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

        }

        if (path.equals("/Emprunt")){

            request.getSession().removeAttribute("minExemp");

            switch (operation) {
                case "create":
                    String isbn = request.getParameter("isbn");
                    if (!ServiceEmprunt.insert(request.getParameter("cin"), request.getParameter("isbn")))
                        request.getSession().setAttribute("minExemp", isbn);
                    break;
            }

            Vector<Emprunt> emprunts = DAOEmprunt.retrieve();
            request.setAttribute("emprunts", emprunts);
            try {
                request.getRequestDispatcher("/EmpruntsList.jsp").forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }

        }

    }



    public void destroy() {
    }
}