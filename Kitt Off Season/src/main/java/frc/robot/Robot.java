/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Robot extends TimedRobot {

  public WPI_TalonSRX talonThree;
  public WPI_TalonSRX talonFour;
 // public Joystick j;
  public DifferentialDrive b;
  public Compressor compressor;
  public XboxController j;
  public Solenoid shifter;
  @Override
  public void robotInit() {
  
    talonThree = new WPI_TalonSRX(0);
    talonFour = new WPI_TalonSRX(1);
    talonThree.set(ControlMode.PercentOutput, 0);
    talonFour.set(ControlMode.PercentOutput, 0);
    //talonThree.setInverted(true);
    // sensorThree = talonThree.getSensorCollection();
    CameraServer.getInstance().startAutomaticCapture();
    compressor=new Compressor();
    b=new DifferentialDrive(talonThree,talonFour);

   // j = new Joystick(0);
   j=new XboxController(0);
   shifter = new Solenoid(0);
  }

  @Override
  public void robotPeriodic() {
  }

 
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }

  /**
   * This function is called periodically during operator control.
   */
@Override
public void teleopInit() {
  compressor.start();
}

  @Override
  public void teleopPeriodic() {
   // b.arcadeDrive(j.getY(), j.getX());
   driverArcadeDrive();
  }

  boolean driveReduction=false;
	double driveReductionAmount = .7; //remember a higher number means less reduction
	
	double turnReduction=.85;
	
	public void driverArcadeDrive() {
		double speed=0;
		double turn=j.getX(Hand.kLeft)*turnReduction;
		if(j.getTriggerAxis(Hand.kRight)>.05) {
			speed=j.getTriggerAxis(Hand.kRight);			
		}else if(j.getTriggerAxis(Hand.kLeft)>.05) {
			speed=-j.getTriggerAxis(Hand.kLeft);
			turn=-turn;
		}else {
			speed=0;
		}
		if(driveReduction) {
			turn=turn*driveReductionAmount;
			speed=speed*driveReductionAmount;
		}
		b.arcadeDrive(speed, turn);
	}

  
public void shifterPeriodic(){
  if(j.getBumper(Hand.kLeft))shifter.set(true);
  else if (j.getBumper(Hand.kRight))shifter.set(false);
}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }


  @Override
  public void disabledInit() {
    compressor.stop();
  }
}
