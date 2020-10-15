package com.mercadolibre.quasar.utils;

import com.mercadolibre.quasar.facade.v0.dto.Position;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
public class FunctionUtils {

    final static double EPSILON = 10;
    final static int[] kenobi = new int[]{-500, -200};
    final static int[] skywalker = new int[]{100, -100};
    final static int[] sato = new int[]{500, 100};

    public List<String> getMessage(List<String> message, List<String> resultMessage) {

        if (resultMessage == null) {
            resultMessage = Arrays.asList(new String[message.size()]);
        }

        for (String messageString : message) {
            if (!messageString.isEmpty() && !resultMessage.contains(messageString)) {
                resultMessage.set(message.indexOf(messageString), messageString);
            }
        }

        return resultMessage;
    }

    public Position getLocation(List<SatelliteIn> satellites) {

        Position position = new Position();
        double r0 = 0, r1 = 0, r2 = 0;

        int x0 = kenobi[0];
        int y0 = kenobi[1];
        int x1 = skywalker[0];
        int y1 = skywalker[1];
        int x2 = sato[0];
        int y2 = sato[1];

        for (SatelliteIn satellite : satellites) {
            if (satellite.getName().equalsIgnoreCase("kenobi")) {
                r0 = satellite.getDistance();
            } else if (satellite.getName().equalsIgnoreCase("skywalker")) {
                r1 = satellite.getDistance();
            } else {
                r2 = satellite.getDistance();
            }
        }

        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

        /* dx and dy are the vertical and horizontal distances between
         * the circle centers.
         */
        dx = x1 - x0;
        dy = y1 - y0;

        /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy * dy) + (dx * dx));

        /* Check for solvability. */
        if (d > (r0 + r1)) {
            /* no solution. circles do not intersect. */
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no solution, both satellites do not intersect");
        }
        if (d < Math.abs(r0 - r1)) {
            /* no solution. one circle is contained in the other */
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no solution, one satellite is contained in the other");
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */

        /* Determine the distance from point 0 to point 2. */
        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

        /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a / d);
        point2_y = y0 + (dy * a / d);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        h = Math.sqrt((r0 * r0) - (a * a));

        /* Now determine the offsets of the intersection points from
         * point 2.
         */
        rx = -dy * (h / d);
        ry = dx * (h / d);

        /* Determine the absolute intersection points. */
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;

        //System.out.println("INTERSECTION Circle1 AND Circle2:" + "(" + intersectionPoint1_x + "," + intersectionPoint1_y + ")" + " AND (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy * dy) + (dx * dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy * dy) + (dx * dx));

        if (Math.abs(d1 - r2) < EPSILON) {
            position.setX(intersectionPoint1_x);
            position.setY(intersectionPoint1_y);
        } else if (Math.abs(d2 - r2) < EPSILON) {
            position.setX(intersectionPoint2_x);
            position.setY(intersectionPoint2_y);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no solution, no satellite can intersect to a common point");
        }

        return position;
    }
}
