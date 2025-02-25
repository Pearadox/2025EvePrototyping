// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class IOConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OP_CONTROLLER_PORT = 1;
  }

  public static final class SwerveConstants{
    //Drivetrain motor/encoder IDs
    public static final int LEFT_FRONT_DRIVE_ID = 1;
    public static final int RIGHT_FRONT_DRIVE_ID = 2;
    public static final int LEFT_BACK_DRIVE_ID = 3;
    public static final int RIGHT_BACK_DRIVE_ID = 4;
    
    public static final int LEFT_FRONT_TURN_ID = 5;
    public static final int RIGHT_FRONT_TURN_ID = 6;
    public static final int LEFT_BACK_TURN_ID = 7;
    public static final int RIGHT_BACK_TURN_ID = 8;
    
    public static final int LEFT_FRONT_CANCODER_ID = 11;
    public static final int RIGHT_FRONT_CANCODER_ID = 12;
    public static final int LEFT_BACK_CANCODER_ID = 13;
    public static final int RIGHT_BACK_CANCODER_ID = 14;

    public static final int PIGEON_ID = 15;

    //Drivetrain characteristics
    public static final double LEFT_FRONT_OFFSET =  0.0;
    public static final double RIGHT_FRONT_OFFSET = 0.0;
    public static final double LEFT_BACK_OFFSET =  0.0;
    public static final double RIGHT_BACK_OFFSET = 0.0;

    public static final double WHEEL_DIAMETER = Units.inchesToMeters(4);
    public static final double DRIVE_MOTOR_GEAR_RATIO = 5.357; //SDS Mk4i L3+ (60:16 first stage)
    public static final double TURN_MOTOR_GEAR_RATIO = 150.0/7;
    public static final double DRIVE_MOTOR_PCONVERSION = WHEEL_DIAMETER * Math.PI / DRIVE_MOTOR_GEAR_RATIO;
    public static final double TURN_MOTOR_PCONVERSION = 2 * Math.PI / TURN_MOTOR_GEAR_RATIO;
    public static final double DRIVE_MOTOR_VCONVERSION = DRIVE_MOTOR_PCONVERSION;
    public static final double TURN_MOTOR_VCONVERSION = TURN_MOTOR_PCONVERSION / 60.0;
    public static final double KP_TURNING = 0.5;

    public static final double DRIVETRAIN_MAX_SPEED = 6.62;
    public static final double DRIVETRAIN_MAX_ANGULAR_SPEED = 3.45 * Math.PI; //TODO: Determine max angular speed

    //Swerve Kinematics
    public static final double TRACK_WIDTH = Units.inchesToMeters(24.75);
    public static final double WHEEL_BASE = Units.inchesToMeters(22.75);
    public static final double DRIVE_BASE_RADIUS = Math.sqrt(Math.pow(TRACK_WIDTH, 2) + Math.pow(WHEEL_BASE, 2)) / 2.0;

    public static final SwerveDriveKinematics DRIVE_KINEMATICS = new SwerveDriveKinematics(
        new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2),
        new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2),
        new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2),
        new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)
    );

    //Teleop constraints
    public static final double TELE_DRIVE_MAX_SPEED = DRIVETRAIN_MAX_SPEED;
    public static final double TELE_DRIVE_MAX_ANGULAR_SPEED = DRIVETRAIN_MAX_ANGULAR_SPEED / 1.25;
    public static final double TELE_DRIVE_MAX_ACCELERATION = 3;
    public static final double TELE_DRIVE_MAX_ANGULAR_ACCELERATION = 3;

    //Auton constraints
    public static final double AUTO_kP_TRANSLATION = 4;
    public static final double AUTO_kP_ROTATION = 1.5;


    public static final double AUTO_DRIVE_MAX_SPEED = DRIVETRAIN_MAX_SPEED / 1.5;
    public static final double AUTO_DRIVE_MAX_ANGULAR_SPEED = DRIVETRAIN_MAX_ANGULAR_SPEED / 2.0;
    public static final double AUTO_DRIVE_MAX_ACCELERATION = 3;
    public static final double AUTO_DRIVE_MAX_ANGULAR_ACCELERATION = Math.PI;

    //https://docs.wpilib.org/en/stable/docs/software/advanced-controls/state-space/state-space-pose-estimators.html
    public static final Vector<N3> ODOMETRY_STD_DEV = VecBuilder.fill(0.1, 0.1, 0.1);

    public static final double kS_PERCENT = 0.035;
    public static final double kP_PERCENT = 0.009;

    //exponent on joystick sensitivity 
    public static final int EXPONENT = 3;
  }

  public static final class IntakeConstants{
    public static final int UTB_ROLLER_ID = 21;
  }

  public static final class ShooterConstants{
    public static final int LEFT_SHOOTER_ID = 31;
    public static final int RIGHT_SHOOTER_ID = 32;
    public static final int PIVOT_ID = 33;

    public static final double LEFT_SHOOTER_kP = 0.3;
    public static final double LEFT_SHOOTER_kI = 0;
    public static final double LEFT_SHOOTER_kD = 0;

    public static final double RIGHT_SHOOTER_kP = 0.3;
    public static final double RIGHT_SHOOTER_kI = 0;
    public static final double RIGHT_SHOOTER_kD = 0;

    public static final double SHOOTER_MIN_OUTPUT = -1.0;
    public static final double SHOOTER_MAX_OUTPUT = 1.0;

    //TODO Tune Pivot to Not Oscillate
    public static final double PIVOT_kP = 0.07;
    public static final double PIVOT_kI = 0.00008;
    public static final double PIVOT_kD = 0;

    public static final double PIVOT_MIN_OUTPUT = -0.85;
    public static final double PIVOT_MAX_OUTPUT = 0.85;

    public static final double AMP_PIVOT_POSITION = 13.8;
    public static final double PASSING_PIVOT_POSITION = 15.5;
    public static final double SPEAKER_PIVOT_POSITION = 19.7;

    public static final double FLOOR_TO_SHOOTER = Units.inchesToMeters(7);
  }

  public static final class TransportConstants{
    public static final int TOP_TRANSPORT_ID = 34;
    public static final int BOT_TRANSPORT_ID = 35;

    public static final int IR_SENSOR_CHANNEL = 9;
  }

  public static final class AmpBarConstants{
    public static final int AMP_BAR_ID = 41;

    public static final double AMP_BAR_kP = 0.25;
    public static final double AMP_BAR_kI = 0;
    public static final double AMP_BAR_kD = 0;

    public static final double AMP_BAR_MIN_OUTPUT = -0.5;
    public static final double AMP_BAR_MAX_OUTPUT = 0.5;

    // Zero at Top of Shooter at Lowest Pivot
    public static final double STOWED_ROT = 3.0;
    public static final double DEPLOYED_ROT = 22.0;
  }

  public static final class FieldConstants{
    public static final double FIELD_LENGTH = 16.54175;
    public static final double FIELD_WIDTH = 8.21055;

    public static final double SPEAKER_HEIGHT = Units.inchesToMeters(80.515);
  }

  public static final class VisionConstants{
    //Limelight Offest with Mount
    // LL Forward = 0.2667 LL Up = 0.46355
    public static final String LL_NAME = "limelight";

    public static final Transform3d ROBOT_TO_SHOOTER_LL = new Transform3d(
      new Translation3d( 
        -Units.inchesToMeters(0.2732),
        Units.inchesToMeters(5.8752),
        Units.inchesToMeters(26.4144)
      ), 
      new Rotation3d());

      // public static final PhotonVisionBackend.StandardDeviation PHOTON_VISION_STD_DEV =
      // (distance, count) -> {
      //     double distanceMultiplier = Math.pow(distance - ((count - 1) * 2), 2);
      //     double translationalStdDev = (0.05 / (count)) * distanceMultiplier + 0.05;
      //     double rotationalStdDev = 0.2 * distanceMultiplier + 0.1;
      //     return VecBuilder.fill(
      //             translationalStdDev,
      //             translationalStdDev,
      //             rotationalStdDev
      //     );
      // };

      public static final Vector<N3> LIMELIGHT_STD_DEV = VecBuilder.fill(.7, .7, .9999999);
      
      public static final double AMBIGUITY_FILTER = 0.3;
      public static final double DISTANCE_FILTER = FieldConstants.FIELD_LENGTH / 2;
  }
}
