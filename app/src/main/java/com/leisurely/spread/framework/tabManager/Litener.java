package com.leisurely.spread.framework.tabManager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

public interface Litener {
    //当前看见的是第几个pager
    public void onResume();

    public void onSaveInstanceState(Bundle outState);

    public void onPause();

    public void onDestroy();

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    public boolean onKeyDown(int keyCode, KeyEvent event);

}
