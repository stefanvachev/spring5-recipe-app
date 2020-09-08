package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 *  This Will Bring the entire spring context, but only once for all tests in the class
 *  Except when we use the DirtiesContext Annotation, which is used to label a tst that modifies the data and can cause others to fai.
 */
@RunWith(SpringRunner.class)
@DataJpaTest //will bring up embedded database and configure spring data JPA
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void findByDescription() {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon"); //this is part of our data.sql file.
        assertEquals("Teaspoon", unitOfMeasure.get().getDescription());
    }

    @Test
    @Commit
    @DirtiesContext //if we don't do this, the next test will fail (provided we commit the transaction, else by default spring will rollback after each test)
    public void delete(){
        Optional<UnitOfMeasure> itemToDelete = unitOfMeasureRepository.findByDescription("Cup");
        unitOfMeasureRepository.delete(itemToDelete.get());
        Optional<UnitOfMeasure> deletedItem = unitOfMeasureRepository.findByDescription("Cup");
        assertFalse(deletedItem.isPresent());

    }

    @Test
    public void findByDescriptionCup() {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Cup"); //this is part of our data.sql file.
        assertEquals("Cup", unitOfMeasure.get().getDescription());
    }
}