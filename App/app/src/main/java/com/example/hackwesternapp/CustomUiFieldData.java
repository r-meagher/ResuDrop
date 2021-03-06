package com.example.hackwesternapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomUiFieldData {
    public static final int NUMBER = 0;
    public static final int BOOLEAN = 1;
    public static final int TEXT = 2;

    int type;
    String id;
    String text;
    ArrayList<String> optionalCommands;
    View view;

    CustomUiFieldData(int type, String id, String text, ArrayList<String> optionalCommands) {
        this.type = type;
        this.id = id;
        this.text = text;
        this.optionalCommands = optionalCommands;
    }

    CustomUiFieldData(String s) {
        String tokens[] = s.split(Character.toString((char) 02));

        type = Integer.parseInt(tokens[0]);
        id = tokens[1];
        text = tokens[2];

        optionalCommands = new ArrayList<>();
        int numOptionalCommands = Integer.parseInt(tokens[3]);
        for (int i = 0; i < numOptionalCommands; i++)
            optionalCommands.add(tokens[i + 4]);
    }

    View createView(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = null;

        switch(type) {
            case NUMBER:
                view = factory.inflate(R.layout.custom_ui_number, null);
                break;

            case BOOLEAN:
                view = factory.inflate(R.layout.custom_ui_boolean, null);
                break;

            case TEXT:
                view = factory.inflate(R.layout.custom_ui_text, null);
                break;
        }

        ((TextView) view.findViewById(R.id.text)).setText(text);
        this.view = view.findViewById(R.id.input);
        return view;
    }

    View getInputView() {
        return view;
    }
}
