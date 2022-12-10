package it.feio.android.omninotes.test2022;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import it.feio.android.omninotes.BaseUnitTest;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.Category;
import it.feio.android.omninotes.models.Note;

public class CategoryTest extends BaseUnitTest {
    private DbHelper db;

    @Before
    public void setup() {
        this.db = DbHelper.getInstance(getContextMock());
    }

    @Test
    public void getCategoryTest() {
        Note note = getNote(1L, "title", "content");
        Category category = new Category();
        note.setCategory(category);
        assertEquals(category, note.getCategory());
    }
}