package com.yellowbkpk.pool.game;

import java.util.ArrayList;
import java.util.List;

import com.yellowbkpk.util.anim.ControllerIF;
import com.yellowbkpk.util.vecmath.Vector3D;

public class PoolGame implements ControllerIF {

    private static final Vector3D VEC_ORIGIN = new Vector3D(0,0,0);
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
    private int demoStage = 0;
    
    public PoolGame() {
        balls = new ArrayList<Ball>();
    }

    public List<Ball> getDrawables() {
        return balls;
    }

    private float mag(Vector3D a) {
        return a.length();
    }

    private Vector3D normal(Vector3D a) {
        return a.normalize();
    }

    private Vector3D scale(float scale, Vector3D a) {
        return a.scale(scale);
    }

    private float dot(Vector3D a, Vector3D b) {
        return a.dot(b);
    }

    private Vector3D cross(Vector3D a, Vector3D b) {
        return a.cross(b);
    }

    private Vector3D neg(Vector3D a) {
        return a.negative();
    }

    private Vector3D add(Vector3D a, Vector3D b) {
        return a.add(b);
    }

    private Vector3D diff(Vector3D a, Vector3D b) {
        return a.subtract(b);
    }

    public synchronized void update(float dt) {
        System.err.println("Update ("+dt+")");
        moveBalls(dt);
        
        for (Ball ball : balls) {
            if(ball.isMoving() && ball.isAlive()) {
                float radius = ball.getRadius();
                if(ball.isSliding()) {
                    
                    // Calculate the relative perimeter velocity at the point of contact with the felt
                    Vector3D vp = add(ball.getLinearVelocity(), cross(new Vector3D(0,0,-ball.getRadius()), ball.getAngularVelocity()));
                    
                    // Force of friction
                    Vector3D friction = scale(-MU_SLIDE * BALL_MASS * GRAVITY, normal(vp));
                    
                    Vector3D vaccel = scale(1f / BALL_MASS, friction);
                    Vector3D waccel = scale(5f / (2f * BALL_MASS * radius * radius), cross(new Vector3D(0,0,-radius), friction));
                    
                    // Perform accelerations
                    ball.setAngularVelocity(add(ball.getAngularVelocity(), scale(dt, waccel)));
                    ball.setLinearVelocity(add(ball.getLinearVelocity(), scale(dt, vaccel)));
                    
                    // Recalculate perimeter speed
                    Vector3D vp2 = add(ball.getLinearVelocity(), cross(ball.getAngularVelocity(), new Vector3D(0,0,-radius)));

                    float vpPar = dot(vp, diff(vp,vp2));
                    float vp2Par = dot(vp2, diff(vp,vp2));
                    
                    // Check to see if it should start to roll
                    if((vpPar > -2.0f && vp2Par < 2.0f) || (vp2Par > -2.0f && vpPar < 2.0f)) {
                        // Align linear velocity with angular velocity
                        ball.setLinearVelocity(cross(ball.getAngularVelocity(), new Vector3D(0,0,radius)));
                        ball.setIsRolling();
                    }
                } else { // Rolling
                    // Relative perimeter velocity at point of contact with the felt
                    Vector3D vp = cross(new Vector3D(0,0,-radius), ball.getAngularVelocity());
                    
                    // Force of friction
                    Vector3D friction = scale(MU_ROLL * BALL_MASS * GRAVITY, normal(vp));
                    Vector3D waccel = scale(5f / (2f * BALL_MASS * radius * radius), cross(new Vector3D(0,0,-radius), friction));
                    
                    ball.setAngularVelocity(add(ball.getAngularVelocity(), scale(dt,waccel)));
                    ball.setLinearVelocity(cross(ball.getAngularVelocity(), new Vector3D(0,0,radius)));
                }
                
                // Check to see if the balls are about to stop
                if(ball.getAngularVelocity().length() < OMEGA_MIN && ball.getLinearVelocity().length() < VEL_MIN) {
                    ball.setLinearVelocity(VEC_ORIGIN);
                    ball.setAngularVelocity(VEC_ORIGIN);
                    ball.setIsStationary();
                }
            }
        }
        System.err.println("Update done");
    }

    private void moveBalls(float delta) {
        int event = EVT_NONE;
        int demoStage = 0;
        Ball firstBall = null;
        Ball secondBall = null;
        float timestep = delta;
        int bankAxis = -1;
        
        // TODO Toss the balls that are out of bounds
        for (int b = 0; b < balls.size(); b++) {
            Ball ball = balls.get(b);
            if((ball.getPosition().y > getHeight() + 10f || ball.getPosition().y < -10f) ||(ball.getPosition().x + 10f > getWidth() || ball.getPosition().x < -10f)) {
                balls.remove(ball);
            }
        }
        
        // Determine if any collisions or banks will occur within time dt
        for (Ball ball : balls) {
            if(ball.isAlive() && ball.isMoving()) {
                // Check for collisions
                for (Ball otherBall : balls) {
                    if(ball != otherBall) {
                        float collisionTime = calculateCollisionTime(ball, otherBall, delta);
                        
                        // If a collision is about to occur
                        if(0f <= collisionTime && collisionTime < timestep) {
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
                    bankBall(firstBall, bankAxis);
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

    private void bankBall(Ball ball, int axis) {
        Vector3D rv, ff, dw;
        Vector3D vt, vn = null; // Tangential and normal components of velocity
        Vector3D vp;     // Perimeter velocity of ball at contact with cushion
        float radius = ball.getRadius();
        float impulseTime = 0.05f; // Time ball is in contact with cushion
        
        if(demoStage == 3) {
            ball.setIsStationary();
        } else {
            if(axis == AXIS_Y) {
                vn = new Vector3D(0,ball.getLinearVelocity().y,0);
            } else if(axis == AXIS_X) {
                vn = new Vector3D(ball.getLinearVelocity().x,0,0);
            }
            vt = diff(ball.getLinearVelocity(), vn);
            
            rv = scale(radius, normal(new Vector3D(vn.x, vn.y, 0.26f * radius)));
            vp = add(vt, cross(ball.getAngularVelocity(), rv));
            
            ff = scale(-mag(vn) * MU_WALL, normal(vp));
            
            dw = scale(5f * impulseTime / (2f * BALL_MASS * radius * radius), ff);
            
            // Parallel component
            ball.setAngularVelocity(VEC_ORIGIN);
            
            // Normal component
            Vector3D linear = add(ball.getLinearVelocity(),add(scale(1f+(float)Math.sqrt(1-CUSHION_LOSS),neg(vn)),ff));
            linear.z = 0f;
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
            // normVec = normal(diff(a.r,b.r))
            normVec = normal(diff(firstBall.getPosition(), secondBall.getPosition()));
            
            // Linear transfer
            an = scale(dot(firstBall.getLinearVelocity(),neg(normVec)),neg(normVec));
            at = diff(firstBall.getLinearVelocity(),an);
            bn = scale(dot(secondBall.getLinearVelocity(),normVec),normVec);
            bt = diff(secondBall.getLinearVelocity(),bn);
            
            firstBall.setLinearVelocity(at.add(bn));
            secondBall.setLinearVelocity(bt.add(an));
            
            // Angular transfer (was commented out in original code)
            Vector3D vp1 = cross(scale(radius,neg(normVec)),firstBall.getAngularVelocity());
            Vector3D vp2 = cross(scale(radius,normVec),secondBall.getAngularVelocity());
            
            Vector3D vpr1 = diff(vp1,vp2);
            Vector3D vpr2 = diff(vp2,vp1);
            
            Vector3D ff1 = scale(-MU_BALL*BALL_MASS*mag(diff(an,bn)),
                  normal(add(vpr1,at)));
            Vector3D ff2 = scale(-MU_BALL*BALL_MASS*mag(diff(an,bn)),
                  normal(add(vpr2,bt)));
            
            Vector3D dw1 = scale(5f*impulseTime/(2f*BALL_MASS*radius*radius),
                  cross(scale(radius,neg(normVec)),ff1));
            Vector3D dw2 = scale(5f*impulseTime/(2f*BALL_MASS*radius*radius),
                  cross(scale(radius,normVec),ff2));
            
            firstBall.setAngularVelocity(add(firstBall.getAngularVelocity(),dw1));
            secondBall.setAngularVelocity(add(secondBall.getAngularVelocity(),dw2));
            
            firstBall.setAngularVelocity(VEC_ORIGIN);
            secondBall.setAngularVelocity(VEC_ORIGIN);
            
            firstBall.setIsSliding();
            secondBall.setIsSliding();
        }
    }

    private void updatePos(float timestep) {
        System.err.println(timestep);
        for (Ball ball : balls) {
            if (ball.isMoving() && ball.isAlive()) {
                // Translate balls
                ball.setPosition(add(ball.getPosition(), scale(timestep, ball.getLinearVelocity())));
                
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
            railPosition = getHeight() / 2f;
        } else { // Y Axis
            r = ball.getPosition().y;
            v = ball.getLinearVelocity().y;
            railPosition = getWidth() / 2f;
        }
        
        // Position of ball is given by r + v * t
        if(v != 0) { // Ball can't be moving parallel to the rail
            return ((v / Math.abs(v)) * (railPosition - radius) - r) / v;
        }
        
        return 1000;
    }

    private float getHeight() {
        return 350;
    }

    private float getWidth() {
        return 350;
    }

    private float calculateCollisionTime(Ball ball, Ball otherBall, float delta) {
        float time1;
        float time2;
        float a, b, c;
        Vector3D dv, dr;
        float radius = ball.getRadius();
        
        dv = diff(ball.getLinearVelocity(), otherBall.getLinearVelocity());
        dr = diff(ball.getPosition(), otherBall.getPosition());
        
        a = dv.dot(dv);
        b = dr.dot(dv) / a;
        c = (dr.dot(dr) - 4f * radius * radius) / a;
        
        // t^2 + 2b*t + c = 0
        c = (b*b>c) ? (float) Math.sqrt(b*b-c) : 1000;
        time1 = -b + c;
        time2 = -b - c;
        
        return Math.min(time1, time2);
    }

    public synchronized void addBall(Ball ball) {
        System.err.println("======================");
        ball.setIsSliding();
        balls.add(ball);
    }

}
