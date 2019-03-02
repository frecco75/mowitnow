package com.bbc.automower.ui;

import com.bbc.automower.domain.Lawn;
import com.bbc.automower.domain.Mower;
import com.bbc.automower.domain.Position;
import javafx.geometry.Point2D;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UIUtil {

    public static Point2D getPoint2D(final Lawn lawn) {
        return new Point2D(lawn.getWidth(), lawn.getHeight());
    }

    public static Point2D getPoint2D(final Mower mower) {
        return toPoint2D(mower.getPosition());
    }

    public static Point2D toPoint2D(final Position position) {
        return new Point2D(position.getX(), position.getY());
    }

}
