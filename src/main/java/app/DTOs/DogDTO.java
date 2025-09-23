package app.DTOs;

import app.entities.Dog;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DogDTO {
    private Integer id;

    private String name;
    private String breed;
    private String gender;
    private int age;

    public DogDTO(Dog dog){
        this.id=dog.getId();
        this.name=dog.getName();
        this.breed=dog.getBreed();
        this.gender=dog.getGender();
        this.age=dog.getAge();
    }
    public DogDTO(Integer id, String name, String breed, String gender, int age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }

    public static List<DogDTO> toDTOList(List<Dog> dogs) {
        return dogs.stream().map(DogDTO::new).toList();
    }
}
