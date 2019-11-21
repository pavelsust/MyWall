#### MyWall is a wallpaper library which can set scrollable and crop wallpaper. In wallpaper application developer have to fight with so many extra code which is almost same for all kind of wallpaper application. So i decided to develop a library for wallpaper application. This library has three options

    Set Scroll-able Wallpaper
    Set Lock Wallpaper
    Set Crop Wallpaper

![](2.gif) ![](1.gif)

###Insall

Add this dependency in your `build.gradle`: 

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
		}
	}
```
```xml
dependencies {
      implementation 'com.github.paveltech.MyWall:wallpaper:1.0.7'
}
```
### Implementation

<p> You don't need any permission for this library.It will automatically handel this. This library written by kotlin. Just pass a image link. </P>
    
 ```java
 WallpaperApplyTask.prepare(this)
           .wallpaperLink("https://wallpaperplay.com/walls/full/0/2/e/105979.jpg")
           .to(WallpaperApplyTask.Apply.HOMESCREEN())
           .start()
```
<p> Also you have three more category</p>

```java

   .to(WallpaperApplyTask.Apply.HOMESCREEN())   // scrollable wallpaper 
   or
   .to(WallpaperApplyTask.Apply.HOME_CROP_WALLPAPER())  // this is for crop wallpaper
   or
    .to(WallpaperApplyTask.Apply.HOMESCREEN_LOCKSCREEN())  // for wallpaper and lock screen both
		
```		

<p> Just change the Apply option for your requirnemn</p>. Also we have some callback option. 
WallpaperApplyTask.OnMessageCallBack
