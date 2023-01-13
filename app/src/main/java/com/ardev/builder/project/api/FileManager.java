package com.ardev.builder.project.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.File;
import java.time.Instant;
import java.util.Optional;

public interface FileManager {

   
    boolean isOpened(@NonNull File file);

    @Nullable
    Instant getLastModified(@NonNull File file);

    void setLastModified(@NonNull File file, Instant instant);

    
    void openFileForSnapshot(@NonNull File file, String content);

   
    void setSnapshotContent(@NonNull File file, String content, FileListener listener);

   
    void setSnapshotContent(@NonNull File file, String content, boolean notify);

    default void setSnapshotContent(File file, String content) {
        setSnapshotContent(file, content, true);
    }

   
    void closeFileForSnapshot(@NonNull File file);

    void addSnapshotListener(FileListener listener);

    void removeSnapshotListener(FileListener listener);

    
    Optional<CharSequence> getFileContent(File file);

  
    void shutdown();

    void saveContents();
}
