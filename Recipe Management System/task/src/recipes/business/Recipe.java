package recipes.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "description", nullable = false)
    private String description;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> directions;

    @Column(name = "author", nullable = false)
    private String author;
}
