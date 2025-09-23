package app.DAOs;

import app.DTOs.DogDTO;
import app.entities.Dog;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DogDAO {

    private static DogDAO instance;
    private static EntityManagerFactory emf;

    private DogDAO() {}

    public static DogDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new DogDAO();
            DogDAO.emf = emf;
        }
        return instance;
    }

    public List<DogDTO> getAllDogs() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
            return DogDTO.toDTOList(query.getResultList());
        }
    }

    public DogDTO getDogById(int id) {
        try (var em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null ? new DogDTO(dog) : null;
        }
    }

    public DogDTO createDog(DogDTO dogDTO) {
        Dog dog = new Dog(dogDTO);
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(dog);
            em.getTransaction().commit();
            return new DogDTO(dog);
        }
    }

    public DogDTO updateDog(int id, DogDTO dogDTO) {
        try (var em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            if (dog == null) return null;

            em.getTransaction().begin();
            dog.setName(dogDTO.getName());
            dog.setBreed(dogDTO.getBreed());
            dog.setGender(dogDTO.getGender());
            dog.setAge(dogDTO.getAge());
            em.merge(dog);
            em.getTransaction().commit();

            return new DogDTO(dog);
        }
    }

    public void deleteDog(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                em.remove(dog);
            }
            em.getTransaction().commit();
        }
    }

    public void deleteAllDogs() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
