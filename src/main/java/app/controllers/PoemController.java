package app.controllers;

import app.DAOs.PoemDAO;
import app.DTOs.PoemDTO;
import app.config.HibernateConfig;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;


import io.javalin.http.Context;
import java.util.List;

public class PoemController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
    PoemDAO poemDAO = PoemDAO.getInstance(emf);


    public void getAllPoems(Context ctx){
        List<PoemDTO> allDogs = poemDAO.getAllPoems();
        ctx.status(HttpStatus.FOUND);
        ctx.json(allDogs);
    }
    public void getPoemById(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDTO dog = poemDAO.getPoemById(id);
        if (dog != null) {
            ctx.status(HttpStatus.FOUND);
            ctx.json(dog);
        }else {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.result("Poem with id " +  id + " not found");
        }
    }
    public void createPoem(Context ctx){
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        PoemDTO createdPoem = poemDAO.createPoem(poemDTO);
        ctx.status(HttpStatus.CREATED);
        ctx.json(createdPoem);
    }
    public void updatePoem(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        PoemDTO updatedDog = poemDAO.updatePoem(id, poemDTO);
        ctx.status(HttpStatus.OK);
        ctx.json(updatedDog);
    }
    public void deletePoem(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        poemDAO.deletePoem(id);
        ctx.result("Poem with Id " +  id + " was deleted");
    }
}
