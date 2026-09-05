package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.GpuChipRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("GPU Specs", appName)
  }

  @Test
  fun `verify database contains at least 300 unique chips`() {
    val repository = GpuChipRepository()
    val chips = repository.getAllChips()
    assertTrue("Total chips should be at least 300, found: ${chips.size}", chips.size >= 300)
  }
}
