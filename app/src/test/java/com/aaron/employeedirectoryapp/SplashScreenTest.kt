package com.aaron.employeedirectoryapp


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aaron.employeedirectoryapp.data.DataRepository
import com.aaron.employeedirectoryapp.di.AppModule
import com.aaron.employeedirectoryapp.di.appresources.AppResources
import com.aaron.employeedirectoryapp.ui.screens.splashscreen.SplashScreenViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
class SplashScreenTest {
    @MockK
    private lateinit var dataRepository: DataRepository
    @MockK
    private lateinit var appResources: AppResources
    private var viewModel: SplashScreenViewModel? = null
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRul = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // Mock behavior of AppResources methods
        every { appResources.getString(R.string.app_logo) } returns "S. Employee"
        every { appResources.getString(R.string.app_header_text) } returns "Work. Paid. Rest"
        viewModel = SplashScreenViewModel(appResources, dataRepository)
        setInitialState()
        Dispatchers.setMain(dispatcher)

    }

    private fun setInitialState() {
        viewModel?.getUiInitialState()

    }

    @Test
    fun splashScreenDataTest() =
        runTest {
            // Verify the expected values
            Truth.assertThat(viewModel?.screenState?.value?.appLogo)
                .isEqualTo("S. Employee")
            Truth.assertThat(viewModel?.screenState?.value?.appHeaderText)
                .isEqualTo("Work. Paid. Rest")
            Truth.assertThat(viewModel?.screenState?.value?.appLogo)
                .isNotEqualTo("Work. Paid. Rest")
            Truth.assertThat(viewModel?.screenState?.value?.appHeaderText)
                .isNotEqualTo("S. Employee")

        }


    class MainDispatcherRule(
        private val dispatcher: TestDispatcher = StandardTestDispatcher()
    ) : TestWatcher() {
        override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)
        override fun finished(description: Description?) = Dispatchers.resetMain()

    }
}