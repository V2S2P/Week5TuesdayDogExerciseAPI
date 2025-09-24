package app.controllers;

import app.DAOs.PoemDAO;
import app.DTOs.PoemDTO;
import app.config.HibernateConfig;
import app.entities.Poem;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;


import io.javalin.http.Context;

import java.util.List;

public class PoemController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
    PoemDAO poemDAO = PoemDAO.getInstance(emf);


    public void getAllPoems(Context ctx) {
        List<PoemDTO> allDogs = poemDAO.getAllPoems();
        ctx.status(HttpStatus.FOUND);
        ctx.json(allDogs);
    }

    public void getPoemById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Poem poem = poemDAO.getPoemById(id);   // 👈 entity from DAO
        if (poem != null) {
            PoemDTO dto = new PoemDTO(poem);   // 👈 convert here
            ctx.status(HttpStatus.OK).json(dto);
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Poem with id " + id + " not found");
        }
    }

    public void createPoem(Context ctx) {
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);

        //Convert DTO to Entity
        Poem poem = new Poem(poemDTO);

        //Save Entity
        Poem createdPoem = poemDAO.createPoem(poem);

        //Convert Entity to DTO
        PoemDTO responseDTO = new PoemDTO(createdPoem);
        ctx.status(HttpStatus.CREATED).json(responseDTO);
    }

    public void updatePoem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
        Poem updatedPoem = poemDAO.updatePoem(id, poemDTO.getPoem());

        if (updatedPoem != null) {
            PoemDTO responseDTO = new PoemDTO(updatedPoem);
            ctx.status(HttpStatus.OK).json(responseDTO);
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Poem with id " + id + " not found");
        }
    }

    public void deletePoem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deletedPoem = poemDAO.deletePoem(id);

        if (deletedPoem) {
            ctx.status(HttpStatus.NO_CONTENT).result("Poem with id " + id + " was deleted");
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Poem with id " + id + " not found");
        }
    }

    public void deleteAllPoems(Context ctx) {
        boolean deletedPoems = poemDAO.deleteAllPoems();
        if (deletedPoems) {
            ctx.status(HttpStatus.NO_CONTENT).result("All Poems were deleted");
        } else {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .result("Failed to delete all poems");
        }
    }
}