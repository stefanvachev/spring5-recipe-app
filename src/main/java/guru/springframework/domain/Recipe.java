package guru.springframework.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Some RDBMS support Sequence, Auto Increment, Oracle older versions before 12 do not, but after 12 orcale should.
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    //mappedBy gives the property on the child class by which the relationship is mapped
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob //Will create blob  for non String types, if type is string, then clob
    private Byte[] image;

    //EnumType Ordinal, which is default, will persist as 1,2,3 - an index representing the way the enums are ordered in the class.
    //This is dangerous if you insert another enum in the class and its not the last one, existing indexes can break!
    //EnumType.STRING will get the name, and store that in the DB, much safer.
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"), //from this side of the relationship, this is the field
            inverseJoinColumns = @JoinColumn(name = "category_id") //from the other side of the relationship, this is the column.
    )
    private Set<Category> categories = new HashSet<>();

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        this.notes.setRecipe(this);
        //Allows to automatically set the bidirectional relationship
        //so we do not need to do this manually every time we set notes to recipes
    }

}

