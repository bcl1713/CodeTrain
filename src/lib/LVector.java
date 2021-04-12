/*
 * File:         LVector.java
 * Created Date: 2021-04-12
 * Author:       Brian Lucas
 * Purpose:      Create my own Vector class
 * -----
 * Last Modified: Mon Apr 12 2021
 * HISTORY:
 * Date        Comments
 * 2021-04-12  Initial Commit
 */


package lib;

public class LVector {
  
  private double x;
  private double y;
  private double z;
  
  public double getX() {
    return x;
  }
  public void setX(double x) {
    this.x = x;
  }
  public double getY() {
    return y;
  }
  public void setY(double y) {
    this.y = y;
  }
  public double getZ() {
    return z;
  }
  public void setZ(double z) {
    this.z = z;
  }
  
  public LVector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public LVector(double x, double y) {
    this(x, y, 0);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(x);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(y);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(z);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LVector other = (LVector) obj;
    if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
      return false;
    if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
      return false;
    return Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
  }

  public double getMagnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public void add(LVector other) {
    this.x += other.x; 
    this.y += other.y;
    this.z += other.z;
  }

  public void sub(LVector other) {
    this.x -= other.x;
    this.y -= other.y;
    this.z -= other.z;
  }
  
  public void scale(double scalar) {
    this.x *= scalar;
    this.y *= scalar;
    this.z *= scalar;
  }

  public void normalize() {
    double mag = this.getMagnitude();
    if (mag != 0) {
      this.scale(1/mag);
    }
  }

  public void setMagnitude(double mag) {
    this.normalize();
    this.scale(mag);
  }

  public LVector copy() {
    return new LVector(x, y, z);
  }

  public void limit(double limit){
    if (getMagnitude() > limit) {
      setMagnitude(limit);
    }
  }

  public static LVector sub(LVector v1, LVector v2) {
    LVector vector = v1.copy();
    LVector vector2 = v2.copy();
    vector.sub(vector2);
    return vector;
  }
}
