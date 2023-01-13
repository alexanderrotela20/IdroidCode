package com.ardev.builder.project.api;

import java.io.File;
import java.io.IOException;

public interface Module {

  

    FileManager getFileManager();

    File getRootFile();

    default String getName() {
        return getRootFile().getName();
    }

    /**
     * Start parsing the project contents such as manifest data, project settings, etc.
     *
     * Implementations may throw an IOException if something went wrong during parsing
     */
    void open() throws IOException;

    /**
     * Remove all the indexed files
     */
    void clear();

    void index();

    /**
     * @return The directory that this project can use to compile files
     */
    File getBuildDirectory();
    
    File getCacheDirectory();
}
