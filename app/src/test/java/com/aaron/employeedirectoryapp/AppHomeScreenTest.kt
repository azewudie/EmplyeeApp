package com.aaron.employeedirectoryapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aaron.employeedirectoryapp.data.DataRepository
import com.aaron.employeedirectoryapp.di.appresources.AppResources
import com.aaron.employeedirectoryapp.ui.screens.apphome.AppHomeScreenViewModel
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AppHomeScreenTest {
    @MockK
    private lateinit var dataRepository: DataRepository

    @MockK
    private lateinit var appResources: AppResources
    private var viewModel: AppHomeScreenViewModel? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRul = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // Mock behavior of AppResources methods
        every { appResources.getString(R.string.button_text) } returns "Refresh"
        every { appResources.getString(R.string.employee_directory_empty_text) } returns "Employee Directory is Empty"
        viewModel = AppHomeScreenViewModel(appResources, dataRepository)
        setInitialState()
        Dispatchers.setMain(dispatcher)

    }

    private fun setInitialState() {
        viewModel?.getUiInitialState()

    }

    @Test
    fun appHomeScreenDataTest() =
        runTest {
            // Verify the expected values
            Truth.assertThat(viewModel?.screenState?.value?.buttonText)
                .isEqualTo("Refresh")
            Truth.assertThat(viewModel?.screenState?.value?.employeeDirectoryEmptyText)
                .isEqualTo("Employee Directory is Empty")
            Truth.assertThat(viewModel?.screenState?.value?.buttonText)
                .isNotEqualTo("Work. Paid. Rest")
            Truth.assertThat(viewModel?.screenState?.value?.employeeDirectoryEmptyText)
                .isNotEqualTo("S. Employee")

        }


    class MainDispatcherRule(
        private val dispatcher: TestDispatcher = StandardTestDispatcher()
    ) : TestWatcher() {
        @OptIn(ExperimentalCoroutinesApi::class)
        override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun finished(description: Description?) = Dispatchers.resetMain()

    }
}