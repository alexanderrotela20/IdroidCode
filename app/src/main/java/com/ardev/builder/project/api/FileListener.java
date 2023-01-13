package com.ardev.builder.project.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.File;

public interface FileListener {

    void onSnapshotChanged(File file, CharSequence contents);
}