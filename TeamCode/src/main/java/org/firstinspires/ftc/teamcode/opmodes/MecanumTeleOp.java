/*
    From FTC Team 7477 - FTC Programming Episode 8: Mecanum Drive (robot centric)
                         FTC Programming Episode 9: Scaling Drive Powers Proportionally

    and https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#robot-centric-final-sample-code
 */
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

@TeleOp(name="Mecanum TeleOp", group = "OpMode")
//@Disabled   // comment this out to add to the OpMode list on the Driver Hub
public class MecanumTeleOp extends LinearOpMode {

    // Create an instance of the MecanumDrive object to be used to access robot drive functionality.
    MecanumDrive drive = new MecanumDrive();    // create instance of MecanumDrive object

    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap, false); // no PinPoint (for now...)

        // Send telemetry message indicating robot is ready and wait for driver to press START.
        // This telemetry line is especially important when using the IMU, as the IMU can take
        // a couple of seconds to initialize and this line executes when IMU initialization is complete.
        telemetry.addLine("Robot ready.  Press PLAY to start.");
        telemetry.update();

        waitForStart();

        // Run until the driver presses stop
        while (opModeIsActive()) {
            telemetry.addLine("Default is FIELD RELATIVE mode.");
            telemetry.addLine("Hold LEFT bumper to drive in ROBOT RELATIVE mode.");
            telemetry.addLine("Press A to reset YAW.");

            // When gamepad1.a is pressed, reset the Yaw to 0 based on the orientation relative to the robot's position
            if (gamepad1.a) {
                drive.resetYaw();
            }

            if (gamepad1.left_bumper) {  // press the left bumper for robot centric drive
                drive.driveRobotRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x * 1.1, gamepad1.right_stick_x); // strafe multiplier to counteract imperfect strafing, adjustable based on driver preference
            } else {    // field centric drive
                drive.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x * 1.1, gamepad1.right_stick_x); // strafe multiplier to counteract imperfect strafing, adjustable based on driver preference
            }
            telemetry.update();
        }

        if (isStopRequested()) {    // run this when STOP is pressed on the driver hub

        }
    }
}