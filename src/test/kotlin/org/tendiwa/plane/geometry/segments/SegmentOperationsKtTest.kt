package org.tendiwa.plane.geometry.segments

import org.junit.Test
import org.tendiwa.math.constants.EPSILON
import org.tendiwa.plane.directions.CardinalDirection.N
import org.tendiwa.plane.directions.CardinalDirection.S
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.distanceTo
import org.tendiwa.plane.geometry.points.spanHorizontalSegment
import org.tendiwa.plane.geometry.points.spanSegment
import org.tendiwa.plane.geometry.rays.Ray
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class SegmentOperationsKtTest {
    @Test
    fun segmentsIntersectIfTheyHaveACommonPoint() {
        val ab = Point(0.0, 0.0).spanSegment(5.0, 6.0)
        val cd = Point(5.0, 0.0).spanSegment(-5.0, 6.0)
        assert(ab.intersects(cd))
    }

    @Test
    fun segmentsDontIntersectIfTheyHaveACommonEndpoint() {
        val ab = Point(1.2, 3.4).spanSegment(5.6, 7.8)
        val ac = Point(1.2, 3.4).spanSegment(-5.6, -7.8)
        assertFalse(ab intersects ac)
    }

    @Test
    fun segmentsDontIntersectIfTheyDontHaveCommonPoints() {
        val ab = Point(0.0, 0.0).spanSegment(5.0, 6.0)
        val cd = Point(10.0, 10.0).spanSegment(7.0, 8.0)
        assertFalse(ab intersects cd)
    }

    @Test
    fun segmentsDontIntersectIfTheyOverlap() {
        val ab = Point(0.0, 0.0).spanSegment(10.0, 6.0)
        val cd = ab.move(5.0, 3.0)
        assertFalse(ab intersects cd)
    }

    @Test
    fun segmentsDontIntersectIfTheyOverlapAndHaveCommonStart() {
        val ab = Point(0.0, 0.0).spanSegment(10.0, 6.0)
        val ac = Point(0.0, 0.0).spanSegment(5.0, 3.0)
        assertFalse(ab intersects ac)
    }

    @Test
    fun segmentsDontIntersectIfTheyOverlapAndHaveCommonEnd() {
        val ab = Point(0.0, 0.0).spanSegment(10.0, 6.0)
        val ac = Point(5.0, 3.0).spanSegment(5.0, 3.0)
        assertFalse(ab intersects ac)
    }

    @Test
    fun segmentsDontIntersectIfOneIsWithinAnother() {
        val ab = Point(0.0, 0.0).spanSegment(5.0, 3.0)
        val ac = Point(-5.0, -3.0).spanSegment(15.0, 9.0)
        assertFalse(ab intersects ac)
    }

    @Test
    fun segmentsWithInvertedSlopesSharingAndEndpointDontIntersect() {
        val ab = Point(0.0, 0.0).spanSegment(1.0, 1.0)
        val cb = Point(2.0, 2.0).spanSegment(-1.0, -1.0)
        assertFalse(ab intersects cb)
    }

    @Test
    fun segmentsCanHaveCommonEndpoints() {
        val ab = Segment(Point(0.0, 0.0), Point(1.0, 1.0))
        val ac = Segment(Point(0.0, 0.0), Point(-1.0, -1.0))
        assert(ab.hasCommonEndpoint(ac))
    }

    @Test
    fun segmentsCanHaveNoCommonEndpoints() {
        val ab = Segment(Point(0.0, 0.0), Point(1.0, 1.0))
        val cd = Segment(Point(2.0, 2.0), Point(-1.0, -1.0))
        assertFalse(ab.hasCommonEndpoint(cd))
    }

    @Test
    fun equalSegmentsHaveCommonEndpoints() {
        val a = Segment(Point(0.0, 0.0), Point(1.0, 1.0))
        val b = Segment(Point(0.0, 0.0), Point(1.0, 1.0))
        assert(a.hasCommonEndpoint(b))
    }

    @Test
    fun `point above segment projects on segment`() {
        assertEquals(
            Point(2.0, 0.0),
            Point(0.0, 0.0)
                .spanHorizontalSegment(5.0)
                .project(Point(2.0, 2.0))
        )
    }

    @Test
    fun `point not above segment projects outside segment`() {
        assertNull(
            Point(0.0, 0.0)
                .spanHorizontalSegment(5.0)
                .project(Point(6.0, 2.0))
        )
    }

    @Test
    fun `intersection with ray can yield point`() {
        Point(0.0, 0.0)
            .spanHorizontalSegment(5.0)
            .intersection(
                Ray(Point(2.5, 2.5), S)
            )!!
            .apply {
                assert(distanceTo(Point(2.5, 0.0)) < EPSILON)
            }
    }

    @Test
    fun `intersection with ray can yield no point if line is not intersected`() {
        Point(0.0, 0.0)
            .spanHorizontalSegment(5.0)
            .intersection(
                Ray(Point(6.0, 2.5), N)
            )
            .apply { assertNull(this) }

    }

    @Test
    fun `intersection with ray can yield no point even if line is intersected`() {
        Point(0.0, 0.0)
            .spanHorizontalSegment(5.0)
            .intersection(
                Ray(Point(6.0, 2.5), S)
            )
            .apply { assertNull(this) }
    }
}
