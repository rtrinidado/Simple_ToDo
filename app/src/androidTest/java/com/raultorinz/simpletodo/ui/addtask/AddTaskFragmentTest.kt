package com.raultorinz.simpletodo.ui.addtask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.raultorinz.simpletodo.R
import com.raultorinz.simpletodo.ServiceLocator
import com.raultorinz.simpletodo.data.source.DefaultTaskRepository
import com.raultorinz.simpletodo.data.source.TasksRepository
import com.raultorinz.simpletodo.data.source.room.Task
import com.raultorinz.simpletodo.data.source.room.TaskRoomDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

class AddTaskFragmentTest {
    private lateinit var repository: TasksRepository
    private lateinit var db: TaskRoomDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TaskRoomDatabase::class.java
        ).build()

        repository = DefaultTaskRepository(db)
        ServiceLocator.tasksRepository = repository

        val task = Task(false, "Prueba 1", "Esta es una prueba", "11/01/2022")
        val task2 = Task(true, "Prueba 2", "Esta tarea está completa", "")

        repository.insert(task)
        repository.insert(task2)
    }

    @Test
    fun startFragmentWithArgs_showDetailsOfTask() {
        val args = Bundle().apply {
            putLong("idTask", 1L)
        }
        val scenario = launchFragmentInContainer<AddTaskFragment>(args, R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.nameTaskEdit)).check(matches(withText("Prueba 1")))
        onView(withId(R.id.descriptionEdit)).check(matches(withText("Esta es una prueba")))
        onView(withId(R.id.completeCheck)).check(matches(isNotChecked()))
        onView(withId(R.id.dateTask)).check(matches(withText("11/01/2022")))
    }

    @Test
    fun startFragmentWithoutArgs_showDetailsOfTask() {
        val scenario =
            launchFragmentInContainer<AddTaskFragment>(Bundle(), R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.nameTaskEdit)).check(matches(withText("")))
        onView(withId(R.id.descriptionEdit)).check(matches(withText("")))
        onView(withId(R.id.completeCheck)).check(matches(isNotChecked()))
        onView(withId(R.id.dateTask)).check(matches(withText("")))
    }

    @Test
    fun startFragmentWithCompleteTaskArgs_showDetailsOfTask() {
        val args = Bundle().apply {
            putLong("idTask", 2L)
        }
        val scenario = launchFragmentInContainer<AddTaskFragment>(args, R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.nameTaskEdit)).check(matches(withText("Prueba 2")))
        onView(withId(R.id.descriptionEdit)).check(matches(withText("Esta tarea está completa")))
        onView(withId(R.id.completeCheck)).check(matches(isChecked()))
        onView(withId(R.id.dateTask)).check(matches(withText("")))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}