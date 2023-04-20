package ma.fstm.ilisi2.projects.biblio.model.service;

import ma.fstm.ilisi2.projects.biblio.model.bo.Adherent;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOAdherent;

import java.util.Vector;

public class ServiceAdherent {

    public static boolean insert(String nom, String prenom, String cin) {
        if (!DAOAdherent.create(new Adherent(nom, prenom, cin)))
            return false;
        return true;
    }

    public static Vector<Adherent> retrieve() {
        return DAOAdherent.retrieve();
    }

    public static Adherent retrieve(String cin) {
        if(!cin.isEmpty())
            return DAOAdherent.retrieve(cin);
        return null;
    }

    public static Adherent remove(Integer id) {
        if (!DAOAdherent.delete(new Adherent(id, null, null, null)))
            return DAOAdherent.retrieveAdh(id);
        return null;
    }

}
