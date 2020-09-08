package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    Category cetegory;

    @Before
    public void setUp(){
        this.cetegory = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 4L;
        cetegory.setId(idValue);
        assertEquals(idValue, cetegory.getId());

    }

    @Test
    public void getDescription() {

    }

    @Test
    public void getRecipes() {
    }
}