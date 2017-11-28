package com.hezare.mmd.Utils;

import com.hezare.mmd.R;

/**
 * Created by amirhododi on 7/29/2017.
 */


public class Utils {


    public static int WichTabel3(int roz, int zang) {
        if (roz == 0 && zang == 0) {

            return R.id.zangaval_col1_shanbe_3;

        } else if (roz == 0 && zang == 1) {
            return R.id.zangdovom_col1_shanbe_3;

        } else if (roz == 0 && zang == 2) {
            return R.id.zangsevom_col1_shanbe_3;

        } else if (roz == 0 && zang == 3) {
            return R.id.zangcharom_col1_shanbe_3;

        } else if (roz == 0 && zang == 4) {
            return R.id.zangpanjom_col1_shanbe_3;

        } else if (roz == 1 && zang == 0) {
            return R.id.zangaval_col2_yeshanbe_3;

        } else if (roz == 1 && zang == 1) {
            return R.id.zangdovom_col2_yeshanbe_3;

        } else if (roz == 1 && zang == 2) {
            return R.id.zangsevom_col2_yeshanbe_3;

        } else if (roz == 1 && zang == 3) {
            return R.id.zangcharom_col2_yeshanbe_3;

        } else if (roz == 1 && zang == 4) {
            return R.id.zangpanjom_col2_yeshanbe_3;

        } else if (roz == 2 && zang == 0) {
            return R.id.zangaval_col3_dushanbe_3;

        } else if (roz == 2 && zang == 1) {
            return R.id.zangdovom_col3_dushanbe_3;

        } else if (roz == 2 && zang == 2) {
            return R.id.zangsevom_col3_dushanbe_3;

        } else if (roz == 2 && zang == 3) {
            return R.id.zangcharom_col3_dushanbe_3;

        } else if (roz == 2 && zang == 4) {
            return R.id.zangpanjom_col3_dushanbe_3;

        } else if (roz == 3 && zang == 0) {
            return R.id.zangaval_col4_seshanbe_3;

        } else if (roz == 3 && zang == 1) {
            return R.id.zangdovom_col4_seshanbe_3;

        } else if (roz == 3 && zang == 2) {
            return R.id.zangsevom_col4_seshanbe_3;

        } else if (roz == 3 && zang == 3) {
            return R.id.zangcharom_col4_seshanbe_3;

        } else if (roz == 3 && zang == 4) {
            return R.id.zangpanjom_col4_seshanbe_3;

        } else if (roz == 4 && zang == 0) {
            return R.id.zangaval_col5_charshanbe_3;

        } else if (roz == 4 && zang == 1) {
            return R.id.zangdovom_col5_charshanbe_3;

        } else if (roz == 4 && zang == 2) {
            return R.id.zangsevom_col5_charshanbe_3;

        } else if (roz == 4 && zang == 3) {
            return R.id.zangcharom_col5_charshanbe_3;

        } else if (roz == 4 && zang == 4) {
            return R.id.zangpanjom_col5_charshanbe_3;

        } else if (roz == 5 && zang == 0) {
            return R.id.zangaval_col6_panjshanbe_3;

        } else if (roz == 5 && zang == 1) {
            return R.id.zangdovom_col6_panjshanbe_3;

        } else if (roz == 5 && zang == 2) {
            return R.id.zangsevom_col6_panjshanbe_3;

        } else if (roz == 5 && zang == 3) {
            return R.id.zangcharom_col6_panjshanbe_3;

        } else if (roz == 5 && zang == 4) {
            return R.id.zangpanjom_col6_panjshanbe_3;

        } else {
            return R.id.zangaval_col1_shanbe_3;
        }

    }


    public static String WichTabelColorDef3(int roz) {
        if (roz == 1) {
            return "#e0e0e0";

        } else if (roz == 2) {
            return "#ffffff";
        } else if (roz == 3) {
            return "#e0e0e0";

        } else if (roz == 4) {
            return "#ffffff";
        } else if (roz == 5) {
            return "#e0e0e0";

        } else {
            return "#ffffff";

        }

    }

}
