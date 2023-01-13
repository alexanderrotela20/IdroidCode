package com.ardev.builder.completion.xml;

import com.ardev.builder.project.Project;
import com.ardev.builder.project.api.Module;
import com.ardev.builder.completion.api.CompilerProvider;

public class XmlIndexProvider extends CompilerProvider<XmlRepository> {

    public static final String KEY = XmlIndexProvider.class.getSimpleName();

    private XmlRepository mRepository;

    @Override
    public XmlRepository get(Project project, Module module) {
        if (mRepository == null) {
            mRepository = new XmlRepository();
        }
        return mRepository;
    }

    public void clear() {
        mRepository = null;
    }
}
