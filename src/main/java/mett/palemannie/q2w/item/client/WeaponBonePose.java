package mett.palemannie.q2w.item.client;

import software.bernie.geckolib.animation.state.BoneSnapshot;

/** Captures only the axes changed by procedural animation, preserving keyframed axes. */
public final class WeaponBonePose {
    private Float rotX;
    private Float rotY;
    private Float rotZ;
    private Float translateX;
    private Float translateY;
    private Float translateZ;
    private Float scaleX;
    private Float scaleY;
    private Float scaleZ;

    public void setRotX(float value) { this.rotX = value; }

    public void setRotY(float value) { this.rotY = value; }

    public void setRotZ(float value) { this.rotZ = value; }

    public void setTranslateX(float value) { this.translateX = value; }

    public void setTranslateY(float value) { this.translateY = value; }

    public void setTranslateZ(float value) { this.translateZ = value; }

    public void setScaleX(float value) { this.scaleX = value; }

    public void setScaleY(float value) { this.scaleY = value; }

    public void setScaleZ(float value) { this.scaleZ = value; }

    public void applyTo(BoneSnapshot target) {
        if (rotX != null) target.setRotX(rotX);
        if (rotY != null) target.setRotY(rotY);
        if (rotZ != null) target.setRotZ(rotZ);
        if (translateX != null) target.setTranslateX(translateX);
        if (translateY != null) target.setTranslateY(translateY);
        if (translateZ != null) target.setTranslateZ(translateZ);
        if (scaleX != null) target.setScaleX(scaleX);
        if (scaleY != null) target.setScaleY(scaleY);
        if (scaleZ != null) target.setScaleZ(scaleZ);
    }
}
