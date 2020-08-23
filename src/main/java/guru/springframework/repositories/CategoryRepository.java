package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    /* Spring JPA will automatically create the method that matches the convention
       of findBy followed by property name or names of the Domain Class (in this case the Category.description.
       We do not need to implement the method at all. Just needs to be defined in the interface.
       Using Java Optional we can better handle nulls.
     */

    Optional<Category> findByDescription(String description);


}
