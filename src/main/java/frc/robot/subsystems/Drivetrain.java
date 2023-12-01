// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  private TalonSRX frontLeft;
  private TalonSRX backLeft;
  private TalonSRX frontRight;
  private TalonSRX backRight;

  private double circumference;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    this.frontLeft = new TalonSRX(2);
    this.backLeft = new TalonSRX(3);
    this.frontRight = new TalonSRX(1);
    this.backRight = new TalonSRX(4);

    this.backLeft.follow(frontLeft);
    this.backRight.follow(frontRight);

    this.frontLeft.setInverted(true);

    this.circumference = (2 * Math.PI * (5.875 / 2));
  }

  public void setSpeeds(double left, double right) {
    this.frontLeft.set(ControlMode.PercentOutput, left);
    this.frontRight.set(ControlMode.PercentOutput, right);
  }

  public double getLeftPosition() {
    /*
     * Note: I forgot to add the units per rotation for the encoder.
     */
    return ((this.frontLeft.getSelectedSensorPosition() / 600) / 2048) * circumference;
  }

  public double getRightPosition() {
    return ((this.frontRight.getSelectedSensorPosition() / 600) / 2048) * circumference;
  }

  public void resetLeft() {
    this.frontLeft.setSelectedSensorPosition(0);
  }

  public void resetRight() {
    this.frontRight.setSelectedSensorPosition(0);
  }

  public void resetAll() {
    resetLeft();
    resetRight();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("left position", getLeftPosition());
    SmartDashboard.putNumber("right position", getRightPosition());
  }
}
