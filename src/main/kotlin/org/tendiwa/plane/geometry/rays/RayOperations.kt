package org.tendiwa.plane.geometry.rays

import org.tendiwa.math.angles.AngularMeasure
import org.tendiwa.math.constants.EPSILON
import org.tendiwa.math.doubles.distance
import org.tendiwa.plane.directions.CardinalDirection
import org.tendiwa.plane.directions.Direction
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.directionTo
import org.tendiwa.plane.geometry.points.move
import org.tendiwa.plane.geometry.rectangles.Rectangle
import org.tendiwa.plane.geometry.rectangles.side
import org.tendiwa.plane.geometry.segments.Segment
import org.tendiwa.plane.geometry.vectors.direction

fun Ray.pointOnRay(radius: Double): Point =
    start.move(
        Math.cos(direction.radians) * radius,
        Math.sin(direction.radians) * radius
    )

fun Ray.changeDirection(direction: Direction): Ray =
    Ray(start, direction)

/**
 * Cretes a new [Ray] with different [Ray.start] but with the same
 * [Ray.direction].
 */
fun Ray.changeStart(start: Point): Ray =
    Ray(start, direction)

/**
 * Creates a new [Ray] with the same [Ray.start] but with [Ray.direction]
 * rotated by half circle.
 */
fun Ray.inverse(): Ray =
    Ray(start, direction + AngularMeasure.HALF_CIRCLE)

fun Ray.rotate(angularMeasure: AngularMeasure): Ray =
    changeDirection(direction + angularMeasure)

/**
 * Returns a segment from the beginning of the ray to a point on ray that is
 * [length] units away from the beginning.
 */
fun Ray.segment(length: Double): Segment =
    Segment(start, pointOnRay(length))

/**
 * Returns closest intersection of this ray with a [Rectangle]
 */
fun Ray.closestIntersection(rectangle: Rectangle): Point? {
    val goodEnoughDistance = rectangle.width
    val pointOnRay = pointOnRay(goodEnoughDistance)
    return CardinalDirection.values()
        .map { rectangle.side(it) }
        .map { RayIntersection(start, pointOnRay, it) }
        .filter { it.r > 0.0 }
        .sortedBy { it.r }
        .filter { it.intersects }
        .map { it.commonPoint() }
        .firstOrNull()
}

/**
 * Checks if a ray contains a point.
 */
fun Ray.contains(point: Point): Boolean =
    (start directionTo point).radians distance direction.radians < EPSILON
