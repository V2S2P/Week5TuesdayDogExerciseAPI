package app.entities;

import app.DTOs.DogDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String breed;
    private String gender;
    private int age;

    public Dog(DogDTO dogDTO) {
        this.id=dogDTO.getId();
        this.name=dogDTO.getName();
        this.breed=dogDTO.getBreed();
        this.gender=dogDTO.getGender();
        this.age=dogDTO.getAge();
    }
}
