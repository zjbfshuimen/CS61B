import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double raster_ul_lon = MapServer.ROOT_ULLON;
    private double raster_ul_lat = MapServer.ROOT_ULLAT;
    private double raster_lr_lon = MapServer.ROOT_LRLON;
    private double raster_lr_lat = MapServer.ROOT_LRLAT;
    private String[][] render_grid;
    private int depth;
    private boolean query_success = true;

    private double[] d;

    public Rasterer() {

        d = new double[8];
        double d0 = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / MapServer.TILE_SIZE;
        for (int i = 0; i < 8; i++) {
            d[i] = d0 / (Math.pow(2, i));
        }
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);

        if (!isValid(params)) {
            query_success = false;
        }
        int layer = layerDecide(params.get("ullon"), params.get("lrlon"), params.get("w"));
        int[][] result = imagDecide(layer, params.get("ullon"), params.get("lrlon"), params.get("ullat"), params.get("lrlat"));
        setRenderGrid(layer, result);

        depth = layer;

        Map<String, Object> results = new HashMap<>();
        results.put("query_success", query_success);
        results.put("depth", layer);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("render_grid", render_grid);

        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
        //                   + "your browser.");
        //System.out.println("the d u use " + d);
        return results;
    }
    private int layerDecide(double log1, double log2, double w) {
        //System.out.println("the log1 is " + log1 + " log 2 is " + log2 + " w " + w);
        /*
        if (log1 < MapServer.ROOT_ULLON ) {
            log1 = MapServer.ROOT_ULLON;
        }
        if (log2 > MapServer.ROOT_LRLON) {
            log2 = MapServer.ROOT_LRLON;
        }
         */
        double logDPP = Math.abs(log1 - log2) / w;
        //System.out.println("logDPP is " + logDPP);
        int i;
        for (i = 0; i < 8; i++) {
            if (d[i] <= logDPP) {
                break;
            }
        }
        if (i == 8) {
            i--;
        }
        return i;
    }

    /**
     *
     * @return 2d array
     * array[0][0] is the starter x(included) array[0][1] is the end x(included)
     * array[1][0] is the starter y
     *
     */
    private int[][] imagDecide(int layer,double imagUllon, double imagLrlon, double imagUllat, double imagLrlat) {
        double deltaLog = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / Math.pow(2, layer);
        double deltalat = Math.abs(MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, layer);

        double ullon = MapServer.ROOT_ULLON;
        double lrlon = MapServer.ROOT_LRLON;
        double ullat = MapServer.ROOT_ULLAT;
        double lrlat = MapServer.ROOT_LRLAT;

        int[][] result = new int[2][2];

        int lon1 = 0, lon2 = 0;
        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (ullon + lon1 * deltaLog <= imagUllon) {
                lon1++;
            }
            if (ullon + lon2 * deltaLog < imagLrlon) {
                lon2++;
            }
        }
        if (lon1 != 0) {
            lon1--;
        }
        if (lon2 != 0) {
            lon2--;
        }

        int lat1 = 0, lat2 = 0;
        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (ullat - lat1 * deltalat >= imagUllat) {
                lat1++;
            }
            if (ullat - lat2 * deltalat >= imagLrlat) {
                lat2++;
            }
        }
        double upper = ullat - lat1 * deltalat, low = ullat - lat2 * deltalat;
        if (lat1 != 0) {
            lat1--;
        }
        if (lat2 != 0) {
            lat2--;
        }

        result[0][0] = lon1;
        result[0][1] = lon2;
        result[1][0] = lat1;
        result[1][1] = lat2;
        /*
        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (ullon + i * deltaLog > imagUllon) {
                i--;
                result[0][0] = i;
                raster_ul_lon += i * deltaLog;
                break;
            }
        }
        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (lrlon - i * deltaLog < imagLrlon) {
                result[0][1] = (int)Math.pow(2, layer) - i;
                raster_lr_lon -= (i - 1) *  deltaLog;
                break;
            }
        }

        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (ullat - i * deltalat < imagUllat) {
                i--;
                result[1][0] = i;
                raster_ul_lat -= i * deltalat;
                break;
            }
        }

        for (int i = 0; i < Math.pow(2, layer); i++) {
            if (lrlat + i * deltalat > imagLrlat) {
                result[1][1] = (int)Math.pow(2, layer) - i;
                raster_lr_lat += (i - 1) * deltalat;
                break;
            }
        }
         */

        return result;
    }



    private void setRenderGrid(int layer, int[][] result) {
        int lenx = result[0][1] - result[0][0] + 1;
        int leny = result[1][1] - result[1][0] + 1;
        render_grid = new String[leny][lenx];
        int index = 0, indexy = 0;

        /*
        for (int i = result[0][0]; i <= result[0][1]; i++) {
            for (int j = result[1][0]; j <= result[1][1]; j++) {
                render_grid[index][indexy] = "d" + layer +"_x" + i + "_y" + j + ".png";
                indexy++;
            }
            indexy = 0;
            index++;
        }
        */

        for (int j = result[1][0]; j <= result[1][1]; j++) {
            for (int i = result[0][0]; i <= result[0][1]; i++) {
                render_grid[indexy][index] = "d" + layer + "_x" + i + "_y" + j + ".png";
                index++;
            }
            index = 0;
            indexy++;
        }

    }

    private boolean isValid(Map<String, Double> params) {
        double ullon1 = params.get("ullon");
        double ullat1 = params.get("ullat");
        double lrlon1 = params.get("lrlon");
        double lrlat1 = params.get("lrlat");

        if (params.get("ullon") >= MapServer.ROOT_LRLON || params.get("ullat") <= MapServer.ROOT_LRLAT
         || params.get("lrlon") <= MapServer.ROOT_ULLON && params.get("lrlat") > MapServer.ROOT_ULLAT
         || params.get("ullon") >= params.get("lrlon")  || params.get("ullat") <= params.get("lrlat")) {
            return false;
        }
        return true;
    }




    /*
    public static void main(String[] args) {
        Rasterer raster = new Rasterer();
        Map<String, Double> params = new HashMap<>();
        params.put("lrlon", -122.2104604264636);
        params.put("ullon", -122.30410170759153);
        params.put("w", 1091.0);
        params.put("h", 566.0);
        params.put("ullat", 37.870213571328854);
        params.put("lrlat", 37.8318576119893);
        Map<String, Object> re = raster.getMapRaster(params);
        System.out.println(re);
    }

     */




}
