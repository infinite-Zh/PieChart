# PieChart
简单的饼状图
![](https://github.com/infinite-Zh/PieChartA/p.png)
---
1、在xml中引用：
```
<com.infinite.piecharta.PieChart
        android:id="@+id/one"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

2、代码中实例化view
3、设置元素
自定义的实体类，需要实现 *IPieElement* 接口
```
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
```
4、设置文字
```
one.setTitleText("年终总结比例图");
```
