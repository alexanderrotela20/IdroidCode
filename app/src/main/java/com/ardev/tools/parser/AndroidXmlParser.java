package com.ardev.tools.parser;
/**
 * Copyright (C) 2020 Coyamo
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ardev.tools.parser.proxy.ProxyAttributeSet;
import com.ardev.tools.parser.proxy.ProxyResources;
import com.ardev.tools.parser.proxy.ViewCreator;
import com.ardev.tools.parser.proxy.MessageArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;



public class AndroidXmlParser {
    public static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private XmlPullParser parser;
    private OnParseListener listener;
    private Context context;
  
    private Stack<View> allViewStack = new Stack<>();
   
    private Stack<ViewGroup> viewGroupStack = new Stack<>();
    private MessageArray debug = MessageArray.getInstanse();

    private AndroidXmlParser(Context context, ViewGroup container) {
        this.context = context;
        if(!viewGroupStack.isEmpty()) viewGroupStack.clear();
        if(!allViewStack.isEmpty()) allViewStack.clear();
        
        viewGroupStack.push(container);
        allViewStack.push(container);
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
        } catch (Exception e) {
            
        }
    }

    public static AndroidXmlParser with(ViewGroup container) {
        return new AndroidXmlParser(container.getContext(), container);
    }


    public AndroidXmlParser parse(String xml) {
        StringReader sr = new StringReader(xml);
        parse(sr);
        return this;
    }

    public AndroidXmlParser parse(Reader reader) {
        if (listener != null) listener.onStart();
        try {
            parser.setInput(reader);
            parse();
            reader.close();
        } catch (Exception e) {
           
        } finally {
            if (listener != null) listener.onFinish();
        }
        return this;
    }

    public AndroidXmlParser parse(File path) {
        try {
            FileReader fileReader = new FileReader(path);
            parse(fileReader);
        } catch (Exception e) {
            
        }
        return this;
    }

    private void parse() {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.TEXT:
                        if (!parser.getText().trim().isEmpty()) {
                            
                        }
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        View view;

                        
                        String attr = parser.getAttributeValue(null, "style");
                        if (attr != null) {
                            int attrInt = ProxyResources.getInstance().getAttr(attr);
                            view = ViewCreator.create(tagName, context, attrInt);
                        } else {
                            view = ViewCreator.create(tagName, context);
                        }
                        
                        ViewGroup viewGroup = viewGroupStack.peek();
                       
                        View lastView = allViewStack.peek();

                        ViewGroup viewGroup2 = null;
                        if (view instanceof ViewGroup) {
                          
                            viewGroup2 = viewGroupStack.push((ViewGroup) view);
                            if (listener != null)
                                listener.onJoin(viewGroup2, new ReadOnlyParser(parser));
                        }

                       
                        if (lastView == viewGroup) {
                            viewGroup.addView(view);
                            if (listener != null && viewGroup2 != view)
                                listener.onAddChildView(view, new ReadOnlyParser(parser));
                        } else {
                          
                            
                        }


                        allViewStack.push(view);

                        
                        ProxyAttributeSet attrs = new ProxyAttributeSet(parser, context);
                        attrs.setTo(view);

                        break;
                    case XmlPullParser.END_TAG:
                        View v = allViewStack.pop();
                        if (v instanceof ViewGroup) {
                            ViewGroup viewGroup1 = viewGroupStack.pop();
                            if (listener != null)
                                listener.onRevert(viewGroup1, new ReadOnlyParser(parser));
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            
        }


    }

    public AndroidXmlParser setOnParseListener(OnParseListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnParseListener {
        void onAddChildView(View v, ReadOnlyParser parser);

        void onJoin(ViewGroup viewGroup, ReadOnlyParser parser);

        void onRevert(ViewGroup viewGroup, ReadOnlyParser parser);

        void onFinish();

        void onStart();
    }
}
