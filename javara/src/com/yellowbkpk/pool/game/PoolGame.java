package com.yellowbkpk.pool.game;

import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.vecmath.Vector3D;

public class PoolGame implements ControllerIF {

    private static final float BALL_MASS = 1.0f;
    private static final float GRAVITY = 9.81f;
    
    private static final float MU_BALL = 0.1f;
    private static final float MU_SLIDE = 0.1f;
    private static final float MU_ROLL = 0.1f;
    private static final float MU_WALL = 0.1f;
    
    private static final float OMEGA_MIN = 0;
    private static final float VEL_MIN = 0;
    
    private static final int EVT_NONE = 0;
    private static final int EVT_COLLISION = 1;
    private static final int EVT_BANK = 2;
    
    private static final int AXIS_X = 0;
    private static final int AXIS_Y = 1;
    private static final float CUSHION_LOSS = 0.1f;
    
    private List<Ball> balls;
    private long prevTime;
    private int demoStage;
    
    public PoolGame() {
        balls = new ArrayList<Ball>();
    }

    public List<Ball> getDrawables() {
        return balls;
    }

    public synchronized void update(float delta) {
        moveBalls(delta);
        
        for (Ball ball : balls) {
            if(ball.isMoving() && ball.isAlive()) {
                if(ball.isSliding()) {
                    // Calculate the relative perimeter velocity at the point of contact with the felt
                    Vector3D vp = ball.getLinearVelocity().add(ball.getAngularVelocity().cross(new Vector3D(0, 0, -ball.getRadius())));
                    
                    // Force of friction
                    Vector3D friction = vp.normalize().scale(-MU_SLIDE * BALL_MASS * GRAVITY);
                    
                    Vector3D vaccel = friction.scale(1f / BALL_MASS);
                    Vector3D waccel = friction.cross(new Vector3D(0,0,-ball.getRadius())).scale(5/(2*BALL_MASS*ball.getRadius()*ball.getRadius()));
                    
                    // Perform accelerations
                    ball.setAngularVelocity(waccel.scale(delta).add(ball.getAngularVelocity()));
                    ball.setLinearVelocity(vaccel.scale(delta).add(ball.getLinearVelocity()));
                    
                    // Recalculate perimeter speed
                    Vector3D vp2 = ball.getLinearVelocity().add(ball.getAngularVelocity().cross(new Vector3D(0,0,-ball.getRadius())));
                    
                    float vpPar = vp.dot(vp.subtract(vp2));
                    float vp2Par = vp2.dot(vp.subtract(vp2));
                    
                    // Check to see if it should start to roll
                    if((vpPar > 0.0 && vp2Par < 0.0) || (vp2Par > 0.0 && vpPar < 0.0)) {
                        // Align linear velocity with angular velocity
                        ball.setLinearVelocity(ball.getAngularVelocity().cross(new Vector3D(0,0,ball.getRadius())));
                        ball.setIsRolling();
                    }
                } else { // Rolling
                    // Relative perimeter velocity at point of contact with the felt
                    Vector3D vp = ball.getAngularVelocity().cross(new Vector3D(0,0,-ball.getRadius()));
                    
                    // Force of friction
                    Vector3D friction = vp.normalize().scale(MU_ROLL * BALL_MASS * GRAVITY);
                    
                    Vector3D waccel = friction.cross(new Vector3D(0,0,-ball.getRadius())).scale(5/(2*BALL_MASS*ball.getRadius()*ball.getRadius()));
                    
                    ball.setAngularVelocity(ball.getAngularVelocity().add(waccel.scale(delta)));
                    ball.setLinearVelocity(ball.getAngularVelocity().cross(new Vector3D(0,0,ball.getRadius())));
                }
                
                // Check to see if the balls are about to stop
                if(ball.getAngularVelocity().length() < OMEGA_MIN && ball.getLinearVelocity().length() < VEL_MIN) {
                    ball.setLinearVelocity(new Vector3D(0,0,0));
                    ball.setAngularVelocity(new Vector3D(0,0,0));
                }
            }
        }
    }

    private void moveBalls(float delta) {
        int event = EVT_NONE;
        int demoStage = 0;
        Ball firstBall = null;
        Ball secondBall = null;
        float timestep = delta;
        int bankAxis = -1;
        
        // Determine if any collisions or banks will occur within time dt
        for (Ball ball : balls) {
            if(ball.isAlive() && ball.isMoving()) {
                // Check for collisions
                for (Ball otherBall : balls) {
                    if(ball != otherBall) {
                        float collisionTime = calculateCollisionTime(ball, otherBall, delta);
                        
                        // If a collision is about to occur
                        if(0 <= collisionTime && collisionTime < timestep) {
                            firstBall = ball;
                            secondBall = otherBall;
                            event = EVT_COLLISION;
                            timestep = collisionTime;
                        }
                    }
                }
                
                // Check for bank
                for (int axis = AXIS_X; axis < 2; axis++) {
                    float bankTime = calculateBankTime(ball, axis, delta);
                    
                    if(0 <= bankTime && bankTime < timestep) {
                        firstBall = ball;
                        bankAxis = axis;
                        event = EVT_BANK;
                        timestep = bankTime;
                    }
                }
            }
            
            // Update position of the ball
            if(demoStage == 2) {
                updatePos(delta);
            } else {
                updatePos(timestep);
            }
            
            if(demoStage == 0 || demoStage > 2) {
                // Update velocity vectors if necessary
                if(event == EVT_COLLISION) {
                    collideBalls(firstBall, secondBall);
                } else if(event == EVT_BANK) {
                    bankBalls(firstBall, bankAxis);
                }
            }
            
            if(demoStage != 2) {
                // Recurse
                if(timestep < delta) {
                    moveBalls(delta - timestep);
                }
            }
        }
    }

    private void bankBalls(Ball ball, int axis) {
        Vector3D rv, ff, dw;
        Vector3D vt, vn = null; // Tangential and normal components of velocity
        Vector3D vp;     // Perimeter velocity of ball at contact with cushion
        float radius = ball.getRadius();
        float impuseTime = 0.05f; // Time ball is in contact with cushion
        
        if(demoStage == 3) {
            ball.setIsStationary();
        } else {
            if(axis == AXIS_Y) {
                vn = new Vector3D(0,ball.getLinearVelocity().y,0);
            } else if(axis == AXIS_X) {
                vn = new Vector3D(ball.getLinearVelocity().x,0,0);
            }
            vt = ball.getLinearVelocity().subtract(vn);
            
            rv = new Vector3D(vn.x, vn.y, 0.26f * radius).normalize().scale(radius);
            vp = vt.add(ball.getAngularVelocity().cross(rv));
            
            ff = vp.normalize().scale(-vn.length() * MU_WALL);
            
            dw = ff.scale(5 * impuseTime / (2 * BALL_MASS * radius * radius));
            
            // Parallel component
            ball.setAngularVelocity(new Vector3D(0,0,0));
            
            // Normal component
            // linear = add(ball.v,add(scale(1+Math.sqrt(1-CUSHION_LOSS),neg(vn)),ff))
            Vector3D linear = ball.getLinearVelocity().add(ff.add(vn.negative().scale(1+(float) Math.sqrt(1-CUSHION_LOSS))));
            ball.setLinearVelocity(linear);
            ball.setIsSliding();
        }
    }

    private void collideBalls(Ball firstBall, Ball secondBall) {
        Vector3D an, at, bn, bt, normVec;
        float radius = firstBall.getRadius();
        float impulseTime = 0.05f;
        
        if(demoStage == 3) {
            firstBall.setIsStationary();
        } else {
            normVec = firstBall.getPosition().subtract(secondBall.getPosition()).normalize();
            
            // Linear transfer
            // an = scale(dot(a.v,neg(normVec)),neg(normVec))
            an = normVec.negative().scale(firstBall.getLinearVelocity().dot(normVec.negative()));
            // at = diff(a.v,an)
            at = firstBall.getLinearVelocity().subtract(an);
            // bn = scale(dot(b.v,normVec),normVec)
            bn = normVec.scale(secondBall.getLinearVelocity().dot(normVec));
            // bt = diff(b.v,bn)
            bt = secondBall.getLinearVelocity().subtract(bn);
            
            firstBall.setLinearVelocity(at.add(bn));
            secondBall.setLinearVelocity(bt.add(an));
            
            // Angular transfer (was commented out in original code)
            firstBall.setAngularVelocity(new Vector3D(0,0,0));
            secondBall.setAngularVelocity(new Vector3D(0,0,0));
            
            firstBall.setIsSliding();
            secondBall.setIsSliding();
        }
    }

    private void updatePos(float timestep) {
        for (Ball ball : balls) {
            if (ball.isMoving() && ball.isAlive()) {
                // Translate balls
                ball.setPosition(ball.getPosition().add(ball.getLinearVelocity().scale(timestep / 8)));
                
                // TODO: Check for pocketed balls
                // TODO: Add "remove from game" call here.
                if (!(ball.getPosition().x >= 0 || ball.getPosition().x <= getWidth())
                        && !(ball.getPosition().y >= 0 || ball.getPosition().y <= getHeight())) {
                    ball.setIsDead();
                }
            }
        }
    }

    private float calculateBankTime(Ball ball, int axis, float delta) {
        float radius = ball.getRadius();
        float r;
        float v;
        float railPosition;
        
        if(axis == AXIS_X) { // X Axis
            r = ball.getPosition().x;
            v = ball.getLinearVelocity().x;
            railPosition = getHeight() / 2;
        } else { // Y Axis
            r = ball.getPosition().y;
            v = ball.getLinearVelocity().y;
            railPosition = getWidth() / 2;
        }
        
        // Position of ball is given by r + v * t
        if(v != 0) { // Ball can't be moving parallel to the rail
            return ((v / Math.abs(v)) * (railPosition - radius) - r) / v;
        }
        
        return 1000;
    }

    private int getHeight() {
        return 150;
    }

    private int getWidth() {
        return 150;
    }

    private float calculateCollisionTime(Ball ball, Ball otherBall, float delta) {
        float time1;
        float time2;
        float a, b, c;
        Vector3D dv, dr;
        float radius = ball.getRadius();
        
        dv = ball.getLinearVelocity().subtract(otherBall.getLinearVelocity());
        dr = ball.getPosition().subtract(otherBall.getPosition());
        
        a = dv.dot(dv);
        b = dr.dot(dv) / a;
        c = (dr.dot(dr) - 4 * radius * radius) / a;
        
        // t^2 + 2b*t + c = 0
        c = (b*b>c) ? (float) Math.sqrt(b*b-c) : 1000;
        time1 = -b + c;
        time2 = -b - c;
        
        return Math.min(time1, time2);
    }

    public synchronized void addBall(Ball ball) {
        ball.setIsSliding();
        balls.add(ball);
    }

}
