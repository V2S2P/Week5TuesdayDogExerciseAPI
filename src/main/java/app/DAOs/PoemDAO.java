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

    public PoemDTO getPoemById(int id) {
        try (var em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);
            return poem != null ? new PoemDTO(poem) : null;
        }
    }

    public PoemDTO createPoem(PoemDTO poemDTO) {
        Poem poem = new Poem(poemDTO);
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(poem);
            em.getTransaction().commit();
            return new PoemDTO(poem);
        }
    }

    public PoemDTO updatePoem(int id, PoemDTO poemDTO) {
        try (var em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);
            if (poem == null) return null;

            em.getTransaction().begin();
            poem.setPoem(poemDTO.getPoem());
            em.merge(poem);
            em.getTransaction().commit();

            return new PoemDTO(poem);
        }
    }

    public void deletePoem(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Poem poem = em.find(Poem.class, id);
            if (poem != null) {
                em.remove(poem);
            }
            em.getTransaction().commit();
        }
    }

    public void deleteAllPoems() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Poem").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
