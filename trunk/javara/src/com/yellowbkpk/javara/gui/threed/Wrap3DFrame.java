package com.yellowbkpk.javara.gui.threed;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Wrap3DFrame extends JPanel {
    private static final double BOUNDSIZE = 100.0;

    private static final double Z_START = 9.0;

    private SimpleUniverse su;

    private BranchGroup sceneBG;

    private BoundingSphere bounds;

    public Wrap3DFrame() {
        setLayout(new BorderLayout());
        setOpaque(false);

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        canvas3D.setFocusable(true);
        canvas3D.requestFocus(); // the canvas now has focus, so receives key
        // events

        su = new SimpleUniverse(canvas3D);

        createSceneGraph();
        su.addBranchGraph(sceneBG);
    }

    private void createSceneGraph() {
        sceneBG = new BranchGroup();
        bounds = new BoundingSphere(new Point3d(0, 0, 0), BOUNDSIZE);

        lightScene(); // add the lights
        addBackground(); // add the sky
        sceneBG.addChild(new CheckerFloor().getBG()); // add the floor

        // add the target
        // PropManager propMan = new PropManager(TARGET, true);
        // sceneBG.addChild(propMan.getTG());
        // Vector3d targetVec = propMan.getLoc();
        // System.out.println("Location of target: " + targetVec);

        Vector3d targetVec = new Vector3d(0f, 0f, 0f);
        initUserControls(targetVec);

        sceneBG.compile(); // fix the scene
    }

    private void lightScene() {
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        // Set up the ambient light
        AmbientLight ambientLightNode = new AmbientLight(white);
        ambientLightNode.setInfluencingBounds(bounds);
        sceneBG.addChild(ambientLightNode);

        // Set up the directional lights
        Vector3f light1Direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        // left, down, backwards
        Vector3f light2Direction = new Vector3f(1.0f, -1.0f, 1.0f);
        // right, down, forwards

        DirectionalLight light1 = new DirectionalLight(white, light1Direction);
        light1.setInfluencingBounds(bounds);
        sceneBG.addChild(light1);

        DirectionalLight light2 = new DirectionalLight(white, light2Direction);
        light2.setInfluencingBounds(bounds);
        sceneBG.addChild(light2);
    }

    private void addBackground() {
        Background back = new Background();
        back.setApplicationBounds(bounds);
        back.setColor(0.17f, 0.65f, 0.92f); // sky colour
        sceneBG.addChild(back);
    }

    private void initUserControls(Vector3d targetVec) {
        ViewingPlatform vp = su.getViewingPlatform();

        // position viewpoint
        TransformGroup steerTG = vp.getViewPlatformTransform();
        Transform3D t3d = new Transform3D();
        steerTG.getTransform(t3d);
        t3d.setTranslation(new Vector3d(0, 1, Z_START));
        steerTG.setTransform(t3d);

        // set up keyboard controls
        KeyBehavior keyBeh = new KeyBehavior();
        keyBeh.setSchedulingBounds(bounds);
        vp.setViewPlatformBehavior(keyBeh);
    }

}
