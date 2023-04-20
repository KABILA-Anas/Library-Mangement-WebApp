package ma.fstm.ilisi2.projects.biblio.model.service;

import ma.fstm.ilisi2.projects.biblio.model.bo.Exemplaire;
import ma.fstm.ilisi2.projects.biblio.model.bo.Livre;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOExemplaire;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOLivre;

import java.util.ArrayList;

public class ServiceLivre {

    public  static boolean insert(String isbn, String title, String auteur, Integer nbrExemp) {

        Livre L = new Livre(isbn, title, auteur, nbrExemp);

        if (!DAOLivre.create(L))
            return false;

        for(int i = 0; i < nbrExemp; i++)
                DAOExemplaire.create(new Exemplaire(L));

        return true;

    }

    public static ArrayList<Livre> retrieve() {
        return DAOLivre.retrieve();
    }

    public static ArrayList<Livre> retrieve(String auteur) {
        if (!auteur.isEmpty())
            return DAOLivre.retrieve(auteur);
        return null;
    }

    public static boolean update(String isbn, String title, String auteur, Integer nbrExemp) {
        if (!DAOLivre.update(new Livre(isbn, title, auteur, nbrExemp)))
            return false;
        return true;
    }

    public static boolean remove(String isbn) {

        Livre L = DAOLivre.retrieveLivre(isbn);

        if(L.getExemplaires().size() != DAOExemplaire.exempDispo(isbn))
            return false;

        for (Object e : L.getExemplaires())
            DAOExemplaire.delete((Exemplaire) e);

        DAOLivre.delete(new Livre(isbn, null, null, null));

        return true;

    }

}
