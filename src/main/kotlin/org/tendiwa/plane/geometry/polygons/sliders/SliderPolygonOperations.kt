package org.tendiwa.plane.geometry.polygons.sliders

import org.tendiwa.collections.nextAfter
import org.tendiwa.math.sliders.CircularSlider
import org.tendiwa.plane.geometry.polygons.cut.CutPolygonEdge
import java.util.*

/**
 * Edges of this polygon in circular slider form, ordered polygonwise.
 */
val SliderPolygon.edges: List<SliderPolygonEdge>
    get() =
    sliders
        .indices
        .map {
            SliderPolygonEdge(
                start = sliders[it],
                end = sliders.nextAfter(it),
                segment = original.segments[it]
            )
        }

fun SliderPolygon.cutEdges(
    cutPositions: List<CircularSlider>
): List<CutPolygonEdge> {
    if (cutPositions.sortedBy { it.position } != cutPositions) {
        throw IllegalArgumentException(
            "cutPositions must be sorted in ascending order; they are " +
                "$cutPositions"
        )
    }
    return edges
        .fold(
            ArrayList<CutPolygonEdge>(
                cutPositions.size
            ), // mutable accumulator
            { lists, sliderEdge ->
                lists.apply {
                    add(
                        CutPolygonEdge(
                            cutPositions = cutPositions,
                            previous = lists.lastOrNull(),
                            sliderEdge = sliderEdge
                        )
                    )
                }
            }
        )
}