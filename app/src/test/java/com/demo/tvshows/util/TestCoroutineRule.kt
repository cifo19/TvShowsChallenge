package com.demo.tvshows.util

import com.demo.tvshows.util.coroutine.dispatchers.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    val testDispatchers = CoroutineDispatchers(testDispatcher, testDispatcher, testDispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
fun TestCoroutineRule.runBlocking(block: suspend () -> Unit) = this.testDispatcher.runBlockingTest {
    block()
}

// https://medium.com/androiddevelopers/testing-two-consecutive-livedata-emissions-in-coroutines-5680b693cbf8
@ExperimentalCoroutinesApi
fun TestCoroutineDispatcher.byPausing(block: () -> Unit) {
    pauseDispatcher()
    block()
    resumeDispatcher()
}
