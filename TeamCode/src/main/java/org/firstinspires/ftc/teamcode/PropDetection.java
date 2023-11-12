package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetection extends OpenCvPipeline {
    /*
    YELLOW  = Parking Right
    CYAN    = Parking Left
    MAGENTA = Parking Center
    */

    // TOP_LEFT anchor point for the bounding box
    private static final Point SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT = new Point(0, 0);
    private static final Point SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT = new Point(106.666666666, 0);
    private static final Point SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT = new Point(213.333333333, 0);

    // Width and height for the bounding boxes
    public static double REGION_WIDTH = 106.666666666;
    public static int REGION_HEIGHT = 240;
    // Color definitions
    private final Scalar
            YELLOW = new Scalar(255, 255, 0),
            CYAN = new Scalar(0, 255, 255),
            MAGENTA = new Scalar(255, 0, 0);
    // Anchor point definitions
    // Left
    Point sleeve_left_point_A = new Point(
            SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT.x,
            SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT.y);
    Point sleeve_left_point_B = new Point(
            // Bottom right corner of the bounding box
            SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT.x + REGION_WIDTH,
            SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    // Center
    Point sleeve_center_point_A = new Point(
            SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT.x,
            SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT.y);
    Point sleeve_center_point_B = new Point(
            // Bottom right corner of the bounding box
            SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT.x + REGION_WIDTH,
            SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    // Right
    Point sleeve_right_point_A = new Point(
            SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT.x,
            SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT.y);
    Point sleeve_right_point_B = new Point(
            // Bottom right corner of the bounding box
            SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT.x + REGION_WIDTH,
            SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    // Running variable storing the parking position
    private volatile ParkingPosition position = ParkingPosition.LEFT;

    @Override
    public Mat processFrame(Mat input) {
        // Get the submat frame, and then sum all the values
        Mat centerAreaMat = input.submat(new Rect(sleeve_center_point_A, sleeve_center_point_B));
        Scalar centerSumColors = Core.sumElems(centerAreaMat);

        // Get the minimum RGB value from every single channel
        double centerMaxColor = Math.max(centerSumColors.val[0], Math.max(centerSumColors.val[1], centerSumColors.val[2]));

        // Get the submat frame, and then sum all the values
        Mat leftAreaMat = input.submat(new Rect(sleeve_left_point_A, sleeve_left_point_B));
        Scalar leftSumColors = Core.sumElems(leftAreaMat);

        // Get the minimum RGB value from every single channel
        double leftMaxColor = Math.max(leftSumColors.val[0], Math.max(leftSumColors.val[1], leftSumColors.val[2]));

        // Get the submat frame, and then sum all the values
        Mat rightAreaMat = input.submat(new Rect(sleeve_right_point_A, sleeve_right_point_B));
        Scalar rightSumColors = Core.sumElems(rightAreaMat);

        // Get the minimum RGB value from every single channel
        double rightMaxColor = Math.max(rightSumColors.val[0], Math.max(rightSumColors.val[1], rightSumColors.val[2]));

        double leftBoundRGB = leftSumColors.val[0] - leftSumColors.val[1] - leftSumColors.val[2];
        double centerBoundRGB = centerSumColors.val[0] - centerSumColors.val[1] - centerSumColors.val[2];
        double rightBoundRGB = rightSumColors.val[0] - rightSumColors.val[1] - rightSumColors.val[2];

        // Change the bounding box color based on the sleeve color
//        if (centerSumColors.val[0] == centerMaxColor) {
//            position = ParkingPosition.CENTER;
//            Imgproc.rectangle(
//                    input,
//                    sleeve_center_point_A,
//                    sleeve_center_point_B,
//                    CYAN,
//                    2
//            );
//        } else
        if (leftBoundRGB > centerBoundRGB && leftBoundRGB > rightBoundRGB) {
            position = ParkingPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    sleeve_center_point_A,
                    sleeve_center_point_B,
                    CYAN,
                    2
            );
        } else if (centerBoundRGB > leftBoundRGB && centerBoundRGB > rightBoundRGB) {
            position = ParkingPosition.CENTER;
            Imgproc.rectangle(
                    input,
                    sleeve_left_point_A,
                    sleeve_left_point_B,
                    MAGENTA,
                    2
            );
        } else if (rightBoundRGB > leftBoundRGB && rightBoundRGB > centerBoundRGB) {
            position = ParkingPosition.RIGHT;
            Imgproc.rectangle(
                    input,
                    sleeve_right_point_A,
                    sleeve_right_point_B,
                    YELLOW,
                    2
            );
        } else {
            position = ParkingPosition.UNDETECTED;
        }

        // Release and return input
        centerAreaMat.release();
        leftAreaMat.release();
        rightAreaMat.release();
        return input;
    }

    // Returns an enum being the current position where the robot will park
    public ParkingPosition getPosition() {
        return position;
    }

    public enum ParkingPosition {
        LEFT,
        CENTER,
        RIGHT,
        UNDETECTED
    }
}
