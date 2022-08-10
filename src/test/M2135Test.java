package test;

import main.M2135;

import org.junit.Before;
import org.junit.Test;


public class M2135Test {
    // the results are compared to the MATLAB implementation of Recommendation ITU-R M.2135-1
    // the test is passed when the results for transmission loss are within 0.01 dB of difference
    // different location percentages, frequencies, distances, and environment categories are tested
    //     Rev   Date        Author                          Description
    //     ------------------------------------------------------------------------------
    //     v0    07JUN18     Ivica Stevanovic, OFCOM         Introduced tests for M2135


    TestUtil util;

    @Before
    public void setup() {

        util = new TestUtil(0.01);
    }

    @Test
    public void test1() {
        M2135 calculator = new M2135();
        double[] f = new double[5];
        double[] d = new double[5];
        int[] los = new int[2];
        double hbs, hms;
        double W, h;
        f[0] = 2.000000;
        f[1] = 3.000000;
        f[2] = 4.000000;
        f[3] = 5.000000;
        f[4] = 6.000000;
        d[0] = 10.000000;
        d[1] = 100.000000;
        d[2] = 1000.000000;
        d[3] = 2000.000000;
        d[4] = 5000.000000;
        los[0] = 1;
        los[1] = 2;
        hbs = 25.000000;
        hms = 1.500000;
        W = 20.000000;
        h = 20.000000;
        double[] expectedResult = new double[50];
        expectedResult[0] = 56.020600;
        expectedResult[1] = 58.651683;
        expectedResult[2] = 78.020600;
        expectedResult[3] = 97.738069;
        expectedResult[4] = 108.976798;
        expectedResult[5] = 136.824455;
        expectedResult[6] = 121.017997;
        expectedResult[7] = 148.590629;
        expectedResult[8] = 136.935598;
        expectedResult[9] = 164.144666;
        expectedResult[10] = 59.542425;
        expectedResult[11] = 62.173508;
        expectedResult[12] = 81.542425;
        expectedResult[13] = 101.259894;
        expectedResult[14] = 109.328980;
        expectedResult[15] = 140.346280;
        expectedResult[16] = 121.370180;
        expectedResult[17] = 152.112455;
        expectedResult[18] = 137.287780;
        expectedResult[19] = 167.666491;
        expectedResult[20] = 62.041200;
        expectedResult[21] = 64.672283;
        expectedResult[22] = 84.041200;
        expectedResult[23] = 103.758669;
        expectedResult[24] = 109.578858;
        expectedResult[25] = 142.845055;
        expectedResult[26] = 121.620057;
        expectedResult[27] = 154.611229;
        expectedResult[28] = 137.537658;
        expectedResult[29] = 170.165266;
        expectedResult[30] = 63.979400;
        expectedResult[31] = 66.610483;
        expectedResult[32] = 85.979400;
        expectedResult[33] = 105.696869;
        expectedResult[34] = 109.772678;
        expectedResult[35] = 144.783255;
        expectedResult[36] = 121.813877;
        expectedResult[37] = 156.549430;
        expectedResult[38] = 137.731478;
        expectedResult[39] = 172.103466;
        expectedResult[40] = 65.563025;
        expectedResult[41] = 68.194108;
        expectedResult[42] = 87.563025;
        expectedResult[43] = 107.280494;
        expectedResult[44] = 109.931040;
        expectedResult[45] = 146.366880;
        expectedResult[46] = 121.972240;
        expectedResult[47] = 158.133055;
        expectedResult[48] = 137.889840;
        expectedResult[49] = 173.687091;
        int count = 0;
        for (int fi = 0; fi < 5; fi++) {
            for (int di = 0; di < 5; di++) {
                for (int li = 0; li < 2; li++) {
                    //double[] out = calculator.m2135_UMa(f[fi], d[di], hbs, hms, W, h, los[li]);
                    //double result = out[0];
                    double result = calculator.tl_m2135(f[fi], d[di], hbs, hms, W, h, M2135.ClutterEnvironment.URBAN, los[li], false);
                    util.assertDoubleEquals(expectedResult[count], result);
                    count = count + 1;
                }
            }
        }
    }
    @Test
    public void test2() {
        M2135 calculator = new M2135();
        double[] f = new double[5];
        double[] d = new double[5];
        int[] los = new int[2];
        double hbs, hms;
        double W, h;
        f[0] = 2.000000;
        f[1] = 3.000000;
        f[2] = 4.000000;
        f[3] = 5.000000;
        f[4] = 6.000000;
        d[0] = 10.000000;
        d[1] = 100.000000;
        d[2] = 1000.000000;
        d[3] = 2000.000000;
        d[4] = 5000.000000;
        los[0] = 1;
        los[1] = 2;
        hbs = 25.000000;
        hms = 1.500000;
        W = 20.000000;
        h = 10.000000;
        double[] expectedResult = new double[50];
        expectedResult[0] = 57.747642;
        expectedResult[1] = 53.911217;
        expectedResult[2] = 79.502064;
        expectedResult[3] = 92.997602;
        expectedResult[4] = 102.876486;
        expectedResult[5] = 132.083988;
        expectedResult[6] = 112.445657;
        expectedResult[7] = 143.850163;
        expectedResult[8] = 128.363257;
        expectedResult[9] = 159.404200;
        expectedResult[10] = 61.269467;
        expectedResult[11] = 57.433042;
        expectedResult[12] = 83.023889;
        expectedResult[13] = 96.519428;
        expectedResult[14] = 106.398312;
        expectedResult[15] = 135.605814;
        expectedResult[16] = 114.892860;
        expectedResult[17] = 147.371988;
        expectedResult[18] = 130.211296;
        expectedResult[19] = 162.926025;
        expectedResult[20] = 63.768242;
        expectedResult[21] = 59.931816;
        expectedResult[22] = 85.522664;
        expectedResult[23] = 99.018202;
        expectedResult[24] = 108.897086;
        expectedResult[25] = 138.104588;
        expectedResult[26] = 117.391635;
        expectedResult[27] = 149.870763;
        expectedResult[28] = 131.978798;
        expectedResult[29] = 165.424800;
        expectedResult[30] = 65.706442;
        expectedResult[31] = 61.870017;
        expectedResult[32] = 87.460864;
        expectedResult[33] = 100.956403;
        expectedResult[34] = 110.835287;
        expectedResult[35] = 140.042789;
        expectedResult[36] = 119.329835;
        expectedResult[37] = 151.808963;
        expectedResult[38] = 133.702172;
        expectedResult[39] = 167.363000;
        expectedResult[40] = 67.290067;
        expectedResult[41] = 63.453642;
        expectedResult[42] = 89.044489;
        expectedResult[43] = 102.540028;
        expectedResult[44] = 112.418912;
        expectedResult[45] = 141.626414;
        expectedResult[46] = 120.913460;
        expectedResult[47] = 153.392588;
        expectedResult[48] = 135.397633;
        expectedResult[49] = 168.946625;
        int count = 0;
        for (int fi = 0; fi < 5; fi++) {
            for (int di = 0; di < 5; di++) {
                for (int li = 0; li < 2; li++) {
                    //double[] out = calculator.m2135_SMa(f[fi], d[di], hbs, hms, W, h, los[li]);
                    //double result = out[0];
                    double result = calculator.tl_m2135(f[fi], d[di], hbs, hms, W, h, M2135.ClutterEnvironment.SUBURBAN, los[li], false);
                    util.assertDoubleEquals(expectedResult[count], result);
                    count = count + 1;
                }
            }
        }
    }
    @Test
    public void test3() {
        M2135 calculator = new M2135();
        double[] f = new double[5];
        double[] d = new double[5];
        int[] los = new int[2];
        double hbs, hms;
        double W, h;
        f[0] = 2.000000;
        f[1] = 3.000000;
        f[2] = 4.000000;
        f[3] = 5.000000;
        f[4] = 6.000000;
        d[0] = 10.000000;
        d[1] = 100.000000;
        d[2] = 1000.000000;
        d[3] = 2000.000000;
        d[4] = 5000.000000;
        los[0] = 1;
        los[1] = 2;
        hbs = 35.000000;
        hms = 1.500000;
        W = 20.000000;
        h = 5.000000;
        double[] expectedResult = new double[50];
        expectedResult[0] = 58.253325;
        expectedResult[1] = 48.287358;
        expectedResult[2] = 78.857054;
        expectedResult[3] = 86.920747;
        expectedResult[4] = 100.593113;
        expectedResult[5] = 125.554136;
        expectedResult[6] = 108.155520;
        expectedResult[7] = 137.183945;
        expectedResult[8] = 123.546811;
        expectedResult[9] = 152.557716;
        expectedResult[10] = 61.775150;
        expectedResult[11] = 51.809183;
        expectedResult[12] = 82.378879;
        expectedResult[13] = 90.442572;
        expectedResult[14] = 104.114939;
        expectedResult[15] = 129.075961;
        expectedResult[16] = 111.677345;
        expectedResult[17] = 140.705770;
        expectedResult[18] = 125.168083;
        expectedResult[19] = 156.079541;
        expectedResult[20] = 64.273925;
        expectedResult[21] = 54.307957;
        expectedResult[22] = 84.877653;
        expectedResult[23] = 92.941347;
        expectedResult[24] = 106.613713;
        expectedResult[25] = 131.574736;
        expectedResult[26] = 114.176120;
        expectedResult[27] = 143.204545;
        expectedResult[28] = 126.764908;
        expectedResult[29] = 158.578316;
        expectedResult[30] = 66.212125;
        expectedResult[31] = 56.246158;
        expectedResult[32] = 86.815854;
        expectedResult[33] = 94.879547;
        expectedResult[34] = 108.551914;
        expectedResult[35] = 133.512936;
        expectedResult[36] = 116.114320;
        expectedResult[37] = 145.142745;
        expectedResult[38] = 128.457121;
        expectedResult[39] = 160.516516;
        expectedResult[40] = 67.795750;
        expectedResult[41] = 57.829783;
        expectedResult[42] = 88.399479;
        expectedResult[43] = 96.463172;
        expectedResult[44] = 110.135539;
        expectedResult[45] = 135.096561;
        expectedResult[46] = 117.697945;
        expectedResult[47] = 146.726370;
        expectedResult[48] = 130.040746;
        expectedResult[49] = 162.100141;
        int count = 0;
        for (int fi = 0; fi < 5; fi++) {
            for (int di = 0; di < 5; di++) {
                for (int li = 0; li < 2; li++) {
                    //double[] out = calculator.m2135_SMa(f[fi], d[di], hbs, hms, W, h, los[li]);
                    //double result = out[0];
                    double result = calculator.tl_m2135(f[fi], d[di], hbs, hms, W, h, M2135.ClutterEnvironment.RURAL, los[li], false);
                    util.assertDoubleEquals(expectedResult[count], result);
                    count = count + 1;
                }
            }
        }
    }

}