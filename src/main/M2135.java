package main;

import java.util.Random;



// Winner 2

public class M2135{

    // Class implementation of ITU-R M2135 propagation model
    //
    // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    // EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    // MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
    // IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
    // OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
    // ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
    // OTHER DEALINGS IN THE SOFTWARE.
    //
    // You may improve, modify, and create derivative works of the software or
    // any portion of the software, and you may copy and distribute such
    // modifications or works. Modified works should carry a notice stating
    // that you changed the software and should note the date and nature of
    // any such change.
    //
    // Please provide appropriate acknowledgments in any copies or
    // derivative works of this software.


    public enum ClutterEnvironment {

        NONE("No clutter"),
        WATER("Water/Sea"),
        URBAN("Urban"),
        URBAN_MICRO("Urban Micro Cell"),
        SUBURBAN("Suburban"),
        DENSE_SUBURBAN("Dense Suburban"),
        RURAL("Rural"),
        DENSE_URBAN("Dense Urban"),
        HIGH_RISE_URBAN("High-rise Urban"),
        RESIDENTIAL("Residential"),
        INDUSTRIAL("Industrial zone"),
        USER_SPECIFIED("User specified");
        private String name;
        ClutterEnvironment(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public double getGaussianDistribution(double mu, double sigma){

        Random x = new Random();

        return x.nextGaussian()*sigma + mu;
    }

    public double getUniformDistribution(double x1, double x2){

        Random x = new Random();

        return x1 + (x2-x1)*x.nextDouble();
    }


    public double tl_m2135(double f, double d, double h1, double h2, double W, double h, ClutterEnvironment env, int lostype,  boolean variations) {

        double L = 0.0;
        double StdDev = 0.0;


        double hbs = h1;
        double hms = h2;

        if (h1 < h2) {
            hbs = h2;
            hms = h1;
        }

        double fmin = 2;
        double fmax = 6;


        double dmin = 10;
        double dmax = 5000;

        double hbsmin = 10.0;
        double hmsmin = 1.0;

        double hbsmax = 150;
        double hmsmax = 10.0;

        if (env == ClutterEnvironment.RURAL) {
            fmin = 0.450;
        }

        if (env == ClutterEnvironment.RURAL && (lostype == 1)) {
            dmax = 10000;
        }


        if (f < fmin || f > fmax) {
            throw new RuntimeException("The chosen model is valid for frequencies in the range [" + Double.toString(fmin) + ", " + Double.toString(fmax) + "] GHz");
        }


        //if (d < dmin || d > dmax) {
        //    throw new RuntimeException("The chosen model is valid for distances in the range [" + Double.toString(dmin) + ", " + Double.toString(dmax) + "] m");
        //}


        if (hbs < hbsmin || hbs > hbsmax) {

            throw new RuntimeException("The chosen model is valid for base station heights in the range [" + Double.toString(hbsmin) + ", " + Double.toString(hbsmax) + "] m");

        }


        if (hms <= hmsmin || hms > hmsmax) {
            throw new RuntimeException("The chosen model is valid for mobile station heights in the range [" + Double.toString(hmsmin) + ", " + Double.toString(hmsmax) + "] m");

        }

        if (W < 5 || W > 50) {
            throw new RuntimeException("The model is valid for street widths in the range [5, 50] m");
        }


        if (h < 5 || h > 50) {
            throw new RuntimeException("The model is valid for average building heights in the range [5, 50] m");
        }


        double prob = 1; // probability that the path is LOS

        switch (lostype) {
            case 2:
                prob = 0;
                break;
            case 3:
                switch (env) {
                    case URBAN:
                        prob = Math.min(18.0/d,1.0)*(1-Math.exp(-d/63.0)) + Math.exp(-d/63.0);
                        break;
                    case SUBURBAN:
                        if (d > 10){
                            prob = Math.exp(-(d-10)/200);
                        }
                        break;
                    case RURAL:
                        if (d > 10) {
                            prob = Math.exp(-(d-10)/1000.0);
                        }
                }
        }

        double[] out;

        switch (lostype) {
            case 1:
                switch (env) {
                    case URBAN:
                        out = m2135_UMa(f, d, hbs, hms, W, h, true);
                        L = out[0];
                        StdDev = out[1];

                        if (variations) {
                            double std = getGaussianDistribution(0, StdDev);

                            L = L + std;
                        }
                        break;
                    default: // SMa or RMa
                        out = m2135_SMa(f, d, hbs, hms, W, h, true);
                        L = out[0];
                        StdDev = out[1];

                        if (variations) {
                            double std = getGaussianDistribution(0, StdDev);

                            L = L + std;
                        }
                }
                break;
            case 2:
                switch (env) {
                    case URBAN:
                        out = m2135_UMa(f, d, hbs, hms, W, h, false);
                        L = out[0];
                        StdDev = out[1];

                        if (variations) {
                            double std = getGaussianDistribution(0, StdDev);

                            L = L + std;
                        }
                        break;
                    default: // SMa or RMa
                        out = m2135_SMa(f, d, hbs, hms, W, h, false);
                        L = out[0];
                        StdDev = out[1];

                        if (variations) {
                            double std = getGaussianDistribution(0, StdDev);

                            L = L + std;
                        }
                }
                break;
            case 3:
                switch (env) {
                    case URBAN:
                        out = m2135_UMa(f, d, hbs, hms, W, h, true);
                        double L1 = out[0];
                        double StdDev1 = out[1];

                        out = m2135_UMa(f, d, hbs, hms, W, h, false);
                        double L2 = out[0];
                        double StdDev2 = out[1];

                        if (variations) {
                            double std1 = getGaussianDistribution(0, StdDev1);
                            double std2 = getGaussianDistribution(0, StdDev2);
                            L1 = L1 + std1;
                            L2 = L2 + std2;
                        }

                        //L = prob*L1 + (1-prob)*L2;
                        double p_trial = getUniformDistribution(0.0, 1.0);
                        if (p_trial < prob) {
                            L = L1; //LOS
                        }else {
                            L = L2; //NLOS
                        }

                        break;
                    default: //SMa or RMa
                        out = m2135_SMa(f, d, hbs, hms, W, h, true);
                        L1 = out[0];
                        StdDev1 = out[1];

                        out = m2135_SMa(f, d, hbs, hms, W, h, false);
                        L2 = out[0];
                        StdDev2 = out[1];

                        if (variations) {
                            double std1 = getGaussianDistribution(0, StdDev1);
                            double std2 = getGaussianDistribution(0, StdDev2);
                            L1 = L1 + std1;
                            L2 = L2 + std2;
                        }

                        //L = prob*L1 + (1-prob)*L2;
                        p_trial = getUniformDistribution(0.0, 1.0);
                        if (p_trial < prob) {
                            L = L1; //LOS
                        }else {
                            L = L2; //NLOS
                        }
                }
        }

        return L;
    }


    public double[] m2135_UMa(double f, double d, double hbs, double hms, double W, double h, boolean los) {
        //    m2135_UMa computes path loss for urban macro cell
        //   This function computes path loss according to ITU-R M.2135-1 model for
        //   urban macro cell (UMa)
        // Input parameters:
        //     f       -   Frequency (GHz)
        //     d       -   Tx-Rx distance (m) - range (10, 5000) m
        //     hbs     -   base station height (m) - typical 25 m - range (10, 150) m
        //     hms     -   mobile station height (m) - typical 1.5 m - range (1, 10) m
        //     W       -   street width - typical 20 m - range (5, 50) m
        //     h       -   avg. building height - typical 20 m - range (5, 50) m
        //     los     -   flag: true = Line of Sight, false = Non Line of Sight
        // Output parameters:
        //     out     -   an array of output values
        //                 out(1) - path loss
        //                 out(2) - standard deviation


        // Numbers refer to Report ITU-R M.2135-1

        //     Rev   Date        Author                          Description
        //     -------------------------------------------------------------------------------
        //     v0    06JUN18     Ivica Stevanovic, OFCOM         Initial version
        //     v1    29OCT18     Max Friedrich, BNetzA           Bug fixed: "hbs->hms" in last term of NLOS

        // check the parameter values and issue warnings

        double sigma = 4.0;
        double L = 0.0;

        if (hbs <= 0 || hms <= 0) {
            throw new RuntimeException("Antenna heights must be larger than 0 m.");
        }

        if (los) {

            // make sure antenna heights are larger than 1 m
            if (hbs <= 1 || hms <= 1) {
                throw new RuntimeException("Antenna heights for ITU-R M.2135-1 UMa must be larger than 1 m.");
            }
            // compute the effective antenna heights
            double hbs1 = hbs - 1.0;
            double hms1 = hms - 1.0;
            // compute the break point

            double dbp = 4 * (hbs1) * (hms1) * f * 10.0 / 3.0; // computed according to the note 1) in Table A1-2

            if (d < dbp) {
                sigma = 4;
                double A = 22;
                double B = 28;
                double C = 20;

                L = A * Math.log10(d) + B + C * Math.log10(f);
            } else {
                sigma = 4;

                L = 40.0 * Math.log10(d) + 7.8 - 18.0 * Math.log10(hbs1) - 18.0 * Math.log10(hms1) + 2.0 * Math.log10(f);

            }

        } else { // NLOS

            sigma = 6;
            // Outdated version using hbs instead of hms in the last term:
            // L = 161.04 - 7.1 * Math.log10(W) + 7.5 * Math.log10(h) - (24.37 - 3.7 * Math.pow(h / hbs, 2.0)) * Math.log10(hbs) +
            //         (43.42 - 3.1 * Math.log10(hbs)) * (Math.log10(d) - 3) + 20 * Math.log10(f) - (3.2 * Math.pow(Math.log10(11.75 * hbs), 2.0) - 4.97);
            // Version amended by BNetzA according ITU-R M.2135-1 description using hms instead of hbs in the last term:
            L = 161.04 - 7.1 * Math.log10(W) + 7.5 * Math.log10(h) - (24.37 - 3.7 * Math.pow(h / hbs, 2.0)) * Math.log10(hbs) +
                    (43.42 - 3.1 * Math.log10(hbs)) * (Math.log10(d) - 3) + 20 * Math.log10(f) - (3.2 * Math.pow(Math.log10(11.75 * hms), 2.0) - 4.97);

        }

        double[] out = new double[2];

        out[0] = L;
        out[1] = sigma;

        return out;
    }

    public double[] m2135_SMa( double f, double d, double hbs, double hms, double W, double h, boolean los) {
        //    m2135_SMa computes path loss for suburban macro cell
        //   This function computes path loss according to ITU-R M.2135-1 model for
        //   suburban macro cell (SMa)
        // Input parameters:
        //     f       -   Frequency (GHz)
        //     d       -   Tx-Rx distance (m) - range (10, 5000) m
        //     hbs     -   base station height (m) - typical 25 m - range (10, 150) m
        //     hms     -   mobile station height (m) - typical 1.5 m - range (1, 10) m
        //     W       -   street width - typical 20 m - range (5, 50) m
        //     h       -   avg. building height - typical 20 m - range (5, 50) m
        //     los     -   flag: true = Line of Sight, false = Non Line of Sight
        // Output parameters:
        //     out     -   an array of output values
        //                 out(1) - path loss
        //                 out(2) - standard deviation


        // Numbers refer to Report ITU-R M.2135-1

        //     Rev   Date        Author                          Description
        //     -------------------------------------------------------------------------------
        //     v0    06JUN18     Ivica Stevanovic, OFCOM         Initial version
        //     v1    29OCT18     Max Friedrich, BNetzA           Bug fixed: new dbp factor "2*Math.PI"

        // check the parameter values and issue warnings

        double sigma = 4.0;
        double L = 0.0;

        if (hbs <= 0 || hms <= 0) {
            throw new RuntimeException("Antenna heights must be larger than 0 m.");
        }

        if (los) {

            // compute the break point

            //outdated version using factor "4" instead of "2*Math.PI":
            //double dbp = 4 * (hbs) * (hms) * f * 10.0 / 3.0; // computed according to the note 4) in Table A1-2
            // Version amended by BNetzA according ITU-R M.2135-1 description using factor "2*Math.PI" instead of "4" :
            double dbp = 2 * Math.PI * (hbs) * (hms) * f * 10.0 / 3.0; // computed according to the note 4) in Table A1-2

            if (d < dbp) {

                sigma = 4;

                L = 20 * Math.log10(40 * Math.PI * d * f / 3.0) + Math.min(0.03 * Math.pow(h, 1.72), 10) * Math.log10(d) -
                        Math.min(0.044 * Math.pow(h, 1.72), 14.77) + 0.002 * d * Math.log10(h);
            } else {

                sigma = 6;

                L = 20 * Math.log10(40 * Math.PI * dbp * f / 3.0) + Math.min(0.03 * Math.pow(h, 1.72), 10) * Math.log10(dbp) -
                        Math.min(0.044 * Math.pow(h, 1.72), 14.77) + 0.002 * dbp * Math.log10(h) + 40 * Math.log10(d / dbp);

            }


        } else { // NLOS

            sigma = 8;

            L = 161.04 - 7.1*Math.log10(W) + 7.5*Math.log10(h)
                    -(24.37 - 3.7*Math.pow(h/hbs, 2.0))*Math.log10(hbs)
                    +(43.42 - 3.1*Math.log10(hbs))*(Math.log10(d)-3)
                    + 20*Math.log10(f) - (3.2*Math.pow(Math.log10(11.75*hms),2.0) - 4.97);

        }

        double[] out = new double[2];

        out[0] = L;
        out[1] = sigma;

        return out;
    }
}
