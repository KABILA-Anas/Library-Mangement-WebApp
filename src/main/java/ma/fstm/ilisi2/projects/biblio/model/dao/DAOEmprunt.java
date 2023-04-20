/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.fstm.ilisi2.projects.biblio.model.dao;

import java.util.Vector;

import ma.fstm.ilisi2.projects.biblio.model.bo.Emprunt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author EliteBook 840 G5
 */
public class DAOEmprunt {
    
    public static boolean create(Emprunt E){
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

    public static boolean update(Emprunt E){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.update(E);
            tx.commit();
            return true;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }
    
    public static Vector<Emprunt> retrieve(){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            Vector<Emprunt> L = null;
            try {
                tx = sc.beginTransaction();
                L = new Vector<Emprunt>(sc.createQuery ("from Emprunt").list());
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

    public static Emprunt retrieve(Integer id){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            Emprunt E = null;
            try {
                tx = sc.beginTransaction();
                E = sc.get(Emprunt.class, id);
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
    
}
