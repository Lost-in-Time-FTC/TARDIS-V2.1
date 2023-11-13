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
    private static final Point SLEEVE_LEFT_TOP_LEFT_ANCHOR_POINT = new Point(0, 120);
    private static final Point SLEEVE_CENTER_TOP_LEFT_ANCHOR_POINT = new Point(106.666666666, 120);
    private static final Point SLEEVE_RIGHT_TOP_LEFT_ANCHOR_POINT = new Point(213.333333333, 120);

    // Width and height for the bounding boxes
    public static double REGION_WIDTH = 106.66666666;
    public static int REGION_HEIGHT = 120;
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
    Mat mat = new Mat();
    Scalar centerMean = new Scalar(0,0,0);
    @Override
    public Mat processFrame(Mat input) {
        // Make a working copy of the input matrix in HSV
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        // Get the submat frame, and then sum all the values
        Mat centerAreaMat = mat.submat(new Rect(sleeve_center_point_A, sleeve_center_point_B));
        centerMean = Core.mean(centerAreaMat);


        // Get the submat frame, and then sum all the values
        Mat leftAreaMat = mat.submat(new Rect(sleeve_left_point_A, sleeve_left_point_B));
        Scalar leftMean = Core.mean(leftAreaMat);

        // Get the submat frame, and then sum all the values
        Mat rightAreaMat = mat.submat(new Rect(sleeve_right_point_A, sleeve_right_point_B));
        Scalar rightMean = Core.mean(rightAreaMat);


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
        if (leftMean.val[0] > 0 && leftMean.val[0] < 50 && leftMean.val[1] > 70 && leftMean.val[2] > 50) {
            position = ParkingPosition.LEFT;
            Imgproc.rectangle(
                    mat,
                    sleeve_left_point_A,
                    sleeve_left_point_B,
                    CYAN,
                    2
            );
        } else if (centerMean.val[0] > 0 && centerMean.val[0] < 50 && centerMean.val[1] > 70 && centerMean.val[2] > 50) {
            position = ParkingPosition.CENTER;
            Imgproc.rectangle(
                    mat,
                    sleeve_center_point_A,
                    sleeve_center_point_B,
                    MAGENTA,
                    2
            );
        } else if (rightMean.val[0] > 0 && rightMean.val[0] < 50 && rightMean.val[1] > 70 && rightMean.val[2] > 50) {
            position = ParkingPosition.RIGHT;
            Imgproc.rectangle(
                    mat,
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
        return mat;
    }

    // Returns an enum being the current position where the robot will park
    public ParkingPosition getPosition() {
        return position;
    }
    public Scalar getCenterMean() {return centerMean; }

    public enum ParkingPosition {
        LEFT,
        CENTER,
        RIGHT,
        UNDETECTED
    }
}
