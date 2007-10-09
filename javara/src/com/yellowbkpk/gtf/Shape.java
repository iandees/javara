package com.yellowbkpk.gtf;

import java.io.Serializable;

public class Shape implements Serializable {

	private static final long serialVersionUID = 3171706764922928243L;

	private final String shapeId;
	private final Double shapePtLat;
	private final Double shapePtLon;
	private final Integer shapePtSequence;
	private Double shapeDistTraveled;

	/**
	 * @param shapeId
	 *            The shape_id field contains an ID that uniquely identifies a
	 *            shape.
	 * @param shapePtLat
	 *            The shape_pt_lat field associates a shape point's latitude
	 *            with a shape ID. The field value must be a valid WGS 84
	 *            latitude. Each row in shapes.txt represents a shape point in
	 *            your shape definition.
	 * @param shapePtLon
	 *            he shape_pt_lon field associates a shape point's longitude
	 *            with a shape ID. The field value must be a valid WGS 84
	 *            longitude value from -180 to 180. Each row in shapes.txt
	 *            represents a shape point in your shape definition.
	 * @param shapePtSequence
	 *            The shape_pt_sequence field associates the latitude and
	 *            longitude of a shape point with its sequence order along the
	 *            shape. The values for shape_pt_sequence must be non-negative
	 *            integers, and they must increase along the trip.
	 * @param shapeDistTraveled
	 *            When used in the shapes.txt file, the shape_dist_traveled
	 *            field positions a shape point as a distance traveled along a
	 *            shape from the first shape point. The shape_dist_traveled
	 *            field represents a real distance traveled along the route in
	 *            units such as feet or kilometers. This information allows the
	 *            trip planner to determine how much of the shape to draw when
	 *            showing part of a trip on the map. The values used for
	 *            shape_dist_traveled must increase along with
	 *            shape_pt_sequence: they cannot be used to show reverse travel
	 *            along a route.
	 */
	public Shape(String shapeId, Double shapePtLat, Double shapePtLon,
			Integer shapePtSequence, Double shapeDistTraveled) {
		this.shapeId = shapeId;
		this.shapePtLat = shapePtLat;
		this.shapePtLon = shapePtLon;
		this.shapePtSequence = shapePtSequence;
		this.shapeDistTraveled = shapeDistTraveled;
	}

	public String getShapeId() {
		return shapeId;
	}

	public Double getShapePtLat() {
		return shapePtLat;
	}

	public Double getShapePtLon() {
		return shapePtLon;
	}

	public Integer getShapePtSequence() {
		return shapePtSequence;
	}

	public Double getShapeDistTraveled() {
		return shapeDistTraveled;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		b.append(shapeId);
		b.append(",");
		b.append(shapePtLat);
		b.append(",");
		b.append(shapePtLon);
		b.append(",");
		b.append(shapePtSequence);
		b.append(",");

		if (shapeDistTraveled == null) {
			b.append("");
		} else {
			b.append(shapeDistTraveled);
		}

		return b.toString();
	}

}
