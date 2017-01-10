/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.soccar.ui.drawable;

import nl.soccar.physics.WorldObject;

/**
 * A PhysicsDrawable is a Drawable, that keeps track of an object that get's controlled by the PhysicsEngine.
 * @author PTS34A
 * @param <M> Model in the library.
 * @param <P> Model in the physics.
 */
public abstract class PhysicsDrawable<M, P extends WorldObject> extends Drawable<M> {

    private final P physicsModel;

    /**
     * Initiates a new PhysicsDrawableFx
     * 
     * @param canvas The canvas on which this Drawable is placed.
     * @param model The model of this Drawable.
     * @param physicsModel The physics-model of this Drawable.
     */
    public PhysicsDrawable(GameCanvas canvas, M model, P physicsModel) {
        super(canvas, model);
        this.physicsModel = physicsModel;
    }
    
    /**
     * Gets the physics-model which this Drawable tracks.
     * 
     * @return The physics-model of this Drawable.
     */
    public final P getPhysicsModel() {
        return physicsModel;
    }
}
