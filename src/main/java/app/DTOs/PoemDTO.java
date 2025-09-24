package app.DTOs;

import app.entities.Poem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoemDTO {
    private Integer id;

    private String poem;

    public PoemDTO(Poem poem){
        this.id= poem.getId();
        this.poem= poem.getPoem();
    }
    public PoemDTO(Integer id, String poem){
        this.id = id;
        this.poem = poem;
    }

    public static List<PoemDTO> toDTOList(List<Poem> haikuses) {
        return haikuses.stream().map(PoemDTO::new).toList();
    }
}
