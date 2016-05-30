package com.vikingpianomovers.vikingpianomoversmobileapp;

import java.io.File;

/**
 * Created by Tom on 5/24/2015.
 */

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
