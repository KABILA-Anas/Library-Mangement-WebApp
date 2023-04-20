package ma.fstm.ilisi2.projects.biblio.model.service;

import ma.fstm.ilisi2.projects.biblio.model.bo.Adherent;
import ma.fstm.ilisi2.projects.biblio.model.bo.Emprunt;
import ma.fstm.ilisi2.projects.biblio.model.bo.Exemplaire;
import ma.fstm.ilisi2.projects.biblio.model.bo.Livre;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOAdherent;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOEmprunt;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOExemplaire;
import ma.fstm.ilisi2.projects.biblio.model.dao.DAOLivre;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;

public class ServiceEmprunt {

    public static boolean insert(String cin, String isbn) {
        if (cin.isEmpty() || isbn.isEmpty())
            return false;
        Livre L = DAOLivre.retrieveLivre(isbn);
        if(L.getNbrexemp() == 0)
            return false;
        Adherent adherent = DAOAdherent.retrieve(cin);
        Exemplaire exemplaire = DAOExemplaire.retreive(isbn);
        if (adherent == null || exemplaire == null)
            return false;
        if (!DAOEmprunt.create(new Emprunt(exemplaire, adherent, new Date())))
            return false;
        L.setNbrexemp(L.getNbrexemp() - 1);
        DAOLivre.update(L);
        return true;
    }

    public static Vector<Emprunt> retrieve() {
        return DAOEmprunt.retrieve();
    }

    public static boolean updateStatus(Integer id) {
        Emprunt emprunt = DAOEmprunt.retrieve(id);
        LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (emprunt.getDateRet().compareTo(currentDate) < 0)
            emprunt.setStatus(-1);
        else
            emprunt.setStatus(1);
        if (!DAOEmprunt.update(emprunt))
            return false;
        Livre L = DAOLivre.retrieveLivre(emprunt.getExemplaire().getLivre().getIsbn());
        L.setNbrexemp(L.getNbrexemp() + 1);
        DAOLivre.update(L);
        return true;
    }

}
