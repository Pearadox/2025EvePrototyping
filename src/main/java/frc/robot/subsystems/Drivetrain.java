// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.text.DecimalFormat;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.SwerveConstants;

public class Drivetrain extends SubsystemBase {
  private SwerveModule leftFront;
  private SwerveModule rightFront;
  private SwerveModule leftBack;
  private SwerveModule rightBack;

  private SlewRateLimiter frontLimiter;
  private SlewRateLimiter sideLimiter;
  private SlewRateLimiter turnLimiter;

  private AHRS gyro;

  public static final ShuffleboardTab swerveTab = Shuffleboard.getTab("Swerve");
  private GenericEntry leftFrontStateEntry;
  private GenericEntry rightFrontStateEntry;
  private GenericEntry leftBackStateEntry;
  private GenericEntry rightBackStateEntry;
  private GenericEntry robotAngleEntry;
  private GenericEntry angularSpeedEntry;

  private static final Drivetrain DRIVETRAIN = new Drivetrain();

  public static Drivetrain getInstance(){
    return DRIVETRAIN;
  }

  /** Creates a new SwerveDrivetrain. */
  public Drivetrain() {
    new Thread(() -> {
      try{
        Thread.sleep(1000);
        zeroHeading();
      }
      catch(Exception e){}
    }).start();

    leftFront = new SwerveModule(
      SwerveConstants.LEFT_FRONT_DRIVE_ID, 
      SwerveConstants.LEFT_FRONT_TURN_ID, 
      false, 
      false, 
      SwerveConstants.LEFT_FRONT_CANCODER_ID, 
      SwerveConstants.LEFT_FRONT_OFFSET);

    rightFront = new SwerveModule(
      SwerveConstants.RIGHT_FRONT_DRIVE_ID, 
      SwerveConstants.RIGHT_FRONT_TURN_ID, 
      false, 
      false, 
      SwerveConstants.RIGHT_FRONT_CANCODER_ID, 
      SwerveConstants.RIGHT_FRONT_OFFSET);

    leftBack = new SwerveModule(
      SwerveConstants.LEFT_BACK_DRIVE_ID, 
      SwerveConstants.LEFT_BACK_TURN_ID, 
      false, 
      false, 
      SwerveConstants.LEFT_BACK_CANCODER_ID, 
      SwerveConstants.LEFT_BACK_OFFSET);
    
    rightBack = new SwerveModule(
      SwerveConstants.RIGHT_BACK_DRIVE_ID, 
      SwerveConstants.RIGHT_BACK_TURN_ID, 
      false, 
      false, 
      SwerveConstants.RIGHT_BACK_CANCODER_ID, 
      SwerveConstants.RIGHT_BACK_OFFSET);

    frontLimiter = new SlewRateLimiter(SwerveConstants.TELE_DRIVE_MAX_ACCELERATION);
    sideLimiter = new SlewRateLimiter(SwerveConstants.TELE_DRIVE_MAX_ACCELERATION);
    turnLimiter = new SlewRateLimiter(SwerveConstants.TELE_DRIVE_MAX_ANGULAR_ACCELERATION);

    gyro = new AHRS(NavXComType.kMXP_SPI);
  

    
    // AutoBuilder.configureHolonomic(
    //   this::getPose,
    //   this::resetPose,
    //   this::getRobotRelativeSpeeds,
    //   this::driveRobotRelative,
    //   SwerveConstants.AUTO_CONFIG,
    //   () -> isRedAlliance(),
    //   this);

    leftFrontStateEntry = swerveTab.add("Left Front Module State", leftFront.getState().toString()).withSize(4, 1).withPosition(0, 0).getEntry();
    rightFrontStateEntry = swerveTab.add("Right Front Module State", rightFront.getState().toString()).withSize(4, 1).withPosition(0, 1).getEntry();
    leftBackStateEntry = swerveTab.add("Left Back Module State", leftBack.getState().toString()).withSize(4, 1).withPosition(0, 2).getEntry();
    rightBackStateEntry = swerveTab.add("Right Back Module State", rightBack.getState().toString()).withSize(4, 1).withPosition(0, 3).getEntry();
    robotAngleEntry = swerveTab.add("Robot Angle", getHeading()).withSize(1, 1).withPosition(4, 1).getEntry();
    angularSpeedEntry = swerveTab.add("Angular Speed", new DecimalFormat("#.00").format((-gyro.getRate() / 180)) + "\u03C0" + " rad/s").withSize(1, 1).withPosition(5, 1).getEntry();
  }

  @Override
  public void periodic() {
    // RobotContainer.poseEstimation.updateOdometry(getHeadingRotation2d(), getModulePositions());

    // SmarterDashboard.putString("Left Front Module State", leftFront.getState().toString(), "Drivetrain");
    // SmarterDashboard.putString("Right Front Module State", rightFront.getState().toString(), "Drivetrain");
    // SmarterDashboard.putString("Left Back Module State", leftBack.getState().toString(), "Drivetrain");
    // SmarterDashboard.putString("Right Back Module State", rightBack.getState().toString(), "Drivetrain");
    // SmarterDashboard.putNumber("Robot Angle", getHeading(), "Drivetrain");
    // SmarterDashboard.putString("Angular Speed", new DecimalFormat("#.00").format((-gyro.getRate() / 180)) + "\u03C0" + " rad/s", "Drivetrain");
    // SmarterDashboard.putString("Odometry", getPose().toString(), "Drivetrain");

    leftFrontStateEntry.setString(leftFront.getState().toString());
    rightFrontStateEntry.setString(rightFront.getState().toString());
    leftBackStateEntry.setString(leftBack.getState().toString());
    rightBackStateEntry.setString(rightBack.getState().toString());
    robotAngleEntry.setDouble(getHeading());
    angularSpeedEntry.setString(new DecimalFormat("#.00").format((-gyro.getRate() / 180)) + "\u03C0" + "rad/s");
  }

  public void swerveDrive(double frontSpeed, double sideSpeed, double turnSpeed, 
    boolean fieldOriented, Translation2d centerOfRotation, boolean deadband, int exponent){ //Drive with rotational speed control w/ joystick

    if(deadband){
      frontSpeed = Math.abs(frontSpeed) > 0.15 ? frontSpeed : 0;
      sideSpeed = Math.abs(sideSpeed) > 0.15 ? sideSpeed : 0;
      turnSpeed = Math.abs(turnSpeed) > 0.15 ? turnSpeed : 0;
    }

    
    frontSpeed = Math.pow(frontSpeed, exponent) * exponent % 2 == 0 ? Math.signum(frontSpeed): 1;
    sideSpeed = Math.pow(sideSpeed, exponent) * exponent % 2 == 0 ? Math.signum(sideSpeed): 1;
    turnSpeed = Math.pow(turnSpeed, exponent) * exponent % 2 == 0 ? Math.signum(turnSpeed): 1;

    frontSpeed = frontLimiter.calculate(frontSpeed) * SwerveConstants.TELE_DRIVE_MAX_SPEED;
    sideSpeed = sideLimiter.calculate(sideSpeed) * SwerveConstants.TELE_DRIVE_MAX_SPEED;
    turnSpeed = turnLimiter.calculate(turnSpeed) * SwerveConstants.TELE_DRIVE_MAX_ANGULAR_SPEED;

    ChassisSpeeds chassisSpeeds;
    if(fieldOriented){
      chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(frontSpeed, sideSpeed, turnSpeed, getHeadingRotation2d());
    }
    else{
      chassisSpeeds = new ChassisSpeeds(frontSpeed, sideSpeed, turnSpeed);
    }

    SwerveModuleState[] moduleStates = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds, centerOfRotation);

    setModuleStates(moduleStates);
  }

  public void swerveDrive(ChassisSpeeds chassisSpeeds, Translation2d centerOfRotation){ //Drive with field relative chassis speeds
    chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, getHeadingRotation2d());

    SwerveModuleState[] moduleStates = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds, centerOfRotation);

    setModuleStates(moduleStates);
  }

  public void setAllIdleMode(boolean brake){
    if(brake){
      leftFront.setBrake(true);
      rightFront.setBrake(true);
      leftBack.setBrake(true);
      rightBack.setBrake(true);
    }
    else{
      leftFront.setBrake(false);
      rightFront.setBrake(false);
      leftBack.setBrake(false);
      rightBack.setBrake(false);
    }
  }
  
  public void resetAllEncoders(){
    leftFront.resetEncoders();
    rightFront.resetEncoders();
    leftBack.resetEncoders();
    rightBack.resetEncoders();
  }

  // public Pose2d getPose(){
  //   return RobotContainer.poseEstimation.getEstimatedPose();
    
  // }

  // public void resetPose(Pose2d pose) {
  //   RobotContainer.poseEstimation.resetPose(pose);
  // }

  public ChassisSpeeds getRobotRelativeSpeeds(){
    return SwerveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleStates());
  }

  public void driveRobotRelative(ChassisSpeeds chassisSpeeds){
    SwerveModuleState[] moduleStates = SwerveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
    setModuleStates(moduleStates);
  }

  public ChassisSpeeds getFieldRelativeSpeeds(){
    ChassisSpeeds chassisSpeeds = SwerveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleStates());
    return ChassisSpeeds.fromRobotRelativeSpeeds(chassisSpeeds, getHeadingRotation2d());
  }

  public void zeroHeading(){
    gyro.zeroYaw();
  }

  public void setHeading(double heading){
    gyro.setAngleAdjustment(heading);
  }

  public double getHeading(){
    return Math.IEEEremainder(-gyro.getAngle(), 360); //clamp heading between -180 and 180
  }

  public Rotation2d getHeadingRotation2d(){
    return Rotation2d.fromDegrees(getHeading());
  }

  public void stopModules(){
    leftFront.stop();
    leftBack.stop();
    rightFront.stop();
    rightBack.stop();
  }

  public void setModuleStates(SwerveModuleState[] moduleStates){
    SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, SwerveConstants.DRIVETRAIN_MAX_SPEED);
    leftFront.setDesiredState(moduleStates[0]);
    rightFront.setDesiredState(moduleStates[1]);
    leftBack.setDesiredState(moduleStates[2]);
    rightBack.setDesiredState(moduleStates[3]);
  }

  public SwerveModuleState[] getModuleStates(){
    SwerveModuleState[] states = new SwerveModuleState[4];
    states[0] = leftFront.getState();
    states[1] = rightFront.getState();
    states[2] = leftBack.getState();
    states[3] = rightBack.getState();
    return states;
  } 

  public SwerveModulePosition[] getModulePositions(){
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    positions[0] = leftFront.getPosition();
    positions[1] = rightFront.getPosition();
    positions[2] = leftBack.getPosition();
    positions[3] = rightBack.getPosition();
    return positions;
  }

  public boolean isRedAlliance(){
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent()) {
        return alliance.get() == DriverStation.Alliance.Red;
    }
    return false;
  }


  public Command rumbleController(){
    return new InstantCommand(() -> RobotContainer.driverController.setRumble(RumbleType.kBothRumble, 0.25))
      .andThen(new WaitCommand(0.5))
      .andThen(new InstantCommand(() -> RobotContainer.driverController.setRumble(RumbleType.kBothRumble, 0)));
  }
}