/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.fstm.ilisi2.projects.biblio.model.dao;

import java.util.List;
import java.util.Vector;

import ma.fstm.ilisi2.projects.biblio.model.bo.Adherent;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author EliteBook 840 G5
 */
public class DAOAdherent {
    
    public static boolean create(Adherent C){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            String hql = "FROM Adherent c WHERE c.cin = :cin";
            Query query = sc.createQuery(hql);
            query.setParameter("cin", C.getCin());

            List resultList = query.list();
            if(resultList.size() != 0) {
                tx.rollback();
                return false;
            }
            sc.save(C);
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }
    
    public static boolean delete(Adherent A){
        Transaction tx = null;
        Adherent C = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            /*String hql = "FROM Adherent c WHERE c.cin = :cin";
            Query query = sc.createQuery(hql);
            query.setParameter("cin", A.getCin());

            List resultList = query.list();
            if(resultList.size() != 0)
                C = (Adherent) resultList.get(0);*/
            C = sc.get(Adherent.class, A.getIdClient());
            sc.delete(C);
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }

    public static boolean update(Adherent C) {
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = sc.beginTransaction();
            sc.saveOrUpdate(C);
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
            System.err.println(e);
            return false;
        }
    }
    
    public static Vector<Adherent> retrieve(){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Vector<Adherent> C = null;
            try {
                tx = sc.beginTransaction();
                //Query q = sc.createQuery ("from Livre");
                C = new Vector<Adherent>(sc.createQuery ("from Adherent").list());
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return C;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }

    public static Adherent retrieveAdh(Integer id){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Adherent A = null;
            try {
                tx = sc.beginTransaction();
                //Query q = sc.createQuery ("from Livre");
                A = (Adherent) sc.get(Adherent.class, id);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return A;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }
    
    public static Adherent retrieve(String cin){
        Transaction tx = null;
        try{
            Session sc = HibernateUtil.getSessionFactory().getCurrentSession();
            //tx = sc.beginTransaction();
            Adherent C = null;
            try {
                tx = sc.beginTransaction();
                String hql = "FROM Adherent c WHERE c.cin = :cin";
                Query query = sc.createQuery(hql);
                query.setParameter("cin", cin);

                List resultList = query.list();
                if(resultList.size() != 0)
                    C = (Adherent) resultList.get(0);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return C;
        }catch(HibernateException e){
            tx.rollback();
            System.err.println(e);
            return null;
        }
    }
    
}
