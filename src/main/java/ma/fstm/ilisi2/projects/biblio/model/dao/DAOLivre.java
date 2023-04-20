/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi2.projects.biblio.model.dao;

import ma.fstm.ilisi2.projects.biblio.model.bo.Livre;
//import org.hibernate.HibernateException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;

/**
 *
 * @author ilisi
 */
public class DAOLivre {

    public static boolean create(Livre L) {
        Transaction tx = null;
        try {
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.save(L);
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }





    public static ArrayList<Livre> retrieve(){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            ArrayList<Livre> L = null;
            try {
                tx = sc.beginTransaction();
                //Query q = sc.createQuery ("from Livre");
                L = new ArrayList<Livre>(sc.createQuery ("from Livre").list());
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return L;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }

    public static ArrayList<Livre> retrieve(String auteur){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            ArrayList<Livre> L = null;
            try {
                tx = sc.beginTransaction();
                String hql = "FROM Livre L WHERE L.auteur = :auteur";
                Query query = sc.createQuery(hql);
                query.setParameter("auteur", auteur);
                L = (ArrayList<Livre>) query.getResultList();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return L;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }


    public static Livre retrieveLivre(String isbn){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Livre L = null;
            try {
                tx = sc.beginTransaction();
                L = sc.get(Livre.class, isbn);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return L;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }



    public static boolean update(Livre L){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.update(L);
            tx.commit();
            return true;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }



    public static boolean delete(Livre L){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            L = sc.get(Livre.class, L.getIsbn());
            sc.delete(L);
            tx.commit();
            return true;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }

}
