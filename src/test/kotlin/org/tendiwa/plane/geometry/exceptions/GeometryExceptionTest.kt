package org.tendiwa.plane.geometry.exceptions

import org.junit.Test
import kotlin.test.assertEquals

class GeometryExceptionTest {
    @Test fun containsMessage() {
        try {
            throw GeometryException("hello")
        } catch (e: RuntimeException) {
            assertEquals("hello", e.message)
        }
    }
}
