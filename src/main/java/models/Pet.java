package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    private Integer id;
    private Category category;
    private String name;
    private ArrayList<String> photoUrls;
    private ArrayList<Tag> tags;
    private String status;

    public static Pet getPet(Integer id, String name, ArrayList<String> photoUrls) {
        return Pet.builder()
                .id(id)
                .name(name)
                .photoUrls(photoUrls)
                .build();
    }
}
