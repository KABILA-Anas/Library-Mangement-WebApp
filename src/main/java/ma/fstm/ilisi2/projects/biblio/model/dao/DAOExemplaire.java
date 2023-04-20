/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.fstm.ilisi2.projects.biblio.model.dao;

import java.util.List;

import ma.fstm.ilisi2.projects.biblio.model.bo.Exemplaire;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author EliteBook 840 G5
 */
public class DAOExemplaire {
    
    public static boolean create(Exemplaire E){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.save(E);
            tx.commit();
            return true;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }
    
    
    public static boolean delete(Exemplaire E){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.delete(E);
            tx.commit();
            return true;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }
    
    
    
    public static Exemplaire retreive(String isbn){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Exemplaire E = null;
            try {
                tx = sc.beginTransaction();
                //Query q = sc.createQuery ("from Livre");
                //L = (Livre) sc.get(Livre.class, isbn);
                Query query = sc.createQuery("FROM Exemplaire E1 where E1.livre.isbn= :isbnValue and E1.idExemp not in (SELECT e.idExemp FROM Exemplaire e JOIN e.emprunts m WHERE e.livre.isbn= :isbnValue and m.status=0)");
                query.setParameter("isbnValue", isbn);
                List<Exemplaire> exemplaires = query.list();
                if (exemplaires.size() != 0)
                    E = exemplaires.get(0);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return E;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }
    

    //Pour la verification avant la suppression d'un livre
    public static int exempDispo(String isbn){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            Exemplaire E = null;
            try {
                tx = sc.beginTransaction();
                Query query = sc.createQuery("FROM Exemplaire E1 where E1.livre.isbn= :isbnValue and E1.idExemp not in (SELECT e.idExemp FROM Exemplaire e JOIN e.emprunts m WHERE e.livre.isbn= :isbnValue)");       
                query.setParameter("isbnValue", isbn);
                List<Exemplaire> exemplaires = query.list();
                tx.commit();
                return exemplaires.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return 0;
        }
        return 0;
    }

    public static int exempNonEmp(String isbn){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Exemplaire E = null;
            try {
                tx = sc.beginTransaction();
                Query query = sc.createQuery("FROM Exemplaire E1 where E1.livre.isbn= :isbnValue and E1.idExemp not in (SELECT e.idExemp FROM Exemplaire e JOIN e.emprunts m WHERE e.livre.isbn= :isbnValue and m.status=0)");
                query.setParameter("isbnValue", isbn);
                List<Exemplaire> exemplaires = query.list();
                tx.commit();
                return exemplaires.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return 0;
        }
        return 0;
    }
    
    
    public static void main(String[] args){
        retreive("444");
    }
    
}
