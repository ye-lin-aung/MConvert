/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.plan.yelinaung.mmconvert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by chenupt@gmail.com on 2015/1/31.
 * Description TODO
 */
public class GuideFragment extends Fragment{

    private int bgRes;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ImageView imageView;
    private Button skip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgRes = getArguments().getInt("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences=getView().getContext().getSharedPreferences(Config.sharedPreferences, getView().getContext().MODE_PRIVATE);
        editor=sharedPreferences.edit();
        imageView = (ImageView) getView().findViewById(R.id.image);
        skip=(Button)getView().findViewById(R.id.skip);
        if(bgRes==R.drawable.ad_four){
            skip.setVisibility(View.VISIBLE);
        }
        else {
            skip.setVisibility(View.INVISIBLE);
        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(Config.firstTime, false);
                editor.commit();
                Intent intent=new Intent(getView().getContext(),MainActivity.class);
                startActivity(intent);getActivity().finish();


            }
        });
        imageView.setBackgroundResource(bgRes);
    }
}
