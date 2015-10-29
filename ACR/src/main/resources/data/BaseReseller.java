package main.resources.data;

import main.java.model.Reseller;
import main.java.model.Supplier;

/**
 * Created by Andrii.Bakhtiozin on 07.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BaseReseller {
    private static final String resellerCode = "autotest";
    private static final String passWord = "1234";

    public static Reseller getReseller(Supplier supplier) {
        switch (supplier) {
            case UFS:
                return new Reseller(resellerCode, "ufs_b2b", passWord);
            case TF:
                return new Reseller(resellerCode, "tf_b2b", passWord);
            case ACP:
                return new Reseller(resellerCode, "acp_b2b", passWord);
            case IP:
                return new Reseller(resellerCode, "ip_b2b", passWord);
        }
        return null;
    }

    public static Reseller getReseller(String code) {
        if (code.equals("DM")) return new Reseller("autotestdm", "dm_b2b", "1234");
        if (code.equals("ADMIN")) return new Reseller(resellerCode, "admin", passWord);
        return new Reseller(resellerCode, code, passWord);
    }
}
