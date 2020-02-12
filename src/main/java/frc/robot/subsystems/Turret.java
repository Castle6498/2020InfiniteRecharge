package frc.robot.subsystems;

import frc.robot.Constants;
import frc.lib.util.Rotation2d;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Turret subsystem controls the direction the ball is fired. On the Turret
 * assembly is the Hood and Flywheel. The Turret can only rotate within 240
 * degrees (+/- 120), and mechanical bumper switches indicate when the
 * mechanical limits are reached. This is part of the Superstructure superclass.
 * 
 * The ball is first picked up with the Intake then is fed to the Flywheel with
 * the HoodRoller. The Turret controls the direction that the ball is fired at.
 * Finally, the Hood controls the output angle and trajectory of the shot.
 * 
 * @see Flywheel
 * @see Hood
 * @see HoodRoller
 * @see Intake
 * @see Superstructure
 */
public class Turret extends Subsystem {
    private TalonFX falcon_;

    public Turret() {
        // The turret has one Talon.
        falcon_ = new TalonFX(Constants.kTurretTalonId);
        falcon_.setNeutralMode(NeutralMode.Brake);
        falcon_.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,LimitSwitchNormal.NormallyOpen);
  
      
        falcon_.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);//.setStatusFrameRateMs(StatusFrameRate.Feedback, 10);
        falcon_.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);//setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
      

        falcon_.config_kP(0, Constants.kTurretKp);
        falcon_.config_kI(0, Constants.kTurretKi);
        falcon_.config_kD(0, Constants.kTurretKd);
        falcon_.config_kF(0, Constants.kTurretKf);
        falcon_.config_IntegralZone(0, Constants.kTurretIZone);
        falcon_.configClosedloopRamp(Constants.kTurretRampRate);
        falcon_.configOpenloopRamp(Constants.kTurretRampRate);

        
      //  falcon_.sensorre(true);

        falcon_.setInverted(TalonFXInvertType.Clockwise);

        // We use soft limits to make sure the turret doesn't try to spin too
        // far.
        falcon_.configForwardSoftLimitEnable(true);//.enableForwardSoftLimit(true);
        falcon_.configReverseSoftLimitEnable(true);
        falcon_.configForwardSoftLimitThreshold((int) (Constants.kSoftMaxTurretAngle / (360.0 * Constants.kTurretRotationsPerTick)));//setForwardSoftLimit(Constants.kSoftMaxTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
        falcon_.configReverseSoftLimitThreshold((int) (Constants.kSoftMinTurretAngle / (360.0 * Constants.kTurretRotationsPerTick)));//setReverseSoftLimit(Constants.kSoftMinTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
    }

    // Set the desired angle of the turret (and put it into position control
    // mode if it isn't already).
    public synchronized void setDesiredAngle(Rotation2d angle) {
        //talon_.changeControlMode(CANTalon.TalonControlMode.Position);
        falcon_.set(ControlMode.Position, angle.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick));
    }

    // Manually move the turret (and put it into vbus mode if it isn't already).
    synchronized void setOpenLoop(double speed) {
        //talon_.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        falcon_.set(ControlMode.PercentOutput, speed);
    }

    // Tell the Talon it is at a given position.
    public synchronized void reset(Rotation2d actual_rotation) {
        falcon_.setSelectedSensorPosition((int) (actual_rotation.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick))); //setPosition(actual_rotation.getRadians() / (2 * Math.PI * Constants.kTurretRotationsPerTick));
    }

    public synchronized Rotation2d getAngle() {
        return Rotation2d.fromRadians(Constants.kTurretRotationsPerTick * falcon_.getSelectedSensorPosition() * 2 * Math.PI);
    }

    public synchronized boolean getForwardLimitSwitch() {
        return (falcon_.isFwdLimitSwitchClosed()==1) ? true : false;
    }

    public synchronized boolean getReverseLimitSwitch() {
        return (falcon_.isRevLimitSwitchClosed()==1) ? true : false;
    }

    public synchronized double getSetpoint() {
        return falcon_.getClosedLoopTarget() * Constants.kTurretRotationsPerTick * 360.0;
    }

    private synchronized double getError() {
        return getAngle().getDegrees() - getSetpoint();
    }

    // We are "OnTarget" if we are in position mode and close to the setpoint.
    public synchronized boolean isOnTarget() {
        return (Math.abs(getError()) < Constants.kTurretOnTargetTolerance);
    }

   

    @Override
    public synchronized void stop() {
        setOpenLoop(0);
    }

    @Override
    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("turret_error", getError());
        SmartDashboard.putNumber("turret_angle", getAngle().getDegrees());
        SmartDashboard.putNumber("turret_setpoint", getSetpoint());
        SmartDashboard.putBoolean("turret_fwd_limit", getForwardLimitSwitch());
        SmartDashboard.putBoolean("turret_rev_limit", getReverseLimitSwitch());
        SmartDashboard.putBoolean("turret_on_target", isOnTarget());
    }

    @Override
    public void zeroSensors() {
        reset(new Rotation2d());
    }
}