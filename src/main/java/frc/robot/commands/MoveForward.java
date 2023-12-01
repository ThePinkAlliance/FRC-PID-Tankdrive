// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class MoveForward extends CommandBase {
  Drivetrain drivetrain;
  double targetPosition;
  PIDController controller;

  /** Creates a new MoveForward. */
  public MoveForward(Drivetrain drivetrain, double targetPosition) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.targetPosition = targetPosition;
    this.controller = new PIDController(1, 0, 0);

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetAll();
    controller.setSetpoint(targetPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPower = controller.calculate(drivetrain.getLeftPosition()) * (1 / targetPosition);
    double rightPower = controller.calculate(drivetrain.getRightPosition()) * (1 / targetPosition);

    drivetrain.setSpeeds(leftPower, rightPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setSpeeds(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
