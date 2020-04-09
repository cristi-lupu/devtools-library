package com.maximbircu.devtools.android.readers.soruces.yaml

import com.maximbircu.devtools.android.BaseTest
import com.maximbircu.devtools.common.core.DevTool
import com.maximbircu.devtools.common.presentation.tools.toggle.ToggleTool
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import java.io.InputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class YamlDevToolsReaderTest : BaseTest() {
    @Test
    fun `returns empty map if config is empty`() {
        enqueueTools(null)
        val reader = YamlDevToolsReader(mockk(relaxed = true), "".byteInputStream())

        val devTools = reader.getDevTools()

        assertTrue(devTools.isEmpty())
    }

    @Test
    fun `returns proper dev tool`() {
        enqueueTools(mapOf("toggle-tool" to ToggleTool()))
        val reader = YamlDevToolsReader(mockk(relaxed = true), "".byteInputStream())

        val tools = reader.getDevTools()

        assertEquals(tools.getValue("toggle-tool").key, "toggle-tool")
    }

    @Suppress("UNCHECKED_CAST")
    private fun enqueueTools(tools: Map<String, DevTool<*>>? = null) {
        mockkConstructor(Yaml::class)
        every {
            anyConstructed<Yaml>().load<Map<String, DevTool<Any>>>(any<InputStream>())
        } returns tools as? Map<String, DevTool<Any>>?
    }
}