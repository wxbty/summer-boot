package ink.zfei.boot;

import ink.zfei.summer.core.ImportSelector;


public class MyImportSelect implements ImportSelector {

    @Override
    public String[] selectImports(Class var1) {
        String[] arrs = new String[1];
        arrs[0] = "ink.zfei.boot.Popo";
        return arrs;
    }
}
