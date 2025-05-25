package com.example.colors

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ColorDatabaseTest {

    private lateinit var db: ColorDatabase
    private lateinit var dao: ColorDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ColorDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.colorDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetAll() {
        val colors = listOf(
            Color(name = "Red", hex = "#FF0000"),
            Color(name = "Blue", hex = "#0000FF"),
            Color(name = "Green", hex = "#00FF00")
        )
        colors.forEach { dao.insert(it) }

        val result = dao.getAll()
        Assert.assertEquals(3, result.size)

        val red = result.find { it.name == "Red" }
        val blue = result.find { it.name == "Blue" }
        val green = result.find { it.name == "Green" }

        Assert.assertNotNull(red)
        Assert.assertEquals("#FF0000", red?.hex)

        Assert.assertNotNull(blue)
        Assert.assertEquals("#0000FF", blue?.hex)

        Assert.assertNotNull(green)
        Assert.assertEquals("#00FF00", green?.hex)
    }

    @Test
    fun updateColor() {
        val blue = Color(name = "Blue", hex = "#0000FF")
        dao.insert(blue)

        val insertedBlue = dao.getAll().first { it.name == "Blue" }

        val updatedBlue = insertedBlue.copy(hex = "#1E90FF")
        dao.update(updatedBlue)

        val result = dao.getAll().first { it.name == "Blue" }
        Assert.assertEquals("#1E90FF", result.hex)
    }

    @Test
    fun deleteColor() {
        val green = Color(name = "Green", hex = "#00FF00")
        dao.insert(green)

        val insertedGreen = dao.getAll().first { it.name == "Green" }
        dao.delete(insertedGreen)

        val result = dao.getAll()
        Assert.assertTrue(result.none { it.name == "Green" })
    }
}

