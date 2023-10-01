public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static double G = 6.67E-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet rocinante){
		double dx, dy, d;
		dx = rocinante.xxPos - this.xxPos;
		dy = rocinante.yyPos - this.yyPos;
		d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}
	public double calcForceExertedBy(Planet x){
		double force;
		force = G * x.mass * this.mass / this.calcDistance(x) / this.calcDistance(x);
		return force;
	}
	public double calcForceExertedByX(Planet x){
		return (this.calcForceExertedBy(x) * (x.xxPos - this.xxPos) / this.calcDistance(x));
	}
	public double calcForceExertedByY(Planet x){
		return (this.calcForceExertedBy(x) * (x.yyPos - this.yyPos) / this.calcDistance(x));
	}
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double fx = 0;
		for(Planet x : allPlanets){
			if(!this.equals(x)){
				fx += this.calcForceExertedByX(x);
			}
		}
		return fx;
	}
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double fy = 0;
		for(Planet x : allPlanets){
			if(!this.equals(x)){
				fy += this.calcForceExertedByY(x);
			}
		}
		return fy;
	}
	public void update(double dt, double fx, double fy){
		double ax, ay;
		ax = fx/this.mass;
		ay = fy/this.mass;
		this.xxVel = this.xxVel + ax * dt;
		this.yyVel = this.yyVel + ay * dt;

		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}
	public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}
