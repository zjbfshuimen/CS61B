public class NBody {
    public static double readRadius(String s){
        In in = new In(s);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String s){
        Planet[] ptr = new Planet[5];
        In in = new In(s);
        in.readInt();
        in.readDouble();
        for(int k = 0; k < 5; k++){
            ptr[k] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }

        return ptr;
    }
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = NBody.readPlanets(filename);
        double radius = NBody.readRadius(filename);

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for(Planet p : planets){
            p.draw();
        }
        StdDraw.enableDoubleBuffering();
        for(double time = 0; time < T ; time += dt){
            double[] xForces = new double[5];
            double[] yForces = new double[5];

            for(int i = 0; i < 5; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for(int k = 0; k < 5 ; k++){
                planets[k].update(dt, xForces[k], yForces[k]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int j = 0; j < 5; j++){
                planets[j].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n",radius);
        for(int i = 0; i < planets.length; i ++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }

}
