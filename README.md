# Sidebar
First of all, forgive me, my English is very poor.

A sidebar framework includes a series of extension set, is convenient to add the sidebar to your project, also can develop your own custom highly sidebar.

一个侧边栏框架包括一系列的扩展工具集合，可以方便快捷地添加侧边栏到你的项目中，也可扩展出你自己高度定制的自定义侧边栏。

You can very easily to use them in your XML layout file, like this: it is a layout file example which contains  top, left, right three directions of the sidebar.

你可以非常便捷地在你的XML布局文件中使用它们，像这样子：这是一个包含了上、左、右三个方向的侧边栏布局文件示例。

<?xml version="1.0" encoding="utf-8"?>
<cn.com.lich.sidebar.container.SlideSidebarContainerFrame
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:sidebar="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="SlideSidebarContainerFrameDemo"
        android:id="@+id/textView_slideSidebarContainerFrame"
        android:gravity="center" />

    <cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_gravity="top"
        android:background="#FF00FF"
        sidebar:attach_align="top"
        sidebar:slide_duration_time="700"
        sidebar:listener_range="0.2">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="SlideSidebarFrameDemo"
            android:id="@+id/textView_simpleSlideSidebarFrame_top"
            android:layout_gravity="center"
            android:gravity="center" />

    </cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame>

    <cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="left"
        android:background="#FFFF00"
        sidebar:attach_align="left"
        sidebar:slide_duration_time="500"
        sidebar:listener_range="0.2">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="SlideSidebarFrameDemo"
            android:id="@+id/textView_simpleSlideSidebarFrame_left"
            android:layout_gravity="center"
            android:gravity="center" />

    </cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame>

    <cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame
        android:layout_width="250dp"
        android:layout_height="200dp"
        android:layout_gravity="right|bottom"
        android:background="#00FFFF"
        sidebar:attach_align="right"
        sidebar:slide_duration_time="500"
        sidebar:listener_range="0.2">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="SlideSidebarFrameDemo"
            android:id="@+id/textView_simpleSlideSidebarFrame_right"
            android:layout_gravity="center"
            android:gravity="center" />

    </cn.com.lich.sidebar.frame.SimpleSlideSidebarFrame>

</cn.com.lich.sidebar.container.SlideSidebarContainerFrame>
