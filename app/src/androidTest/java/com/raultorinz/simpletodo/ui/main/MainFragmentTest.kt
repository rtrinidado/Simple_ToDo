package com.raultorinz.simpletodo.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import org.junit.Test
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
import org.mockito.Mockito
import java.io.IOException
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition

import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.mockito.Mockito.verify


class MainFragmentTest {
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

        val task = Task(false, "Prueba 1", "", "")
        val task2 = Task(true, "Prueba 2", "", "")
        repository.insert(task)
        repository.insert(task2)
    }

    @Test
    fun getTodoElement_isInCorrectList() {
        val scenario = launchFragmentInContainer<MainFragment>(Bundle(), R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.toDoList)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("Prueba 1"))
                )
            )
        )
    }

    @Test
    fun getDoneElement_isInCorrectList() {
        val scenario = launchFragmentInContainer<MainFragment>(Bundle(), R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.doneList)).check(
            matches(
                atPosition(
                    0,
                    hasDescendant(withText("Prueba 2"))
                )
            )
        )
    }

    @Test
    fun clickAddTask_navigateToAddTaskFragment() {
        val scenario = launchFragmentInContainer<MainFragment>(Bundle(), R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.addButton)).perform(click())

        verify(navController).navigate(R.id.mainFragment_to_addTaskFragment)
    }

    @Test
    fun clickTask_navigateToAddTaskFragment() {
        val scenario = launchFragmentInContainer<MainFragment>(Bundle(), R.style.Theme_SimpleToDo)
        val navController = Mockito.mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        val action = MainFragmentDirections.mainFragmentToAddTaskFragment()
        action.idTask = 1

        onView(withId(R.id.toDoList)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        verify(navController).navigate(action)
    }

    private fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}