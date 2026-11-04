/*
 * This file includes an autonomous file for the goBILDA® StarterBot for the
 * 2026-2027 FIRST® Tech Challenge season BIOBUZZ™.
 *
 * This robot starts up against...
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

@Autonomous(name="My BioBuzz Auto", group = "StarterBot")
//@Disabled   // comment this out to add to the OpMode list on the Driver Hub
public class BioBuzzAuto extends LinearOpMode {

    // Create an instance of the MecanumDrive object to be used to access robot drive functionality.
    MecanumDrive drive = new MecanumDrive();    // create instance of MecanumDrive object

    private ElapsedTime runtime = new ElapsedTime();

    // Create an ENUM not to create a state machine, but to capture which alliance we are on.
    private enum Alliance {
        RED,
        BLUE;
    }

    private Alliance alliance = Alliance.RED;   // assign the ENUM a default state

    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap, false); // no PinPoint (for now...)

        // Select which alliance we are on using the gamepad.
        if (gamepad1.b) {
            alliance = Alliance.RED;
        } else if (gamepad1.x) {
            alliance = Alliance.BLUE;
        }
        telemetry.addData("Press B", "for RED alliance");
        telemetry.addData("Press X", "for BLUE alliance");
        telemetry.addData("Selected Alliance", alliance);



        // Wait for the game to start (Display Gyro value while waiting)
        while (opModeInInit()) {
            telemetry.addData(">", "Robot Heading = %4.0f", drive.getHeading());
            telemetry.update();
        }



        // Send telemetry message indicating robot is ready and wait for driver to press START.
        // This telemetry line is especially important when using the IMU, as the IMU can take
        // a couple of seconds to initialize and this line executes when IMU initialization is complete.
        telemetry.addLine("\nRobot ready.  Press PLAY to start.");
        telemetry.update();

        waitForStart();

        // Leave start position (by time).
        drive.driveRobotRelative(0.5, 0, 0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) { // drive forward for 2 seconds
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Move to Loading Zone
        if (alliance == Alliance.RED) {
            drive.driveRobotRelative(0.0, 0, 0.3);
        } else if (alliance == Alliance.BLUE) {
            drive.driveRobotRelative(0.0, 0, -0.3);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.2)) { // turn 45 degrees (est. based on time)
            telemetry.addData("Path", "Leg 2: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Stop movement.
        drive.stopRobot();

        if (isStopRequested()) {}  // run this when STOP is pressed on the driver hub
    }
}