package com.infinite.piecharta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PieChart one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one= (PieChart) findViewById(R.id.one);
        TestEntity entity=new TestEntity(50,"#FF7F50");
        TestEntity entity1=new TestEntity(50,"#DC143C");
        TestEntity entity2=new TestEntity(50,"#00008B");
        TestEntity entity3=new TestEntity(50,"#A9A9A9");
        TestEntity entity4=new TestEntity(50,"#8B0000");
        TestEntity entity5=new TestEntity(50,"#9400D3");
        TestEntity entity6=new TestEntity(50,"#FFD700");

        List<IPieElement> list=new ArrayList<>();
        list.add(entity);
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        list.add(entity4);
        list.add(entity5);
        list.add(entity6);
        one.setData(list);
        one.setTitleText("年终总结比例图");
    }
}
