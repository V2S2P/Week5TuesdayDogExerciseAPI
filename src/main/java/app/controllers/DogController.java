package app.controllers;

import app.DAOs.DogDAO;
import app.DTOs.DogDTO;
import app.config.HibernateConfig;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;


import io.javalin.http.Context;
import java.util.List;

public class DogController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("dogdemo");
    DogDAO dogDAO = DogDAO.getInstance(emf);


    public void getAllDogs(Context ctx){
        List<DogDTO> allDogs = dogDAO.getAllDogs();
        ctx.status(HttpStatus.FOUND);
        ctx.json(allDogs);
    }
    public void getDogById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dog = dogDAO.getDogById(id);
        if (dog != null) {
            ctx.status(HttpStatus.FOUND);
            ctx.json(dog);
        }else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("Dog with id " +  id + " not found");
        }
    }
    public void createDog(Context ctx){
        DogDTO dogDTO = ctx.bodyAsClass(DogDTO.class);
        DogDTO createdDog = dogDAO.createDog(dogDTO);
        ctx.status(HttpStatus.CREATED);
        ctx.json(createdDog);
    }
    public void updateDog(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dogDTO = ctx.bodyAsClass(DogDTO.class);
        DogDTO updatedDog = dogDAO.updateDog(id, dogDTO);
        ctx.status(HttpStatus.OK);
        ctx.json(updatedDog);
    }
    public void deleteDog(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        dogDAO.deleteDog(id);
        ctx.result("Dog with Id " +  id + " was deleted");
    }
}
