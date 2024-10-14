package com.example.mynotks

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.mynotks.data.notesTest
import com.example.mynotks.data.tasksTest
import com.example.mynotks.ui.SemanticProperties
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.home.HomeDestination
import com.example.mynotks.ui.lists.ListEntryNavigation
import com.example.mynotks.ui.lists.ListUpdateDestination
import com.example.mynotks.ui.lists.ListsDetailDestination
import com.example.mynotks.ui.notes.NotesDetailDestination
import com.example.mynotks.ui.notes.NotesEntryDestination
import com.example.mynotks.ui.notes.NotesUpdateDestination
import com.example.mynotks.ui.toHexString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep


class NotksScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNotksNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            NotksApp(navController = navController)
        }
    }

    @Test
    fun notksNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun notksNavHost_verifyBackNavigationNotShownOnHomeScreen() {
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    //List Tests
    @Test
    fun notksNavHost_clickAddList_navigateToEntryListScreen() {
        navigateToEntryListScreen()

        navController.assertCurrentRouteName(ListEntryNavigation.route)
    }


    @Test
    fun notksNavHost_entryNewList_newListAddedToHomeScreen () {

        navigateToEntryListScreen()
        addNewList()

        navController.assertCurrentRouteName(HomeDestination.route)
        composeTestRule.onNodeWithText(tasksTest.listTitle).assertExists()
    }

    @Test
    fun notksNavHost_navigateToDetailListScreen() {
        navigateToDetailListScreen()
        navController.assertCurrentRouteName(ListsDetailDestination.routeWithArgs)
    }

    @Test
    fun notksNavHost_navigateToUpdateListScreen() {
        navigateToUpdateListScreen()
        navController.assertCurrentRouteName(ListUpdateDestination.routeWithArgs)
    }

    @Test
    fun notksNavHost_updateTitleList_assertTitleUpdated() {
        navigateToUpdateListScreen()
        val titleUpdate = "${tasksTest.listTitle} updated!!"
        val titlePlaceholder = composeTestRule.activity.getString(R.string.placeholder_list_title)

        composeTestRule.onNodeWithText(titlePlaceholder).performTextClearance()
        composeTestRule.onNodeWithText(titlePlaceholder).performTextInput(titleUpdate)

        val save = composeTestRule.activity.getString(R.string.check_icon)

        composeTestRule.onNodeWithContentDescription(save).performClick()

        navController.assertCurrentRouteName(HomeDestination.route)

        composeTestRule.onNodeWithText(titleUpdate).assertExists()
    }


    @Test
    fun notksNavHost_updateTaskList_assertTaskUpdated() {
        navigateToUpdateListScreen()

        val taskUpdate1 = "${tasksTest.listTasks[0].first} updated 1!!"
        val taskUpdate2 = "${tasksTest.listTasks[1].first} updated 2!!"
        val taskUpdate3 = "${tasksTest.listTasks[2].first} updated 3!!"

        val taskPlaceholder = composeTestRule.activity.getString(R.string.placeholder_list_task)

        composeTestRule.onNodeWithText(tasksTest.listTasks[0].first).performTextClearance()
        composeTestRule.onNodeWithText(taskPlaceholder).performTextInput(taskUpdate1)

        //add new empty task line - perform click FAB button
        val fabButton = composeTestRule.activity.getString(R.string.add_icon_description)
        composeTestRule.onNodeWithContentDescription(fabButton).performClick()
        sleep(500)
        composeTestRule.onNodeWithText(taskPlaceholder).performTextInput(taskUpdate2)

        //add other new empty task line - perform click FAB button
        composeTestRule.onNodeWithContentDescription(fabButton).performClick()
        sleep(500)
        composeTestRule.onNodeWithText(taskPlaceholder).performTextInput(taskUpdate3)

        //save changes
        val save = composeTestRule.activity.getString(R.string.check_icon)
        composeTestRule.onNodeWithContentDescription(save).performClick()

        navController.assertCurrentRouteName(HomeDestination.route)

        composeTestRule.onNodeWithText(tasksTest.listTitle).performClick()
        navController.assertCurrentRouteName(ListsDetailDestination.routeWithArgs)

        composeTestRule.onNodeWithText(taskUpdate1).assertExists()
        composeTestRule.onNodeWithText(taskUpdate2).assertExists()
        composeTestRule.onNodeWithText(taskUpdate3).assertExists()
    }

    @Test
    fun notksNavHost_updateListBackgroundColor_assertColorChange() {
        navigateToUpdateListScreen()

        val colorPicker = composeTestRule.activity.getString(R.string.color_picker)
        composeTestRule.onNodeWithContentDescription(colorPicker).performClick()
        sleep(500)

        //check color picker dialog is displayed
        colors.forEach { color ->
            composeTestRule.onNodeWithTag(color.toHexString()).assertIsDisplayed()
        }

        //select one color
        val firstColor = colors[0].toHexString()

        composeTestRule.onNodeWithTag(firstColor).performClick()

        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticProperties.tint, firstColor
            )).assertIsDisplayed()

        //selecto different color
        val secondColor = colors[7].toHexString()
        composeTestRule.onNodeWithContentDescription(colorPicker).performClick()
        sleep(500)

        composeTestRule.onNodeWithTag(secondColor).performClick()
        sleep(500)

        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticProperties.tint, secondColor
            )
        ).assertIsDisplayed()
    }

    @Test
    fun notksNavHost_deleteList_assertListDoesNotExist() {
        navigateToDetailListScreen()

        val delete = composeTestRule.activity.getString(R.string.delete_icon_description)

        composeTestRule.onNodeWithContentDescription(delete).performClick()

        //check alert dialog showed and click confirm button
        sleep(500)
        val warning = composeTestRule.activity.getString(R.string.warning)

        composeTestRule.onNodeWithText(warning).assertIsDisplayed()

        val confirm = composeTestRule.activity.getString(R.string.confirm)
        composeTestRule.onNodeWithText(confirm).performClick()

        sleep(500)

        navController.assertCurrentRouteName(HomeDestination.route)
        composeTestRule.onNodeWithText(tasksTest.listTitle).assertDoesNotExist()

    }



    //Note Screens Tests
    @Test
    fun notksNavHost_clickAddNote_navigateToEntryNoteScreen() {
        navigateToEntryNoteScreen()

        navController.assertCurrentRouteName(NotesEntryDestination.route)
    }

    @Test
    fun notksNavHost_entryNewNote_newNoteAddedToHomeScreen () {
        navigateToEntryNoteScreen()
        addNewNote()

        navController.assertCurrentRouteName(HomeDestination.route)
        composeTestRule.onNodeWithText(notesTest.title).assertIsDisplayed()
    }

    @Test
    fun notksNavHost_navigateToDetailNoteScreen() {
        navigateToDetailNoteScreen()

        navController.assertCurrentRouteName(NotesDetailDestination.routeWithArgs)
    }

    @Test
    fun notksNavHost_navigateToUpdateNoteScreen() {
        navigateToUpdateNoteScreen()

        navController.assertCurrentRouteName(NotesUpdateDestination.routeWithArgs)
    }


    @Test
    fun notksNavHost_updateTitleNote_assertTitleUpdated() {
        navigateToUpdateNoteScreen()

        val titleUpdate = "${notesTest.title} - updated!!"

        val title = composeTestRule.activity.getString(R.string.placeholder_note_title)
        composeTestRule.onNodeWithText(title).performTextClearance()
        composeTestRule.onNodeWithText(title).performTextInput(titleUpdate)

        val save = composeTestRule.activity.getString(R.string.check_icon)

        composeTestRule.onNodeWithContentDescription(save).performClick()

        navController.assertCurrentRouteName(HomeDestination.route)

        composeTestRule.onNodeWithText(titleUpdate).assertIsDisplayed()
    }

    @Test
    fun notksNavHost_updateNoteBody_assertNoteUpdated() {
        navigateToUpdateNoteScreen()
        val newBodyNote = notesTest.notes.substring(notesTest.notes.length / 2)

        val body = composeTestRule.activity.getString(R.string.placeholder_note_body)
        composeTestRule.onNodeWithText(body).performTextClearance()
        composeTestRule.onNodeWithText(body).performTextInput(newBodyNote)

        val save = composeTestRule.activity.getString(R.string.check_icon)
        composeTestRule.onNodeWithContentDescription(save).performClick()

        navController.assertCurrentRouteName(HomeDestination.route)

        composeTestRule.onNodeWithText(notesTest.title).performClick()

        navController.assertCurrentRouteName(NotesDetailDestination.routeWithArgs)

        composeTestRule.onNodeWithText(newBodyNote).assertIsDisplayed()
    }

    @Test
    fun notksNavHost_updateNoteBackgroundColor_assertColorChange() {
        navigateToUpdateNoteScreen()

        val colorPicker = composeTestRule.activity.getString(R.string.color_picker)
        composeTestRule.onNodeWithContentDescription(colorPicker).performClick()

        sleep(250)

        //check color dialog is displayed
        colors.forEach {color ->
            composeTestRule.onNodeWithTag(color.toHexString()).assertIsDisplayed()
        }

        //select one color and asserts change
        val firstColor = colors[1].toHexString()

        composeTestRule.onNodeWithTag(firstColor).performClick()
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticProperties.tint, firstColor
            )
        ).assertIsDisplayed()

        //select another color and asserts change
        val secondColor = colors[8].toHexString()

        composeTestRule.onNodeWithContentDescription(colorPicker).performClick()
        composeTestRule.onNodeWithTag(secondColor).performClick()

        composeTestRule.onNode(
            SemanticsMatcher.expectValue(
                SemanticProperties.tint, secondColor
            )
        ).assertIsDisplayed()
    }

    @Test
    fun notksNavHost_deleteNote_assertListDoesNotExist() {
        navigateToDetailNoteScreen()

        val delete = composeTestRule.activity.getString(R.string.delete_icon_description)
        composeTestRule.onNodeWithContentDescription(delete).performClick()

        //check alert dialog is displayed and confirm delete action
        sleep(250)
        val warning = composeTestRule.activity.getString(R.string.warning)
        composeTestRule.onNodeWithText(warning).assertIsDisplayed()

        val confirm = composeTestRule.activity.getString(R.string.confirm)
        composeTestRule.onNodeWithText(confirm).performClick()

        //
        sleep(250)
        navController.assertCurrentRouteName(HomeDestination.route)

        composeTestRule.onNodeWithText(notesTest.title).assertDoesNotExist()
//        navigateToDetailListScreen()
//
//        val delete = composeTestRule.activity.getString(R.string.delete_icon_description)
//
//        composeTestRule.onNodeWithContentDescription(delete).performClick()
//
//        //check alert dialog showed and click confirm button
//        sleep(500)
//        val warning = composeTestRule.activity.getString(R.string.warning)
//
//        composeTestRule.onNodeWithText(warning).assertIsDisplayed()
//
//        val confirm = composeTestRule.activity.getString(R.string.confirm)
//        composeTestRule.onNodeWithText(confirm).performClick()
//
//        sleep(500)
//
//        navController.assertCurrentRouteName(HomeDestination.route)
//        composeTestRule.onNodeWithText(tasksTest.listTitle).assertDoesNotExist()

    }


    //List navigation helpers
    private fun navigateToEntryListScreen() {
        val fabText = composeTestRule.activity.getString(R.string.fab_home_description)
        composeTestRule
            .onNodeWithContentDescription(fabText)
            .performClick()
        val miniFabList = composeTestRule.activity.getString(R.string.mini_fab_list_label)
        composeTestRule
            .onNodeWithContentDescription(miniFabList)
            .performClick()
    }

    private fun navigateToDetailListScreen() {
        navigateToEntryListScreen()
        addNewList()
        composeTestRule.onNodeWithText(tasksTest.listTitle).performClick()
    }

    private fun navigateToUpdateListScreen() {
        navigateToDetailListScreen()
        val edit = composeTestRule.activity.getString(R.string.edit_icon_description)

        composeTestRule.onNodeWithContentDescription(edit).performClick()
    }

    //Note navigation helpers

    private fun navigateToEntryNoteScreen() {
        val fabText = composeTestRule.activity.getString(R.string.fab_home_description)
        composeTestRule
            .onNodeWithContentDescription(fabText)
            .performClick()
        val miniFabList = composeTestRule.activity.getString(R.string.mini_fab_note_label)
        composeTestRule
            .onNodeWithContentDescription(miniFabList)
            .performClick()
    }

    private fun navigateToDetailNoteScreen() {
        navigateToEntryNoteScreen()
        addNewNote()
        composeTestRule.onNodeWithText(notesTest.title).performClick()
    }

    private fun navigateToUpdateNoteScreen() {
        navigateToDetailNoteScreen()

        val edit = composeTestRule.activity.getString(R.string.edit_icon_description)
        composeTestRule.onNodeWithContentDescription(edit).performClick()
    }





    private fun addNewList(newTitle: String? = null, newTask: String? = null) {
        val title = newTitle ?: composeTestRule.activity.getString(R.string.placeholder_list_title)
        val task= newTask ?: composeTestRule.activity.getString(R.string.placeholder_list_task)

        val save = composeTestRule.activity.getString(R.string.check_icon)

        composeTestRule.onNodeWithText(title).performTextInput(tasksTest.listTitle)
        composeTestRule.onNodeWithText(task).performTextInput(tasksTest.listTasks[0].first)

        composeTestRule.onNodeWithContentDescription(save).performClick()
    }

    private fun addNewNote(newTitle: String? = null, newNote: String? = null) {
        val title = newTitle ?: composeTestRule.activity.getString(R.string.placeholder_note_title)
        val note = newNote ?: composeTestRule.activity.getString(R.string.placeholder_note_body)

        val save = composeTestRule.activity.getString(R.string.check_icon)

        composeTestRule.onNodeWithText(title).performTextInput(notesTest.title)
        composeTestRule.onNodeWithText(note).performTextInput(notesTest.notes)

        composeTestRule.onNodeWithContentDescription(save).performClick()
    }


}