package ru.innopolis.refrigerator.core.db.hibernate.element;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import ru.innopolis.refrigerator.core.db.hibernate.util.HibernateUtil;


public class ElementDAOImpl<E> implements ElementDAO<E> {
    private static final Logger logger = LogManager.getLogger(ElementDAOImpl.class.getName());

    private Class<E> elementClass;

    public ElementDAOImpl(Class<E> elementClass) {
        this.elementClass = elementClass;
    }

    /**
     * Добавляет элемент в БД.
     *
     * @param el
     *            - элемент для добавления.
     */
    @Override
    public E addElement(E el) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(el);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("I can not add an item to the database" + e.toString());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return el;
    }

    /**
     * Удаляет элемент в БД.
     *
     * @param el
     *            - элемент для удаления.
     */
    @Override
    public void deleteElement(E el) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(el);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("I can not remove an item to the database" + e.toString());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Запращивает все элементы из БД.
     *
     * @return {@link Collection} элементов {@link E} из БД.
     */
    @Override
    public Collection<E> getAllElements() {
        List<E> els = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Transaction transaction = session.beginTransaction();
            // TODO
            DetachedCriteria criteria = DetachedCriteria.forClass(elementClass);
            els = criteria.getExecutableCriteria(session).list();
            // els = session.createCriteria(elementClass).list();
            // transaction.commit();
        }
        catch (Exception e) {
            logger.error("I can not query all items in the database" + e.toString());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return els;
    }

    /**
     * Запращивает элемент по Id.
     *
     * @param elId
     *            - элемент для запроса.
     * @return элемент {@link E} из БД.
     */
    public E getElementByID(long elId) {
        return getElementByID(Long.valueOf(elId));
    }

    /**
     * Запращивает элемент по Id.
     *
     * @param elId
     *            - элемент для запроса.
     * @return элемент {@link E} из БД.
     */
    @Override
    public E getElementByID(Long elId) {
        E el = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Transaction transaction = session.beginTransaction();
            el = session.get(elementClass, elId);
            // transaction.commit();
        }
        catch (Exception e) {
            logger.error("I can not query item in the database" + e.toString());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return el;
    }

    /**
     * Обновляет элемент в БД.
     *
     * @param el
     *            - элемент для добавления.
     */
    @Override
    public void updateElement(E el) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(el);
            transaction.commit();
        }
        catch (Exception e) {
            logger.error("I can not update an item to the database" + e.toString());
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

}