package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import javax.net.ssl.SSLEngineResult;

@Autonomous
public class StateMachineExample extends OpMode{

    DigitalChannel digitalTouch;  // Hardware Device Object
    Servo servo1;
    DcMotor motor1;

    enum State{
        START,
        WAIT_FOR_SENSOR_RELEASE,
        WAIT_FOR_TURN,
        STOP,
        DONE
    }
    State state = State.START;

    @Override
    public void init(){
        // get a reference to our digitalTouch object.
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        servo1  = hardwareMap.get(Servo.class, "servo");
        motor1  = hardwareMap.get(DcMotor.class, "motor");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void start(){
        state = state.START;
    }

    @Override
    public void loop(){
        telemetry.addData("State:", state);
        switch(state){
            case START:
                servo1.setPosition(0.5);
                if(digitalTouch.getState() != true){
                    state = State.WAIT_FOR_SENSOR_RELEASE;
                }
                break;
            case WAIT_FOR_SENSOR_RELEASE:
                servo1.setPosition(0.0);
                if(digitalTouch.getState() == true){
                    state = State.WAIT_FOR_TURN;
                }
                break;
            case WAIT_FOR_TURN:
                servo1.setPosition(1.0);
                motor1.setPower(0.5);
                if(angle > 90){
                    state = state.STOP;
                }
                break;
            case STOP:
                motor1.setPower(0);
                state = state.DONE;
                break;
            default:
                telemetry.addData("Auto:", "Finished");
        }
    }

}
