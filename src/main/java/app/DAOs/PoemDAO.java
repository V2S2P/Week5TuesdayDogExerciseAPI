package app.DAOs;

import app.DTOs.PoemDTO;
import app.entities.Poem;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PoemDAO {

    private static PoemDAO instance;
    private static EntityManagerFactory emf;

    private PoemDAO() {}

    public static PoemDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new PoemDAO();
            PoemDAO.emf = emf;
        }
        return instance;
    }

    public List<PoemDTO> getAllPoems() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Poem> query = em.createQuery("SELECT d FROM Poem d", Poem.class);
            return PoemDTO.toDTOList(query.getResultList());
        }
    }

    public Poem getPoemById(int id) {
        try (var em = emf.createEntityManager()) {
            return em.find(Poem.class, id);  // 👈 return entity only
        }
    }

    public Poem createPoem(Poem poem) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(poem);
            em.getTransaction().commit();
            return poem;  // 👈 return entity
        }
    }

    public Poem updatePoem(int id, String newText) {
        try (var em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);
            if (poem == null) return null;

            em.getTransaction().begin();
            poem.setPoem(newText);   // 👈 update only the field(s)
            em.merge(poem);
            em.getTransaction().commit();

            return poem;  // 👈 return updated entity
        }
    }

    public boolean deletePoem(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Poem poem = em.find(Poem.class, id);
            if (poem != null) {
                em.remove(poem);
                em.getTransaction().commit();
                return true;   // 👈 deleted successfully
            }
            em.getTransaction().rollback();
            return false;  // 👈 nothing deleted
        }
    }

    public boolean deleteAllPoems() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Poem").executeUpdate();
            //em.createNativeQuery("ALTER TABLE Poem RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
