package org.tendiwa.geometry.continuum.constructors

import org.tendiwa.geometry.continuum.lines.Line
import org.tendiwa.geometry.continuum.points.Point
import org.tendiwa.geometry.continuum.segments.Segment
import org.tendiwa.geometry.continuum.vectors.Vector

infix fun Point.segmentTo(target: Point): Segment =
    Segment(this, target)

infix fun Point.lineThrough(target: Point): Line =
    Line(
        this.y - target.y,
        target.x - this.x,
        (this.x - target.x) * this.y + (target.y - this.y) * this.x
    )

infix fun Point.vectorTo(target: Point): Vector =
    Vector(target.x - this.x, target.y - this.y)
