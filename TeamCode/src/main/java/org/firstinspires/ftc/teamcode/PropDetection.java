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
    YELLOW  = Parking Left
    CYAN    = Parking Middle
    MAGENTA = Parking Right
    */

    // TOP_LEFT anchor point for the bounding box
    private static final Point SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT = new Point(0, 0);
    private static final Point SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT = new Point(107, 0);
    private static final Point SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT = new Point((106*2)+2, 0);

    // Width and height for the bounding boxes
    public static int REGION_WIDTH = 106;
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
        double centerMinColor = Math.min(centerSumColors.val[0], Math.min(centerSumColors.val[1], centerSumColors.val[2]));

        // Get the submat frame, and then sum all the values
        Mat leftAreaMat = input.submat(new Rect(sleeve_left_point_A, sleeve_left_point_B));
        Scalar leftSumColors = Core.sumElems(leftAreaMat);

        // Get the minimum RGB value from every single channel
        double leftMinColor = Math.min(leftSumColors.val[0], Math.min(leftSumColors.val[1], leftSumColors.val[2]));

        // Get the submat frame, and then sum all the values
        Mat rightAreaMat = input.submat(new Rect(sleeve_right_point_A, sleeve_right_point_B));
        Scalar rightSumColors = Core.sumElems(rightAreaMat);

        // Get the minimum RGB value from every single channel
        double rightMinColor = Math.min(rightSumColors.val[0], Math.min(rightSumColors.val[1], rightSumColors.val[2]));

        // Change the bounding box color based on the sleeve color
//        if (centerSumColors.val[0] == centerMinColor) {
//            position = ParkingPosition.CENTER;
//            Imgproc.rectangle(
//                    input,
//                    sleeve_center_point_A,
//                    sleeve_center_point_B,
//                    CYAN,
//                    2
//            );
//        } else
        if (centerSumColors.val[1] == centerMinColor) {
            position = ParkingPosition.CENTER;
            Imgproc.rectangle(
                    input,
                    sleeve_center_point_A,
                    sleeve_center_point_B,
                    MAGENTA,
                    2
            );
        } else if (leftSumColors.val[1] == leftMinColor) {
            position = ParkingPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    sleeve_left_point_A,
                    sleeve_left_point_B,
                    MAGENTA,
                    2
            );
        } else if (rightSumColors.val[1] == rightMinColor) {
            position = ParkingPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    sleeve_right_point_A,
                    sleeve_right_point_B,
                    MAGENTA,
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
