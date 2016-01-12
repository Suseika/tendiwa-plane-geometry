package org.tendiwa.plane.geometry.vectors

import org.tendiwa.math.angles.Angle

val VectorSector.bisector: Vector
    get() = if (ccw.makesReflexAngle(cw)) sumVector.reversed else sumVector

val VectorSector.sumVector: Vector
    get() {
        var bisectorDirection = cw.normalized + ccw.normalized;
        if (bisectorDirection.isZero) {
            bisectorDirection = ccw.rotatedQuarterClockwise;
        }
        return bisectorDirection.normalized * averageMagnitude(cw, ccw)
    }

// TODO: Replace with (cw.magnitude + ccw.magnitude) / 2
private fun averageMagnitude(cw: Vector, ccw: Vector): Double =
    cw.magnitude / 2 + ccw.magnitude / 2

val VectorSector.angle: Angle
    get() = cw.direction.counterClockwiseAngle(ccw.direction)
